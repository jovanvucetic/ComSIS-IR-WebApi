package comsis.service;

import comsis.core.model.PublicationData;
import comsis.core.model.PublicationIndexModel;
import comsis.core.serviceInterface.DocumentService;
import comsis.core.serviceInterface.IndexService;
import comsis.service.lucene.IrPublicationIndexer;
import comsis.service.lucene.IrPublicationSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static comsis.core.utils.Constants.Index.*;

@Service
public class IrIndexService implements IndexService {

    @Autowired
    private DocumentService documentService;

    private String indexFolderPath;

    public  IrIndexService(@Value("${index.folder.path}")String indexFolderPath){
        this.indexFolderPath = indexFolderPath;
    }

    @Override
    public void indexPublications(Collection<PublicationData> publicationData) {
        List<PublicationIndexModel>  indexModels = publicationData.stream()
                .map(this::getPublicationIndexModel)
                .collect(Collectors.toList());

        IrPublicationIndexer indexer = new IrPublicationIndexer(true, indexFolderPath);
        indexer.indexPublications(indexModels, documentService);
    }

    @Override
    public List<PublicationIndexModel> getPublicationsByTitle(String query, int numberOfHits) {
        return searchPapers(TITLE_SEARCH_KEY, query, numberOfHits);
    }

    @Override
    public List<PublicationIndexModel> getPublicationsByAuthor(String query, int numberOfHits) {
        return searchPapers(AUTHORS_SEARCH_KEY, query, numberOfHits);
    }

    @Override
    public List<PublicationIndexModel> getPublicationsByAbstract(String query, int numberOfHits) {
        return searchPapers(ABSTRACT_SEARCH_KEY, query, numberOfHits);
    }

    @Override
    public List<PublicationIndexModel> getPublicationsByWords(String query, int numberOfHits) {
        return searchPapers(PUBLICATION_SEARCH_KEY, query, numberOfHits);
    }

    private PublicationIndexModel getPublicationIndexModel(PublicationData publicationData) {
        String publicationPdfText = "";//getPublicationPdfText(publicationData.getId(), publicationData.getDownloadPath());
        return new PublicationIndexModel(publicationData, publicationPdfText);
    }

    private String getPublicationPdfText(UUID publicationId, String publicationDownloadPath) {
        documentService.downloadPdfIfNotExist(publicationId, publicationDownloadPath);
        return documentService.readPdf(publicationId);
    }

    private List<PublicationIndexModel> searchPapers(String field, String query, int numberOfHits) {
        IrPublicationSearcher publicationSearcher = new IrPublicationSearcher(indexFolderPath);
        return publicationSearcher.search(field, query, numberOfHits);
    }
}
