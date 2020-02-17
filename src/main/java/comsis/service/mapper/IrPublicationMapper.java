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
        dto.setVenue(publicationData.getVenue());
        dto.setYear(publicationData.getYear());

        List<AuthorDto> authorList = publicationData.getAuthors().stream()
                .map(author -> authorMapper.toDto(author))
                .collect(Collectors.toList());

        if(publicationData.getKeyWords() != null) {
            dto.setKeyWords(String.join(",", publicationData.getKeyWords()));
        }

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

        String[] keyWords = publicationDto.getKeyWords() != null ? publicationDto.getKeyWords().split(",") : new String[0];
        PublicationData publicationData = new PublicationData(publicationDto.getId(), publicationDto.getTitle(), publicationDto.getPublicationAbstract(),
                keyWords, authors, publicationDto.getDownloadPath());
        publicationData.setVenue(publicationDto.getVenue());
        publicationData.setYear(publicationDto.getYear());


        return publicationData;
    }
}
