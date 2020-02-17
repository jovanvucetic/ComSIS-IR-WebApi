package comsis.service;

import comsis.core.exception.InvalidAffiliationException;
import comsis.core.mapperinterface.PublicationMapper;
import comsis.core.model.Author;
import comsis.core.model.PublicationData;
import comsis.core.model.comsis.PublicationPage;
import comsis.core.model.dblp.DblpPublication;
import comsis.core.serviceInterface.*;
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
    private DocumentService documentService;

    @Autowired
    private CrawlerRunner crawlerRunner;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private PublicationMapper publicationMapper;

    @Autowired
    private PublicationIndexService publicationIndexService;

    @Autowired
    private DblpSearchService dblpSearchService;

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

        List<PublicationData> publicationDataList = parsePublications(paperPageSet);

        for(PublicationData publicationData : publicationDataList) {
            DblpPublication dblpPublication = findDblpPublication(publicationData.getTitle());
            if(dblpPublication != null) {
                publicationData.setYear(dblpPublication.getYear());
            }

            createPublication(publicationData);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void indexPublications() {
        Iterator<PublicationDto> publicationsDtos = publicationRepository.findAll().iterator();
        List<PublicationData> publicationData = new ArrayList<>();
        while(publicationsDtos.hasNext()){
            PublicationData publication = publicationMapper.toServiceModel(publicationsDtos.next());
            documentService.downloadPdfIfNotExist(publication.getId(), publication.getDownloadPath());
            documentService.parseAndSavePdfText(publication.getId());
            publicationData.add(publication);
        }

        publicationIndexService.indexPublications(publicationData);
    }

    @Override
    public void createPublication(PublicationData publicationData) {
        List<Author> authors = new ArrayList<>();
        for(Author a : publicationData.getAuthors()){
            Author author = authorService.getOrCreate(a);
            authors.add(author);
        }

        publicationData.setAuthors(authors);
        publicationRepository.save(publicationMapper.toDto(publicationData));
    }

    public DblpPublication findDblpPublication(String publicationTitle){
        return dblpSearchService.findPublicationByTitle(publicationTitle);
    }

    private List<PublicationData> parsePublications(Set<PublicationPage> paperPageSet) {
        List<PublicationData> publicationDataList = new ArrayList<>();
        if(paperPageSet != null) {
            Iterator<PublicationPage> it = paperPageSet.iterator();
            while (it.hasNext()) {

                PublicationPage publicationPage = it.next();
                try{
                    PublicationData publicationData = PublicationPageDataParser.parsePublication(publicationPage, downloadSiteUrl);
                    publicationDataList.add(publicationData);
                } catch (InvalidAffiliationException e) {
                    System.out.println(publicationPage.getPageUrl());
                }
            }
        }
        return publicationDataList;
    }
}
