package comsis.service;

import comsis.core.exception.InvalidAffiliationException;
import comsis.core.mapperinterface.PublicationMapper;
import comsis.core.model.Author;
import comsis.core.model.Publication;
import comsis.core.model.comsis.PublicationPage;
import comsis.core.serviceInterface.AuthorService;
import comsis.core.serviceInterface.PublicationIndexer;
import comsis.core.serviceInterface.PublicationService;
import comsis.data.entity.PublicationDto;
import comsis.data.repositoryInterface.PublicationRepository;
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

    @Autowired
    private AuthorService authorService;

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private PublicationMapper publicationMapper;

    @Autowired
    private PublicationIndexer publicationIndexer;

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

        for(Publication publication : publications) {
            createPublication(publication);
        }
    }

    @Override
    public void indexPublications() {
        Iterator<PublicationDto> publicationsDtos = publicationRepository.findAll().iterator();
        List<Publication> publications = new ArrayList<>();
        while(publicationsDtos.hasNext()){
            publications.add(publicationMapper.toServiceModel(publicationsDtos.next()));
        }

        publicationIndexer.indexPublications(publications);
    }

    @Override
    public void createPublication(Publication publication) {
        List<Author> authors = new ArrayList<>();
        for(Author a : publication.getAuthors()){
            Author author = authorService.getOrCreate(a);
            authors.add(author);
        }

        publication.setAuthors(authors);
        publicationRepository.save(publicationMapper.toDto(publication));
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
