package comsis.service.lucene;

import comsis.core.model.AuthorIndexModel;
import comsis.core.utils.Constants;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class IrAuthorIndexer {
    private IndexWriter indexer;

    public IrAuthorIndexer(boolean createNew, String indexFolderPath) {
        try {
            Directory indexDirectory = FSDirectory.open(Paths.get(indexFolderPath));

            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

            indexWriterConfig = createNew
                    ? indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE)
                    : indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.APPEND);

            indexer = new IndexWriter(indexDirectory, indexWriterConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void indexAuthos(List<AuthorIndexModel> authors) {
        try {
            authors.stream().filter(a -> a != null).forEach(a -> indexAuthor(a));
            System.out.println("Done!!!");
            indexer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void indexAuthor(AuthorIndexModel author) {
        try {
            Document document = new Document();
            populateDocumentWithAuthorData(document, author);
            indexer.addDocument(document);
            System.out.println("Added document for " + author.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateDocumentWithAuthorData(Document document, AuthorIndexModel author){
        document.add(new TextField(Constants.Index.Author.ID_SEARCH_KEY, author.getId(), Field.Store.YES));
        document.add(new TextField(Constants.Index.Author.NAME_SEARCH_KEY, author.getFullName(), Field.Store.YES));
        document.add(new TextField(Constants.Index.Author.EMAIL_SEARCH_KEY, author.getEmail(), Field.Store.YES));
        document.add(new TextField(Constants.Index.Author.INSTITUTION_SEARCH_KEY, author.getInstitution(), Field.Store.YES));
    }
}
