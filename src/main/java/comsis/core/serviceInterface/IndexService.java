package comsis.core.serviceInterface;

import comsis.core.model.PublicationData;
import comsis.core.model.PublicationIndexModel;

import java.util.Collection;
import java.util.List;

public interface IndexService {
    void indexPublications(Collection<PublicationData> publicationData);

    List<PublicationIndexModel> getPublicationsByTitle(String query, int numberOfHits);

    List<PublicationIndexModel> getPublicationsByAuthor(String query, int numberOfHits);

    List<PublicationIndexModel> getPublicationsByAbstract(String query, int numberOfHits);

    List<PublicationIndexModel> getPublicationsByWords(String query, int numberOfHits);
}
