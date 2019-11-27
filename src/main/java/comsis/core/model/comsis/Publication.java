package comsis.core.model.comsis;

import java.util.List;

public class Publication {
    private String title;
    private String publicationAbstract;
    private List<Author> authors;

    public Publication(String title, String publicationAbstract, List<Author> authors) {
        this.title = title;
        this.publicationAbstract = publicationAbstract;
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public String getPublicationAbstract() {
        return publicationAbstract;
    }

    public List<Author> getAuthors() {
        return authors;
    }
}
