package comsis.service;

import comsis.core.model.Publication;
import comsis.core.model.PublicationIndexModel;
import comsis.core.serviceInterface.IndexService;
import comsis.core.serviceInterface.PublicationSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IrPublicationSearchService implements PublicationSearchService {

    @Autowired
    private IndexService indexService;

    @Override
    public List<Publication> getPublicationsByTitle(String query, int numberOfHits) {
        List<PublicationIndexModel> publicationIndexModels = indexService.getPublicationsByTitle(query, numberOfHits);
        return publicationIndexModels.stream().map(this::parsePublication).collect(Collectors.toList());
    }

    @Override
    public List<Publication> getPublicationsByAuthor(String query, int numberOfHits) {
        List<PublicationIndexModel> publicationIndexModels = indexService.getPublicationsByAuthor(query, numberOfHits);
        return publicationIndexModels.stream().map(this::parsePublication).collect(Collectors.toList());
    }

    @Override
    public List<Publication> getPublicationsByAbstract(String query, int numberOfHits) {
        List<PublicationIndexModel> publicationIndexModels = indexService.getPublicationsByAbstract(query, numberOfHits);
        return publicationIndexModels.stream().map(this::parsePublication).collect(Collectors.toList());
    }

    @Override
    public List<Publication> getPublicationsByWords(String query, int numberOfHits) {
        List<PublicationIndexModel> publicationIndexModels = indexService.getPublicationsByWords(query, numberOfHits);
        return publicationIndexModels.stream().map(this::parsePublication).collect(Collectors.toList());
    }

    private Publication parsePublication(PublicationIndexModel model) {
        if(model == null) {
            return  null;
        }

        Publication publication = new Publication();

        publication.setId(model.getId());
        publication.setTitle(model.getTitle());
        publication.setAuthors(model.getAuthors());
        publication.setPublicationAbstract(model.getPublicationAbstract());
        publication.setTextContent(model.getTextContent());

        return publication;
    }
}
