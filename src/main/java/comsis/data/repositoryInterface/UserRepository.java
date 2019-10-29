package comsis.data.repositoryInterface;

import comsis.data.entity.UserDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<UserDto, UUID> {

    @Query(value = "SELECT * FROM ir_user u WHERE u.username = ?1 LIMIT 1", nativeQuery = true)
    UserDto findByUsername(String username);
}
