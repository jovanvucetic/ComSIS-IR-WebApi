package comsis.core.model;

import java.util.List;
import java.util.UUID;

public class PublicationData {
    private UUID id;
    private String title;
    private String publicationAbstract;
    private String downloadPath;
    private String venue;
    private int year;
    private List<Author> authors;
    private String[] keyWords;

    public PublicationData(String title, String publicationAbstract, String[] keyWords, List<Author> authors, String downloadPath) {
        this.title = title;
        this.publicationAbstract = publicationAbstract;
        this.authors = authors;
        this.downloadPath = downloadPath;
        this.keyWords = keyWords;
    }

    public PublicationData(UUID id, String title, String publicationAbstract,String[] keyWords, List<Author> authors, String downloadPath) {
        this(title, publicationAbstract,keyWords, authors, downloadPath);
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

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
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

    public boolean validatePublication() {
        if(id == null) {
            return false;
        }

        if(title == null || title.equals("")){
            return false;
        }

        if(publicationAbstract == null || publicationAbstract.equals("")){
            return false;
        }

        if(year == 0){
            return false;
        }

        if(authors == null || authors.isEmpty()){
            return false;
        }

        if(downloadPath == null || downloadPath.equals("")){
            return false;
        }

        return true;
    }
}
