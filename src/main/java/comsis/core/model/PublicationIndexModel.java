package comsis.core.model;

import java.util.List;
import java.util.UUID;

public class PublicationIndexModel {
    private UUID id;
    private String title;
    private String publicationAbstract;
    private List<Author> authors;
    private String textContent;

    public UUID getId() {
        return id;
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

    public String getTextContent() {
        return textContent;
    }

    public PublicationIndexModel(PublicationData publicationData, String pdfText) {
        this.id = publicationData.getId();
        this.title = publicationData.getTitle();
        this.authors = publicationData.getAuthors();
        this.publicationAbstract = publicationData.getPublicationAbstract();
        this.textContent = pdfText;
    }

    public PublicationIndexModel(String title, String publicationAbstract, List<Author> authors, String textContent) {
        this.title = title;
        this.publicationAbstract = publicationAbstract;
        this.authors = authors;
        this.textContent = textContent;
    }
}
