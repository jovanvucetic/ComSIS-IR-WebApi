package comsis.core.serviceInterface;

import comsis.core.model.Author;

import java.util.List;

public interface AuthorSearchService {

    Author getAuthorById(String query, int numberOfHits);

    List<Author> getAuthorsByEmail(String query, int numberOfHits);

    List<Author> getAuthorsByName(String query, int numberOfHits);

    List<Author> getAuthorsByInstitution(String query, int numberOfHits);
}
