package comsis.service.webCrawler;

import comsis.core.exception.InvalidAffiliationException;
import comsis.core.model.comsis.Author;
import comsis.core.model.comsis.Publication;
import comsis.core.model.comsis.PublicationPage;

import java.util.List;

public class PublicationPageDataParser {

    public static Publication parsePublication(PublicationPage publicationPage) throws InvalidAffiliationException {
        String affiliationsSection = publicationPage.getAffiliationsSection();
        String authorsSection = publicationPage.getAuthorsSection();

        List<Author> authors = AuthorsPageDataParser.parseAuthors(authorsSection, affiliationsSection);

        return new Publication(publicationPage.getPublicationTitle(),
                publicationPage.getPublicationAbstract(),
                authors);
    }
}
