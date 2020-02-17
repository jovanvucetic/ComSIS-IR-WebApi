package comsis.service;

import comsis.core.model.Author;
import comsis.core.model.AuthorIndexModel;

import java.util.UUID;

public class AuthorParser {
    public static Author parseAuthor(AuthorIndexModel model) {
        if(model == null) {
            return  null;
        }

        UUID authorId = UUID.fromString(model.getId());
        Author publication = new Author(authorId, model.getFullName(), model.getEmail(), model.getInstitution() );

        return publication;
    }
}
