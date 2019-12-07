package comsis.core.serviceInterface;

import comsis.core.model.Publication;

public interface PublicationService {

    void reloadComSisPublications();

    void indexPublications();

    void createPublication(Publication publication);
}
