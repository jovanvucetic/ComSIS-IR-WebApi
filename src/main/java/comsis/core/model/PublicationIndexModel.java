package comsis.core.model;

import java.util.List;
import java.util.UUID;

public class PublicationIndexModel {
    private UUID id;
    private String title;
    private String publicationAbstract;
    private List<Author> authors;
    private String year;
    private String documentDownloadPath;
    private String keyWords;

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

    public String getYear() {
        return year;
    }

    public String getDocumentDownloadPath() {
        return documentDownloadPath;
    }

    public String getKeyWords() {return  keyWords;}

    public PublicationIndexModel(PublicationData publicationData) {
        this.id = publicationData.getId();
        this.title = publicationData.getTitle();
        this.authors = publicationData.getAuthors();
        this.publicationAbstract = publicationData.getPublicationAbstract();
        this.year = ((Integer)publicationData.getYear()).toString();
        this.documentDownloadPath = publicationData.getDownloadPath();
        this.keyWords = String.join(",", publicationData.getKeyWords());
    }

    public PublicationIndexModel(UUID id, String title, String publicationAbstract, String keyWords,
                                 List<Author> authors, String year, String downloadPath) {
        this.title = title;
        this.publicationAbstract = publicationAbstract;
        this.authors = authors;
        this.documentDownloadPath = downloadPath;
        this.year = year;
        this.id = id;
        this.keyWords = keyWords;
    }
}
