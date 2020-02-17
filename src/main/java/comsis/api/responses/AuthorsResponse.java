package comsis.api.responses;

import comsis.core.model.Author;

import java.util.List;

public class AuthorsResponse {
    private List<Author> authors;
    private boolean isSuccessful;

    public static AuthorsResponse fromAuthorsList(List<Author> authors) {
        AuthorsResponse authorsResponse = new AuthorsResponse();

        authorsResponse.authors = authors;
        authorsResponse.isSuccessful = authors != null;

        return authorsResponse;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }
}
