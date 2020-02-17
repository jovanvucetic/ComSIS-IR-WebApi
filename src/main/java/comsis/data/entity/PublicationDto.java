package comsis.data.entity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="ir_publication", schema = "public")
public class PublicationDto {

    @Id
    @GeneratedValue
    private UUID Id;

    private String title;

    @Column(columnDefinition="TEXT")
    private String publicationAbstract;

    private String downloadPath;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<AuthorDto> authors;

    private int year;

    private String venue;

    @Column(length = 1000)
    private String keyWords;

    public PublicationDto() {}

    public PublicationDto(String title, String publicationAbstract, String downloadPath, int year, String venue, List<AuthorDto> authorDtos) {
        this.title = title;
        this.publicationAbstract = publicationAbstract;
        this.downloadPath = downloadPath;
        this.authors = authorDtos;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
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

    public List<AuthorDto> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDto> authors) {
        this.authors = authors;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }
}
