package comsis.api.controller;

import comsis.api.responses.AuthorDetailsResponse;
import comsis.api.responses.AuthorsResponse;
import comsis.core.serviceInterface.AuthorSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors/search")
public class AuthorSearchController {
    private static final String TOP_COUNT = "10";

    @Autowired
    private AuthorSearchService authorSearchService;

    @RequestMapping("/id")
    public AuthorDetailsResponse searchPapersById(@RequestParam String authorId,
                                                 @RequestParam(value = "count", defaultValue = TOP_COUNT, required = false) int count) {
        return AuthorDetailsResponse.fromAuthor(authorSearchService.getAuthorById(authorId, count));
    }

    @RequestMapping("/email")
    public AuthorsResponse searchPapersByAuthors(@RequestParam String query,
                                                 @RequestParam(value = "count", defaultValue = TOP_COUNT, required = false) int count) {
        return AuthorsResponse.fromAuthorsList(authorSearchService.getAuthorsByEmail(query, count));
    }

    @RequestMapping("/name")
    public AuthorsResponse searchPapersByName(@RequestParam String query,
                                                 @RequestParam(value = "count", defaultValue = TOP_COUNT, required = false) int count) {
        return AuthorsResponse.fromAuthorsList(authorSearchService.getAuthorsByName(query, count));
    }
    @RequestMapping("/institute")
    public AuthorsResponse searchPapersByInstitute(@RequestParam String query,
                                                 @RequestParam(value = "count", defaultValue = TOP_COUNT, required = false) int count) {
        return AuthorsResponse.fromAuthorsList(authorSearchService.getAuthorsByInstitution(query, count));
    }
}
