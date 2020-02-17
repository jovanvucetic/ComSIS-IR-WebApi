package comsis.core.model;

import java.util.UUID;

public class AuthorIndexModel {
    private String id;
    private String fullName;
    private String email;
    private String institution;

    public AuthorIndexModel(Author author) {
        this.id = author.getId().toString();
        this.fullName = author.getFullName();
        this.email = author.getEmail();
        this.institution = author.getInstitution();
    }

    public AuthorIndexModel(String id, String fullName, String email, String institution) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.institution = institution;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getInstitution() {
        return institution;
    }
}
