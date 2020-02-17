package comsis.core.serviceInterface;

import comsis.core.model.Author;
import comsis.core.model.AuthorIndexModel;
import comsis.core.model.index.AuthorIndexProperty;

import java.util.Collection;
import java.util.List;

public interface AuthorIndexService {
    void indexAuthors(Collection<Author> authors);

    List<AuthorIndexModel> searchIndex(AuthorIndexProperty property, String query, int numberOfHits);

    List<AuthorIndexModel> getAllAuthors();
}
