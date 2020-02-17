package comsis.core.serviceInterface;

import comsis.core.model.PublicationData;
import comsis.core.model.PublicationIndexModel;
import comsis.core.model.WordFrequency;

import java.util.Collection;
import java.util.List;

public interface PublicationIndexService {
    void indexPublications(Collection<PublicationData> publicationData);

    List<PublicationIndexModel> getPublicationsById(String query, int numberOfHits);

    List<PublicationIndexModel> getPublicationsByTitle(String query, int numberOfHits);

    List<PublicationIndexModel> getPublicationsByAuthor(String query, int numberOfHits);

    List<PublicationIndexModel> getPublicationsByAbstract(String query, int numberOfHits);

    List<PublicationIndexModel> getPublicationsByWordsInDocument(String query, int numberOfHits);

    List<PublicationIndexModel> getPublicationsByYear(int year, int numberOfHits);

    List<PublicationIndexModel> getPublicationByKeyWords(String query, int numberOfHits);

    List<WordFrequency> getMostFrequentTitleWords(int topCount);

    List<PublicationIndexModel> findAll();
}
