package comsis.service.webCrawler;

import comsis.core.model.PageInfo;
import comsis.core.model.PaperPage;
import comsis.core.structure.SynchronizedQueue;
import comsis.core.structure.SynchronizedSet;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DocumentProcessor {
    private static final String PAPER_SELECTOR = "a[href*=archive.php?show=ppr]";
    private static final String PAPER_AUTHOR_SELECTOR = "p.authors";
    private static final String PAPER_TITLE_SELECTOR = "h1.title";
    private static final String PAPER_ABSTRACT_SELECTOR = "h3:contains(Abstract)";
    private static final String ISSUE_SELECTOR = "a[href*=archive.php?show=vol]";


    private SynchronizedQueue<PageInfo> urlQueue;
    private SynchronizedSet<String> visitedUrls;
    private SynchronizedSet<PaperPage> loadedPapers;

    public DocumentProcessor(SynchronizedQueue urlQueue, SynchronizedSet visitedUrls, SynchronizedSet loadedPapers){
        this.urlQueue = urlQueue;
        this.visitedUrls = visitedUrls;
        this.loadedPapers = loadedPapers;
    }

    public void processDocument(Document document, int depth) {
        switch (depth) {
            case 0:
                loadIssue(document);
            case 1:
                loadPapers(document);
                break;
            case 2:
                extractPaperData(document);
                break;
            default:
                throw new IllegalArgumentException("Depth of web crawler is invalid");
        }
    }

    private void loadIssue(Document document) {
        Elements paperLinks = document.select(ISSUE_SELECTOR);
        boolean newPaperLoaded = false;

        for(Element issueLink : paperLinks) {
            String linkUrl = issueLink.attr("abs:href");
            if(!visitedUrls.contains(linkUrl)) {
                newPaperLoaded = true;
                visitedUrls.add(linkUrl);
                urlQueue.add(new PageInfo(linkUrl, 1));
            }
        }

        if(newPaperLoaded) {
            synchronized (urlQueue) {
                urlQueue.notify();
            }
        }
    }

    private void extractPaperData(Document document) {
        String paperTitle = document.select(PAPER_TITLE_SELECTOR).text();
        String paperAuthors = document.select(PAPER_AUTHOR_SELECTOR).html();
        String institutions = document.select(PAPER_AUTHOR_SELECTOR).next().html();
        String paperAbstract = document.select(PAPER_ABSTRACT_SELECTOR).next("p").text();

        PaperPage paperPage = new PaperPage(paperTitle, paperAuthors, institutions, paperAbstract);
        loadedPapers.add(paperPage);
    }

    private void loadPapers(Document document) {
        Elements paperLinks = document.select(PAPER_SELECTOR);
        boolean newPaperLoaded = false;

        for(Element paperLink : paperLinks) {
            String linkUrl = paperLink.attr("abs:href");
            if(!visitedUrls.contains(linkUrl)) {
                newPaperLoaded = true;
                visitedUrls.add(linkUrl);
                urlQueue.add(new PageInfo(linkUrl, 2));
            }
        }

        if(newPaperLoaded) {
            synchronized (urlQueue) {
                urlQueue.notify();
            }
        }
    }
}
