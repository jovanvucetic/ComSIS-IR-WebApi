package comsis.service.lucene;

import comsis.core.model.AuthorIndexModel;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static comsis.core.utils.Constants.Index.Author;

public class IrAuthorSarcher {
    private IndexReader indexReader;
    private IndexSearcher searcher;
    private StandardAnalyzer queryAnalyzer;

    public IrAuthorSarcher(String indexPath) {
        try {
            Directory indexDirectory = FSDirectory.open(Paths.get(indexPath));
            indexReader = DirectoryReader.open(indexDirectory);
            searcher = new IndexSearcher(indexReader);

            queryAnalyzer = new StandardAnalyzer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<AuthorIndexModel> search(String field, String searchQuery, int numberOfHits){
        List<AuthorIndexModel> authors = new ArrayList<>();
        try {
            Query query = new QueryParser(field, queryAnalyzer).parse(searchQuery);
            TopDocs documents = searcher.search(query, numberOfHits);
            ScoreDoc[] hits = documents.scoreDocs;

            authors = Arrays.stream(hits).map(x -> extractAuthorFromScoreDoc(x)).collect(Collectors.toList());
            indexReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return authors;
    }

    public List<AuthorIndexModel> findAll() {
        List<AuthorIndexModel> authors = new ArrayList<>();
        try {
            Query query = new MatchAllDocsQuery();
            TopDocs documents = searcher.search(query, Integer.MAX_VALUE);
            ScoreDoc[] hits = documents.scoreDocs;

            authors = Arrays.stream(hits).map(x -> extractAuthorFromScoreDoc(x)).collect(Collectors.toList());
            indexReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return authors;
    }

    private AuthorIndexModel extractAuthorFromScoreDoc(ScoreDoc scoreDoc) {
        try {
            Document document = searcher.doc(scoreDoc.doc);

            String id = document.get(Author.ID_SEARCH_KEY);
            String fullName = document.get(Author.NAME_SEARCH_KEY);
            String institution = document.get(Author.INSTITUTION_SEARCH_KEY);
            String email = document.get(Author.EMAIL_SEARCH_KEY);

            return new AuthorIndexModel(id, fullName, email, institution);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
