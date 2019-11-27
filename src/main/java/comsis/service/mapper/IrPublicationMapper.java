package comsis.service.mapper;

import comsis.core.mapperinterface.AuthorMapper;
import comsis.core.mapperinterface.PublicationMapper;
import comsis.core.model.Author;
import comsis.core.model.Publication;
import comsis.data.entity.AuthorDto;
import comsis.data.entity.PublicationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IrPublicationMapper implements PublicationMapper {

    @Autowired
    private AuthorMapper authorMapper;

    @Override
    public PublicationDto toDto(Publication publication) {
        if(publication == null){
            return null;
        }

        PublicationDto dto = new PublicationDto();

        dto.setId(publication.getId());
        dto.setTitle(publication.getTitle());
        dto.setPublicationAbstract(publication.getPublicationAbstract());
        dto.setDownloadPath(publication.getDownloadPath());

        List<AuthorDto> authorList = publication.getAuthors().stream()
                .map(author -> authorMapper.toDto(author))
                .collect(Collectors.toList());

        dto.setAuthors(authorList);

        return dto;
    }

    @Override
    public Publication toServiceModel(PublicationDto publicationDto) {
        if(publicationDto == null) {
            return null;
        }

        List<Author> authors = publicationDto.getAuthors().stream()
                .map(authorDto -> authorMapper.toServiceModel(authorDto))
                .collect(Collectors.toList());

        return new Publication(publicationDto.getTitle(), publicationDto.getPublicationAbstract(),
                authors, publicationDto.getDownloadPath());
    }
}
