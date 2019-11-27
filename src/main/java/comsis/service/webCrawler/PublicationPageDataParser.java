package comsis.service.webCrawler;

import comsis.core.exception.InvalidAffiliationException;
import comsis.core.model.Author;
import comsis.core.model.Publication;
import comsis.core.model.comsis.PublicationPage;

import java.util.List;

public class PublicationPageDataParser {

    public static Publication parsePublication(PublicationPage publicationPage, String downloadSiteUrl) throws InvalidAffiliationException {
        String affiliationsSection = publicationPage.getAffiliationsSection();
        String authorsSection = publicationPage.getAuthorsSection();

        List<Author> authors = AuthorsPageDataParser.parseAuthors(authorsSection, affiliationsSection);

        String downloadPath = downloadSiteUrl + publicationPage.getPublicationDownloadRelativePath();

        return new Publication(publicationPage.getPublicationTitle(),
                publicationPage.getPublicationAbstract(), authors, downloadPath);
    }
}
