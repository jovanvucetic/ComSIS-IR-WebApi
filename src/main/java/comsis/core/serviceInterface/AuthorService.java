package comsis.core.serviceInterface;

import comsis.core.model.Author;

public interface AuthorService {
    Author getOrCreate(Author author);

    void indexAuthors();
}
