package comsis.service;

import comsis.core.model.Publication;
import comsis.core.model.PublicationIndexModel;
import comsis.core.serviceInterface.PublicationIndexService;
import comsis.core.serviceInterface.PublicationSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IrPublicationSearchService implements PublicationSearchService {

    @Autowired
    private PublicationIndexService publicationIndexService;

    @Override
    public Publication getPublicationById(String id) {
        List<PublicationIndexModel> publicationIndexModels = publicationIndexService.getPublicationsById(id, 1);
        return publicationIndexModels.stream().findFirst().map(PublicationParser::parsePublication).get();
    }

    @Override
    public List<Publication> getPublicationsByTitle(String query, int numberOfHits) {
        List<PublicationIndexModel> publicationIndexModels = publicationIndexService.getPublicationsByTitle(query, numberOfHits);
        return publicationIndexModels.stream().map(PublicationParser::parsePublication).collect(Collectors.toList());
    }

    @Override
    public List<Publication> getPublicationsByAuthor(String query, int numberOfHits) {
        List<PublicationIndexModel> publicationIndexModels = publicationIndexService.getPublicationsByAuthor(query, numberOfHits);
        return publicationIndexModels.stream().map(PublicationParser::parsePublication).collect(Collectors.toList());
    }

    @Override
    public List<Publication> getPublicationsByAbstract(String query, int numberOfHits) {
        List<PublicationIndexModel> publicationIndexModels = publicationIndexService.getPublicationsByAbstract(query, numberOfHits);
        return publicationIndexModels.stream().map(PublicationParser::parsePublication).collect(Collectors.toList());
    }

    @Override
    public List<Publication> getPublicationsByWordsInDocument(String query, int numberOfHits) {
        List<PublicationIndexModel> publicationIndexModels = publicationIndexService.getPublicationsByWordsInDocument(query, numberOfHits);
        return publicationIndexModels.stream().map(PublicationParser::parsePublication).collect(Collectors.toList());
    }

    @Override
    public List<Publication> getPublicationsByYear(int year, int numberOfHits) {
        List<PublicationIndexModel> publicationIndexModels = publicationIndexService.getPublicationsByYear(year, numberOfHits);
        return publicationIndexModels.stream().map(PublicationParser::parsePublication).collect(Collectors.toList());
    }

    @Override
    public List<Publication> getPublicationByKeyWords(String query, int numberOfHits) {
        List<PublicationIndexModel> publicationIndexModels = publicationIndexService.getPublicationByKeyWords(query, numberOfHits);
        return publicationIndexModels.stream().map(PublicationParser::parsePublication).collect(Collectors.toList());
    }


}
