package comsis.core.model;

import java.util.UUID;

public class Author {
    private UUID id;
    private String fullName;
    private String email;
    private String institution;

    public Author(String fullName, String email, String institution) {
        this.fullName = fullName;
        this.email = email;
        this.institution = institution;
    }

    public Author(UUID id, String fullName, String email, String institution) {
        this(fullName, email,institution);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public boolean validateAuthor(){
        if(id == null) {
            return false;
        }

        if(fullName == null || fullName.isEmpty()){
            return false;
        }

        if(email == null || email.isEmpty()){
            return false;
        }

        if(institution == null || institution.isEmpty()){
            return false;
        }

        return true;
    }
}
