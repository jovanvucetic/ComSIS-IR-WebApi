package comsis.service;

import comsis.core.exception.InvalidAffiliationException;
import comsis.core.model.Publication;
import comsis.core.model.comsis.PublicationPage;
import comsis.core.serviceInterface.PublicationService;
import comsis.service.webCrawler.CrawlerRunner;
import comsis.service.webCrawler.PublicationPageDataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class IrPublicationService implements PublicationService {

    private String downloadSiteUrl;

    @Autowired
    private CrawlerRunner crawlerRunner;

    public IrPublicationService(@Value("${download.site.url}")String downloadSiteUrl){
        this.downloadSiteUrl = downloadSiteUrl;
    }

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

                PublicationPage publicationPage = it.next();
                try{
                    Publication publication = PublicationPageDataParser.parsePublication(publicationPage, downloadSiteUrl);
                    publicationList.add(publication);
                } catch (InvalidAffiliationException e) {
                    System.out.println(publicationPage.getPageUrl());
                }
            }
        }
        return publicationList;
    }
}
