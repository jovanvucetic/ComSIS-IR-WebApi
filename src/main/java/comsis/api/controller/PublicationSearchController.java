package comsis.api.controller;

import comsis.api.responses.PublicationsResponse;
import comsis.core.model.PublicationIndexModel;
import comsis.core.serviceInterface.IndexService;
import comsis.core.serviceInterface.PublicationSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/publication/search")
public class PublicationSearchController {
    private static final String TOP_COUNT = "10";

    @Autowired
    private PublicationSearchService publicationSearchService;

    @RequestMapping("/authors")
    public PublicationsResponse searchPapersByAuthors(@RequestParam String query,
                                                      @RequestParam(value = "count", defaultValue = TOP_COUNT, required = false) int count) {
        return PublicationsResponse.fromPublicationList(publicationSearchService.getPublicationsByAuthor(query, count));
    }

    @RequestMapping("/abstract")
    public PublicationsResponse searchPapersByAbstract(@RequestParam String query,
                                                 @RequestParam(value = "count", defaultValue = TOP_COUNT, required = false) int count) {
        return PublicationsResponse.fromPublicationList(publicationSearchService.getPublicationsByAbstract(query, count));
    }
    @RequestMapping("/title")
    public PublicationsResponse searchPapersByTitle(@RequestParam String query,
                                              @RequestParam(value = "count", defaultValue = TOP_COUNT, required = false) int count) {
        return PublicationsResponse.fromPublicationList(publicationSearchService.getPublicationsByTitle(query, count));
    }

    @RequestMapping("/words")
    public PublicationsResponse searchPapersByWords(@RequestParam String query,
                                                           @RequestParam(value = "count", defaultValue = TOP_COUNT, required = false) int count) {
        return PublicationsResponse.fromPublicationList(publicationSearchService.getPublicationsByWords(query, count));
    }
}
