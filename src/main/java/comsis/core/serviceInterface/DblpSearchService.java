package comsis.core.serviceInterface;

import comsis.core.model.dblp.DblpPublication;

import java.util.List;

public interface DblpSearchService {
    DblpPublication findPublicationByTitle(String title);

    List<DblpPublication> findPublicationsForAuthor(String authorsName);
}
