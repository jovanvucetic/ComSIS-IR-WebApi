package comsis.service.mapper;

import comsis.core.mapperinterface.AuthorMapper;
import comsis.core.mapperinterface.PublicationMapper;
import comsis.core.model.Author;
import comsis.core.model.PublicationData;
import comsis.data.entity.AuthorDto;
import comsis.data.entity.PublicationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IrPublicationMapper implements PublicationMapper {

    @Autowired
    private AuthorMapper authorMapper;

    @Override
    public PublicationDto toDto(PublicationData publicationData) {
        if(publicationData == null){
            return null;
        }

        PublicationDto dto = new PublicationDto();

        dto.setId(publicationData.getId());
        dto.setTitle(publicationData.getTitle());
        dto.setPublicationAbstract(publicationData.getPublicationAbstract());
        dto.setDownloadPath(publicationData.getDownloadPath());

        List<AuthorDto> authorList = publicationData.getAuthors().stream()
                .map(author -> authorMapper.toDto(author))
                .collect(Collectors.toList());

        dto.setAuthors(authorList);

        return dto;
    }

    @Override
    public PublicationData toServiceModel(PublicationDto publicationDto) {
        if(publicationDto == null) {
            return null;
        }

        List<Author> authors = publicationDto.getAuthors().stream()
                .map(authorDto -> authorMapper.toServiceModel(authorDto))
                .collect(Collectors.toList());

        return new PublicationData(publicationDto.getId(), publicationDto.getTitle(), publicationDto.getPublicationAbstract(),
                authors, publicationDto.getDownloadPath());
    }
}
