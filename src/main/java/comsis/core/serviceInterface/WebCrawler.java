package comsis.core.serviceInterface;

import comsis.core.model.PaperPage;

import java.util.Set;

public interface WebCrawler {
    Set<PaperPage> searchForPapers() throws InterruptedException;
}
