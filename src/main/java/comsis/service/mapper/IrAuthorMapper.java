package comsis.service.mapper;

import comsis.core.mapperinterface.AuthorMapper;
import comsis.core.model.Author;
import comsis.data.entity.AuthorDto;
import org.springframework.stereotype.Service;

@Service
public class IrAuthorMapper implements AuthorMapper {

    @Override
    public AuthorDto toDto(Author author) {
        if(author == null) {
            return null;
        }

        AuthorDto dto = new AuthorDto();

        dto.setId(author.getId());
        dto.setName(author.getFullName());
        dto.setEmail(author.getEmail());
        dto.setInstitution(author.getInstitution());

        return dto;
    }

    @Override
    public Author toServiceModel(AuthorDto authorDto) {
        if (authorDto == null) {
            return null;
        }

        return new Author(authorDto.getId(), authorDto.getName(), authorDto.getEmail(), authorDto.getInstitution());
    }
}
