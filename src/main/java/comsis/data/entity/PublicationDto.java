package comsis.data.entity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name = "ir_paper")
public class PublicationDto {

    @Id
    @GeneratedValue
    private UUID Id;

    private String title;

    @Column(columnDefinition="TEXT")
    private String publicationAbstract;

    private String downloadPath;

    @ManyToMany
    private List<AuthorDto> authors;

    public PublicationDto() {}

    public PublicationDto(String title, String publicationAbstract, String downloadPath, List<AuthorDto> authorDtos) {
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
}
