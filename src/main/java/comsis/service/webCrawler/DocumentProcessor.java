package comsis.service.webCrawler;

import comsis.core.model.comsis.WebPageData;
import comsis.core.model.comsis.PublicationPage;
import comsis.common.structure.SynchronizedQueue;
import comsis.common.structure.SynchronizedSet;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DocumentProcessor {
    private static final String PUBLICATION_SELECTOR = "a[href*=archive.php?show=ppr]";
    private static final String PUBLICATION_AUTHOR_SELECTOR = "p.authors";
    private static final String PUBLICATION_TITLE_SELECTOR = "h1.title";
    private static final String PUBLICATION_ABSTRACT_SELECTOR = "h3:contains(Abstract)";
    private static final String ISSUE_SELECTOR = "a[href*=archive.php?show=vol]";
    private static final String PUBLICATION_AFFILIATIONS_SELECTOR = "ol";
    private static final String PUBLICATION_DOWNLOAD_LINK = "a.download";

    private SynchronizedQueue<WebPageData> urlQueue;
    private SynchronizedSet<String> visitedUrls;
    private SynchronizedSet<PublicationPage> loadedPublications;

    public DocumentProcessor(SynchronizedQueue urlQueue, SynchronizedSet visitedUrls, SynchronizedSet loadedPublications){
        this.urlQueue = urlQueue;
        this.visitedUrls = visitedUrls;
        this.loadedPublications = loadedPublications;
    }

    public void processDocument(Document document, int depth) {
        switch (depth) {
            case 0:
                loadIssue(document);
            case 1:
                loadPublications(document);
                break;
            case 2:
                extractPublicationData(document);
                break;
            default:
                throw new IllegalArgumentException("Depth of web crawler is invalid");
        }
    }

    private void loadIssue(Document document) {
        Elements publicationLinks = document.select(ISSUE_SELECTOR);
        boolean newPublicationLoaded = false;

        for(Element issueLink : publicationLinks) {
            String linkUrl = issueLink.attr("abs:href");
            if(!visitedUrls.contains(linkUrl)) {
                newPublicationLoaded = true;
                visitedUrls.add(linkUrl);
                urlQueue.add(new WebPageData(linkUrl, 1));
            }
        }

        if(newPublicationLoaded) {
            synchronized (urlQueue) {
                urlQueue.notify();
            }
        }
    }

    private void extractPublicationData(Document document) {
        String publicationTitle = document.select(PUBLICATION_TITLE_SELECTOR).text();
        String publicationAuthors = document.select(PUBLICATION_AUTHOR_SELECTOR).html();
        String affiliations = document.select(PUBLICATION_AFFILIATIONS_SELECTOR).html();
        String publicationAbstract = document.select(PUBLICATION_ABSTRACT_SELECTOR).next("p").text();
        String publicationRelativeDownloadPath = document.select(PUBLICATION_DOWNLOAD_LINK).attr("href");

        PublicationPage publicationPage = new PublicationPage(publicationTitle, publicationAuthors,
                affiliations, publicationAbstract, publicationRelativeDownloadPath, document.baseUri());
        loadedPublications.add(publicationPage);
    }

    private void loadPublications(Document document) {
        Elements publicationLinks = document.select(PUBLICATION_SELECTOR);
        boolean newPublicationLoaded = false;

        for(Element publicationLink : publicationLinks) {
            String linkUrl = publicationLink.attr("abs:href");
            if(!visitedUrls.contains(linkUrl)) {
                newPublicationLoaded = true;
                visitedUrls.add(linkUrl);
                urlQueue.add(new WebPageData(linkUrl, 2));
            }
        }

        if(newPublicationLoaded) {
            synchronized (urlQueue) {
                urlQueue.notify();
            }
        }
    }
}
