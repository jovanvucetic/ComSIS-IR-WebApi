package comsis.core.model;

import java.util.List;
import java.util.UUID;

public class Publication {
    private UUID id;
    private String title;
    private String publicationAbstract;
    private String downloadPath;
    private String venue;
    private int year;
    private String[] keyWords;
    private List<Author> authors;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublicationAbstract() {
        return publicationAbstract;
    }

    public void setPublicationAbstract(String publicationAbstract) {
        this.publicationAbstract = publicationAbstract;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String textContent) {
        this.venue = textContent;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String[] getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String[] keyWords) {
        this.keyWords = keyWords;
    }
}
