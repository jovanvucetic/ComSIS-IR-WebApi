package comsis.core.model;

import comsis.core.model.Author;

import java.util.List;
import java.util.UUID;

public class Publication {
    private UUID id;
    private String title;
    private String publicationAbstract;
    private String downloadPath;
    private List<Author> authors;

    public Publication(String title, String publicationAbstract, List<Author> authors, String downloadPath) {
        this.title = title;
        this.publicationAbstract = publicationAbstract;
        this.authors = authors;
        this.downloadPath = downloadPath;
    }

    public Publication(UUID id, String title, String publicationAbstract, List<Author> authors, String downloadPath) {
        this(title, publicationAbstract, authors, downloadPath);
        this.id = id;
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

    public void setAuthors(List<Author> authors) {this.authors = authors;}

    public UUID getId() {
        return id;
    }

    public String getDownloadPath() {
        return downloadPath;
    }
}
