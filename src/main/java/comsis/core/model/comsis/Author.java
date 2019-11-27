package comsis.core.model.comsis;

public class Author {
    private String fullName;
    private String email;
    private String affiliation;

    public Author(String fullName, String email, String affiliation) {
        this.fullName = fullName;
        this.email = email;
        this.affiliation = affiliation;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }
}
