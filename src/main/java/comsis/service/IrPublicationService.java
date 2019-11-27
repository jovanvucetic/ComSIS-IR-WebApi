package comsis.service;

import comsis.core.exception.InvalidAffiliationException;
import comsis.core.model.comsis.Author;
import comsis.core.model.comsis.Publication;
import comsis.core.model.comsis.PublicationPage;
import comsis.core.serviceInterface.PublicationService;
import comsis.service.webCrawler.CrawlerRunner;
import comsis.service.webCrawler.PublicationPageDataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class IrPublicationService implements PublicationService {

    @Autowired
    private CrawlerRunner crawlerRunner;

    @Override
    public void reloadComSisPublications() {
        Set<PublicationPage> paperPageSet = null;

        try {
            paperPageSet = crawlerRunner.searchForPublications();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Publication> publications = parsePublications(paperPageSet);
    }

    private List<Publication> parsePublications(Set<PublicationPage> paperPageSet) {
        List<Publication> publicationList = new ArrayList<>();
        if(paperPageSet != null) {
            Iterator<PublicationPage> it = paperPageSet.iterator();
            while (it.hasNext()) {

                PublicationPage pp = it.next();
                try{
                    Publication publication = PublicationPageDataParser.parsePublication(pp);
                    publicationList.add(publication);
                } catch (InvalidAffiliationException e) {
                    System.out.println(pp.getPageUrl());
                }
            }
        }
        return publicationList;
    }
}
