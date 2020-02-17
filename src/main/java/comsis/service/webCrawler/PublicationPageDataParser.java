package comsis.service.webCrawler;

import comsis.core.exception.InvalidAffiliationException;
import comsis.core.model.Author;
import comsis.core.model.PublicationData;
import comsis.core.model.comsis.PublicationPage;

import java.util.Arrays;
import java.util.List;

public class PublicationPageDataParser {

    public static PublicationData parsePublication(PublicationPage publicationPage, String downloadSiteUrl) throws InvalidAffiliationException {
        String affiliationsSection = publicationPage.getAffiliationsSection();
        String authorsSection = publicationPage.getAuthorsSection();

        List<Author> authors = AuthorsPageDataParser.parseAuthors(authorsSection, affiliationsSection);

        String downloadPath = downloadSiteUrl + publicationPage.getPublicationDownloadRelativePath();

        String[] publicationKeyWords = Arrays.stream(publicationPage.getKeyWords().split("[,;]"))
                .map(String::toLowerCase).map(String::trim).toArray(String[]::new);

        return new PublicationData(publicationPage.getPublicationTitle(),
                publicationPage.getPublicationAbstract(), publicationKeyWords, authors, downloadPath);
    }
}
