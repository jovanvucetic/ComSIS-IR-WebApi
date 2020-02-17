package comsis.api.controller;

import comsis.core.model.dblp.DblpPublication;
import comsis.core.serviceInterface.DblpSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search/dblp")
public class DblpController {

    @Autowired
    private DblpSearchService dblpSearchService;

    @RequestMapping("/title")
    public DblpPublication findPublicationByTitle(@RequestParam String title) {  return dblpSearchService.findPublicationByTitle(title);  }

    @RequestMapping("/author")
    public List<DblpPublication> findPublicationByAuthor(@RequestParam String authorName) {  return dblpSearchService.findPublicationsForAuthor(authorName);  }
}
