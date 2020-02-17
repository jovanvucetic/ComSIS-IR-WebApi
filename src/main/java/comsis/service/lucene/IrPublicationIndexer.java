package comsis.service.lucene;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import comsis.core.model.Author;
import comsis.core.model.PublicationIndexModel;
import comsis.core.serviceInterface.DocumentService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static comsis.core.utils.Constants.Index.Publication;

public class IrPublicationIndexer {

    private DocumentService documentService;

    private IndexWriter indexer;

    public  IrPublicationIndexer(boolean createNew, String indexFolderPath, DocumentService documentService) {
        try {
            this.documentService = documentService;
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

    public void indexPublications(List<PublicationIndexModel> publications) {
        try {
            publications.stream().filter(p -> p != null).forEach(p -> indexPaper(p));
            System.out.println("Done!!!");
            indexer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void indexPaper(PublicationIndexModel publication) {
        try {
            Document document = new Document();
            populateDocumentWithPublicationData(document, publication);
            indexer.addDocument(document);
            System.out.println("Added document for " + publication.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateDocumentWithPublicationData(Document document, PublicationIndexModel publication) {
        document.add(new TextField(Publication.TITLE_SEARCH_KEY, publication.getTitle(), Field.Store.YES));
        document.add(new TextField(Publication.ID_SEARCH_KEY, publication.getId().toString(), Field.Store.YES));
        document.add(new TextField(Publication.ABSTRACT_SEARCH_KEY, publication.getPublicationAbstract(), Field.Store.YES));
        document.add(new TextField(Publication.YEAR_SEARCH_KEY, publication.getYear(), Field.Store.YES));
        document.add(new TextField(Publication.DOWNLOAD_PATH_SEARCH_KEY, publication.getDocumentDownloadPath(), Field.Store.YES));
        document.add(new TextField(Publication.KEY_WORDS_SEARCH_KEY, publication.getKeyWords(), Field.Store.YES));

        String publicationContent = documentService.readDocumentContent(publication.getId());
        document.add(new TextField(Publication.PUBLICATION_CONTENT_SEARCH_KEY, publicationContent, Field.Store.YES));

        String authorsListJson = getAuthorsString(publication.getAuthors());
        document.add(new TextField(Publication.AUTHOR_SEARCH_KEY, authorsListJson, Field.Store.YES));
    }

    private String getAuthorsString(List<Author> authors) {
        Gson gson = new GsonBuilder().create();

        return gson.toJson(authors);
    }
}
