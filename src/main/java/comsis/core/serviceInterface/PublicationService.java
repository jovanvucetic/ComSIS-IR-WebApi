package comsis.core.serviceInterface;

import comsis.core.model.PublicationData;

public interface PublicationService {

    void reloadComSisPublications();

    void indexPublications();

    void createPublication(PublicationData publicationData);
}
