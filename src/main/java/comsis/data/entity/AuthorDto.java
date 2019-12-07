package comsis.data.entity;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="ir_author", schema = "public")
public class AuthorDto {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String email;

    private String institution;

    @ManyToMany(mappedBy = "authors")
    private Set<PublicationDto> publications;

    public AuthorDto(String name) {
        this.name = name;
    }

    public AuthorDto() {   }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public Set<PublicationDto> getPublications() {
        return publications;
    }

    public void setPublications(Set<PublicationDto> publications) { this.publications = publications;}
}
