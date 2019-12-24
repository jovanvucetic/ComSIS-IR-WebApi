package comsis.core.serviceInterface;

import comsis.core.model.Publication;

import java.util.List;

public interface PublicationSearchService {

    List<Publication> getPublicationsByTitle(String query, int numberOfHits);

    List<Publication> getPublicationsByAuthor(String query, int numberOfHits);

    List<Publication> getPublicationsByAbstract(String query, int numberOfHits);

    List<Publication> getPublicationsByWords(String query, int numberOfHits);

    List<Publication> getPublicationsByYear(int year, int numberOfHits);
}
