package comsis.service.lucene;

import comsis.core.model.PublicationIndexModel;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static comsis.core.utils.Constants.Index.*;

public class IrPublicationSearcher {
    private IndexReader indexReader;
    private IndexSearcher searcher;
    private StandardAnalyzer queryAnalyzer;

    public IrPublicationSearcher(String indexPath) {
        try {
            Directory indexDirectory = FSDirectory.open(Paths.get(indexPath));
            indexReader = DirectoryReader.open(indexDirectory);
            searcher = new IndexSearcher(indexReader);

            queryAnalyzer = new StandardAnalyzer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<PublicationIndexModel> search(String field, String searchQuery, int numberOfHits){
        List<PublicationIndexModel> publications = new ArrayList<>();
        try {
            Query query = new QueryParser(field, queryAnalyzer).parse(searchQuery);
            TopDocs documents = searcher.search(query, numberOfHits);
            ScoreDoc[] hits = documents.scoreDocs;

            publications = Arrays.stream(hits).map(x -> extractPublicationFromScoreDoc(x)).collect(Collectors.toList());
            indexReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return publications;
    }

    private PublicationIndexModel extractPublicationFromScoreDoc(ScoreDoc scoreDoc) {
        try {
            Document document = searcher.doc(scoreDoc.doc);
            String title = document.get(TITLE_SEARCH_KEY);
            String paperAbstract = document.get(ABSTRACT_SEARCH_KEY);
            String[] authors = document.get(AUTHORS_SEARCH_KEY).split(",");
            String textContent = document.get(PUBLICATION_SEARCH_KEY);

            return new PublicationIndexModel(title, paperAbstract, new ArrayList<>(), textContent);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
