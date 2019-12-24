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

import java.security.PublicKey;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static comsis.core.utils.Constants.Index.Publication;

@Service
public class IrIndexService implements IndexService {

    private String indexFolderPath;

    @Autowired
    private  DocumentService documentService;

    public  IrIndexService(@Value("${index.folder.path}")String indexFolderPath){
        this.indexFolderPath = indexFolderPath;
    }

    @Override
    public void indexPublications(Collection<PublicationData> publicationData) {
        List<PublicationIndexModel>  indexModels = publicationData.stream()
                .filter(p -> p.validatePublication())
                .map(this::getPublicationIndexModel)
                .collect(Collectors.toList());

        IrPublicationIndexer indexer = new IrPublicationIndexer(true, indexFolderPath, documentService);
        indexer.indexPublications(indexModels);
    }

    @Override
    public List<PublicationIndexModel> getPublicationsByTitle(String query, int numberOfHits) {
        return searchPublication(Publication.TITLE_SEARCH_KEY, query, numberOfHits);
    }

    @Override
    public List<PublicationIndexModel> getPublicationsByAuthor(String query, int numberOfHits) {
        return searchPublication(Publication.AUTHOR_SEARCH_KEY, query, numberOfHits);
    }

    @Override
    public List<PublicationIndexModel> getPublicationsByAbstract(String query, int numberOfHits) {
        return searchPublication(Publication.ABSTRACT_SEARCH_KEY, query, numberOfHits);
    }

    @Override
    public List<PublicationIndexModel> getPublicationsByWords(String query, int numberOfHits) {
        return searchPublication(Publication.PUBLICATION_CONTENT_SEARCH_KEY, query, numberOfHits);
    }

    @Override
    public List<PublicationIndexModel> getPublicationsByYear(int year, int numberOfHits) {
        return searchPublication(Publication.YEAR_SEARCH_KEY, ((Integer)year).toString(), numberOfHits);
    }

    private PublicationIndexModel getPublicationIndexModel(PublicationData publicationData) {
        return new PublicationIndexModel(publicationData);
    }

    private List<PublicationIndexModel> searchPublication(String field, String query, int numberOfHits) {
        IrPublicationSearcher publicationSearcher = new IrPublicationSearcher(indexFolderPath);
        return publicationSearcher.search(field, query, numberOfHits);
    }
}
