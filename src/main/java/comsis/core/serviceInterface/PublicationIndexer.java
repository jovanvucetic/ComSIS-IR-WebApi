package comsis.core.serviceInterface;

import comsis.core.model.Publication;

import java.util.Collection;

public interface PublicationIndexer {
    void indexPublications(Collection<Publication> publications);
}
