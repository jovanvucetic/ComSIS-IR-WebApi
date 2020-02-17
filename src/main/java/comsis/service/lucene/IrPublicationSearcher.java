package comsis.service.lucene;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import comsis.core.model.Author;
import comsis.core.model.PublicationIndexModel;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.misc.HighFreqTerms;
import org.apache.lucene.misc.TermStats;
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
import java.util.UUID;
import java.util.stream.Collectors;

import static comsis.core.utils.Constants.Index.Publication;


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

    public List<PublicationIndexModel> findAll() {

        List<PublicationIndexModel> publications = new ArrayList<>();
        try {
            Query query = new MatchAllDocsQuery();
            TopDocs documents = searcher.search(query, Integer.MAX_VALUE);
            ScoreDoc[] hits = documents.scoreDocs;
            publications = Arrays.stream(hits).map(x -> extractPublicationFromScoreDoc(x)).collect(Collectors.toList());
            indexReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return publications;
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

    public TermStats[] findMostFrequentWords(String field, int topCount) {
        HighFreqTerms.TotalTermFreqComparator cmp = new HighFreqTerms.TotalTermFreqComparator();
        try {
            return HighFreqTerms.getHighFreqTerms(indexReader, topCount, field, cmp);
        } catch (Exception e) {
            e.printStackTrace();
            return new TermStats[0];
        }
    }

    private PublicationIndexModel extractPublicationFromScoreDoc(ScoreDoc scoreDoc) {
        try {
            Document document = searcher.doc(scoreDoc.doc);

            UUID id = UUID.fromString( document.get(Publication.ID_SEARCH_KEY));
            String title = document.get(Publication.TITLE_SEARCH_KEY);
            String year = document.get(Publication.YEAR_SEARCH_KEY);
            String downloadPath = document.get(Publication.DOWNLOAD_PATH_SEARCH_KEY);
            String paperAbstract = document.get(Publication.ABSTRACT_SEARCH_KEY);
            String keyWords = document.get(Publication.KEY_WORDS_SEARCH_KEY);
            List<Author> authors = getAuthorsList(document);

            return new PublicationIndexModel(id, title, paperAbstract,keyWords, authors, year, downloadPath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Author> getAuthorsList(Document document) {
        String authorsJson = document.get(Publication.AUTHOR_SEARCH_KEY);

        Gson gson = new GsonBuilder().create();
        return gson.fromJson(authorsJson, (new TypeToken<List<Author>>(){}).getType());
    }
}
