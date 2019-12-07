package comsis.data.repositoryInterface;

import comsis.data.entity.AuthorDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AuthorRepository  extends CrudRepository<AuthorDto, UUID> {

    @Query(value = "SELECT * FROM ir_author a WHERE a.email = ?1 LIMIT 1", nativeQuery = true)
    AuthorDto findByEmail(String email);
}
