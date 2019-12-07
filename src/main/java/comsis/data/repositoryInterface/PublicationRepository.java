package comsis.data.repositoryInterface;

import comsis.data.entity.PublicationDto;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PublicationRepository extends CrudRepository<PublicationDto, UUID> {
}
