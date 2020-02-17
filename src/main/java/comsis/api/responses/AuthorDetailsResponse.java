package comsis.api.responses;

import comsis.core.model.Author;


public class AuthorDetailsResponse {
    private Author author;
    private boolean isSuccessful;

    public static AuthorDetailsResponse fromAuthor(Author author) {
        AuthorDetailsResponse authorsResponse = new AuthorDetailsResponse();

        authorsResponse.author = author;
        authorsResponse.isSuccessful = author != null;

        return authorsResponse;
    }

    public Author getAuthor() {
        return author;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }
}
