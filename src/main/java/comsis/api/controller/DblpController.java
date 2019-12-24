package comsis.api.controller;

import comsis.core.model.dblp.DblpPublication;
import comsis.core.serviceInterface.DblpSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dblp")
public class DblpController {

    @Autowired
    private DblpSearchService dblpSearchService;

    @RequestMapping("/findPublicationByTitle")
    public DblpPublication findPublicationByTitle(@RequestParam String title) {  return dblpSearchService.findPublicationByTitle(title);  }
}
