package comsis.service;
import comsis.core.model.PublicationData;
import comsis.core.model.PublicationIndexModel;
import comsis.core.model.WordFrequency;
import comsis.core.serviceInterface.DocumentService;
import comsis.core.serviceInterface.PublicationIndexService;
import comsis.service.lucene.IrPublicationIndexer;
import comsis.service.lucene.IrPublicationSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static comsis.core.utils.Constants.Index.Publication;

@Service
public class IrPublicationIndexService implements PublicationIndexService {

    private String publicationIndexFolderPath;

    @Autowired
    private  DocumentService documentService;

    public IrPublicationIndexService(@Value("${index.folder.path}")String publicationIndexFolderPath) {
        this.publicationIndexFolderPath = publicationIndexFolderPath;
    }

    @Override
    public void indexPublications(Collection<PublicationData> publicationData) {
        List<PublicationIndexModel>  indexModels = publicationData.stream()
                .filter(p -> p.validatePublication())
                .map(this::getPublicationIndexModel)
                .collect(Collectors.toList());

        IrPublicationIndexer indexer = new IrPublicationIndexer(true, publicationIndexFolderPath, documentService);
        indexer.indexPublications(indexModels);
    }

    @Override
    public List<PublicationIndexModel> getPublicationsById(String query, int numberOfHits) {
        return searchPublication(Publication.ID_SEARCH_KEY, query, numberOfHits);
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
    public List<PublicationIndexModel> getPublicationsByWordsInDocument(String query, int numberOfHits) {
        return searchPublication(Publication.PUBLICATION_CONTENT_SEARCH_KEY, query, numberOfHits);
    }

    @Override
    public List<PublicationIndexModel> getPublicationsByYear(int year, int numberOfHits) {
        return searchPublication(Publication.YEAR_SEARCH_KEY, ((Integer)year).toString(), numberOfHits);
    }

    @Override
    public List<PublicationIndexModel> getPublicationByKeyWords(String query, int numberOfHits) {
        return searchPublication(Publication.KEY_WORDS_SEARCH_KEY, query, numberOfHits);
    }

    @Override
    public List<WordFrequency> getMostFrequentTitleWords(int topCount) {
        IrPublicationSearcher publicationSearcher = new IrPublicationSearcher(publicationIndexFolderPath);
        return Arrays.stream(publicationSearcher.findMostFrequentWords(Publication.TITLE_SEARCH_KEY, topCount))
                .map(termStats -> new WordFrequency(termStats.termtext.utf8ToString(), termStats.docFreq)).collect(Collectors.toList());
    }

    @Override
    public List<PublicationIndexModel> findAll() {
        IrPublicationSearcher publicationSearcher = new IrPublicationSearcher(publicationIndexFolderPath);
        return publicationSearcher.findAll();
    }

    private PublicationIndexModel getPublicationIndexModel(PublicationData publicationData) {
        return new PublicationIndexModel(publicationData);
    }

    private List<PublicationIndexModel> searchPublication(String field, String query, int numberOfHits) {
        IrPublicationSearcher publicationSearcher = new IrPublicationSearcher(publicationIndexFolderPath);
        return publicationSearcher.search(field, query, numberOfHits);
    }
}
