package comsis.service.webCrawler;

import comsis.core.model.comsis.WebPageData;
import comsis.common.structure.SynchronizedQueue;
import comsis.common.structure.SynchronizedSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class CrawlerThread extends Thread {

    private int id;
    private String seedUrl;
    private SynchronizedQueue<WebPageData> urlQueue;
    private boolean waitingForUrls;
    private DocumentProcessor documentProcessor;

    public CrawlerThread(int id, String seedUrl, SynchronizedQueue urlQueue, SynchronizedSet visitedUrls, SynchronizedSet loadedPublications){
        this.id = id;
        this.seedUrl = seedUrl;
        this.urlQueue = urlQueue;
        this.documentProcessor = new DocumentProcessor(urlQueue, visitedUrls, loadedPublications);
    }

    @Override
    public void run() {
        WebPageData currentPage = null;
        try {
            while(true) {
                currentPage = getPageAsync();

                String url = currentPage.getUrl();
                int depth = currentPage.getDepth();

                Document document = null;
                try {
                    document = Jsoup.connect(url).get();
                }
                catch (IOException ioe) {
                    continue;
                }

                documentProcessor.processDocument(document, depth);
            }
        } catch (InterruptedException e) {
            System.out.println("Crawler thread " + id + " interrupted");
        }
    }

    public boolean isWaitingForUrls() {
        return waitingForUrls;
    }

    private WebPageData getPageAsync() throws InterruptedException {
        synchronized (urlQueue) {
            while (urlQueue.isEmpty()) {
                waitingForUrls = true;
                urlQueue.wait();
            }

            waitingForUrls = false;
            return urlQueue.removeFirst();
        }
    }
}
