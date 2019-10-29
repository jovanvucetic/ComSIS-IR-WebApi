package comsis.api.controller;

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
    public void findPublicationByTitle(@RequestParam String title) {  dblpSearchService.findPublicationByTitle(title);  }
}
