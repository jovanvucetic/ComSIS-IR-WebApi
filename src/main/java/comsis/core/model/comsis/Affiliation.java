package comsis.core.model.comsis;

import java.util.List;

public class Affiliation {
    private String institution;
    private List<String> emails;

    public Affiliation(String institution, List<String> emails) {
        this.institution = institution;
        this.emails = emails;
    }

    public String getInstitution() {
        return institution;
    }

    public List<String> getEmails() {
        return emails;
    }
}
