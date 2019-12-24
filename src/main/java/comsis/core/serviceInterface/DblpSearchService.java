package comsis.core.serviceInterface;

import comsis.core.model.dblp.DblpPublication;

public interface DblpSearchService {
    DblpPublication findPublicationByTitle(String title);
}
