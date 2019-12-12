package comsis.core.serviceInterface;

import comsis.core.model.PublicationData;

import java.util.Collection;

public interface PublicationIndexer {
    void indexPublications(Collection<PublicationData> publicationData);
}
