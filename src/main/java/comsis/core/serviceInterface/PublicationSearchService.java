package comsis.core.serviceInterface;

import comsis.core.model.Publication;

import java.util.List;

public interface PublicationSearchService {

    Publication getPublicationById(String id);

    List<Publication> getPublicationsByTitle(String query, int numberOfHits);

    List<Publication> getPublicationsByAuthor(String query, int numberOfHits);

    List<Publication> getPublicationsByAbstract(String query, int numberOfHits);

    List<Publication> getPublicationsByWordsInDocument(String query, int numberOfHits);

    List<Publication> getPublicationsByYear(int year, int numberOfHits);

    List<Publication> getPublicationByKeyWords(String query, int numberOfHits);
}
