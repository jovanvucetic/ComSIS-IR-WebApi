package comsis.service.webCrawler;

import comsis.core.model.PageInfo;
import comsis.core.structure.SynchronizedQueue;
import comsis.core.structure.SynchronizedSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class CrawlerThread extends Thread {

    private int id;
    private String seedUrl;
    private SynchronizedQueue<PageInfo> urlQueue;
    private boolean waitingForUrls;
    private DocumentProcessor documentProcessor;

    public CrawlerThread(int id, String seedUrl, SynchronizedQueue urlQueue, SynchronizedSet visitedUrls, SynchronizedSet loadedPapers){
        this.id = id;
        this.seedUrl = seedUrl;
        this.urlQueue = urlQueue;
        this.documentProcessor = new DocumentProcessor(urlQueue, visitedUrls, loadedPapers);
    }

    @Override
    public void run() {
        PageInfo currentPage = null;
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

    public boolean isVaitingForUrls() {
        return waitingForUrls;
    }

    private PageInfo getPageAsync() throws InterruptedException {
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
