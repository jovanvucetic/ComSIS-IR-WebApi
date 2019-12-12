package comsis.service.lucene;

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

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static comsis.core.utils.Constants.Index.*;

public class IrPublicationIndexer {

    private IndexWriter indexer;

    public  IrPublicationIndexer(boolean createNew, String indexFolderPath) {
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

    public void indexPublications(List<PublicationIndexModel> publications, DocumentService documentService) {
        try {
            publications.stream().forEach(p -> indexPaper(p, documentService));
            System.out.println("Done!!!");
            indexer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void indexPaper(PublicationIndexModel publication, DocumentService documentService) {
        try {
            Document document = new Document();
            document.add(new TextField(TITLE_SEARCH_KEY, publication.getTitle(), Field.Store.YES));
            document.add(new TextField(AUTHORS_SEARCH_KEY, getAuthorsString(publication.getAuthors()), Field.Store.YES));
            document.add(new TextField(ABSTRACT_SEARCH_KEY, publication.getPublicationAbstract(), Field.Store.YES));
            document.add(new TextField(PUBLICATION_SEARCH_KEY, documentService.readPdf(publication.getId()), Field.Store.YES));
            indexer.addDocument(document);
            System.out.println("Added document for " + publication.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getAuthorsString(List<Author> authors) {
        List<String> authorsNames = authors.stream().map(author -> author.getFullName()).collect(Collectors.toList());;
        return String.join(",", authorsNames);
    }
}
