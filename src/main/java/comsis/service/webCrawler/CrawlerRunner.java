package comsis.service.webCrawler;

import comsis.core.model.PageInfo;
import comsis.core.model.PublicationPage;
import comsis.core.serviceInterface.WebCrawler;
import comsis.common.structure.SynchronizedQueue;
import comsis.common.structure.SynchronizedSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
public class CrawlerRunner implements WebCrawler {
    private SynchronizedQueue<PageInfo> urlProcessingQueue = new SynchronizedQueue();
    private SynchronizedSet<String> visitedUrls = new SynchronizedSet<>();
    private SynchronizedSet<PublicationPage> loadedPublications = new SynchronizedSet<>();

    private ArrayList<CrawlerThread> workers = new ArrayList<>();

    public CrawlerRunner(@Value("${crawler.seed.url}")String seedUrl,
                         @Value("${number.of.crawlers}") int numberOfWorkers) {
        urlProcessingQueue.add(new PageInfo(seedUrl, 0));
        visitedUrls.add(seedUrl);

        for(int i = 0; i < numberOfWorkers; i ++) {
            CrawlerThread crawlerThread = new CrawlerThread(i, seedUrl, urlProcessingQueue, visitedUrls, loadedPublications);
            workers.add(crawlerThread);
        }
    }

    public Set<PublicationPage> searchForPublications() throws InterruptedException {
        for (int i = 0; i < workers.size(); i++) {
            workers.get(i).start();
        }

        boolean finished;
        do {
            Thread.sleep(1000);
            finished = true;

            for(CrawlerThread crawlerThread : workers) {
                if(crawlerThread.isAlive() && !crawlerThread.isVaitingForUrls()) {
                    finished = false;
                }
            }
        }while(!finished);

        for(CrawlerThread crawlerThread : workers) {
            crawlerThread.interrupt();
        }

        return loadedPublications.asSet();
    }
}
