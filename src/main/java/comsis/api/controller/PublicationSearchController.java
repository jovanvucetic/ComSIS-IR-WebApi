package comsis.api.controller;

import comsis.api.responses.PublicationDetailsResponse;
import comsis.api.responses.PublicationsResponse;
import comsis.core.serviceInterface.PublicationSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/publications/search")
public class PublicationSearchController {
    private static final String TOP_COUNT = "10";

    @Autowired
    private PublicationSearchService publicationSearchService;

    @RequestMapping("")
    public PublicationDetailsResponse getPublicationDetails(@RequestParam String publicationId,
                                                            @RequestParam(value = "count", defaultValue = TOP_COUNT, required = false) int count) {
        return PublicationDetailsResponse.fromPublication(publicationSearchService.getPublicationById(publicationId));
    }

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

    @RequestMapping("/wordsInDocument")
    public PublicationsResponse searchPapersByWordsInDocument(@RequestParam String query,
                                                           @RequestParam(value = "count", defaultValue = TOP_COUNT, required = false) int count) {
        return PublicationsResponse.fromPublicationList(publicationSearchService.getPublicationsByWordsInDocument(query, count));
    }

    @RequestMapping("/keywords")
    public PublicationsResponse searchPapersByKeyWords(@RequestParam String query,
                                                    @RequestParam(value = "count", defaultValue = TOP_COUNT, required = false) int count) {
        return PublicationsResponse.fromPublicationList(publicationSearchService.getPublicationByKeyWords(query, count));
    }

    @RequestMapping("/year")
    public PublicationsResponse searchPapersByYer(@RequestParam int year) {
        return PublicationsResponse.fromPublicationList(publicationSearchService.getPublicationsByYear(year, Integer.MAX_VALUE));
    }
}
