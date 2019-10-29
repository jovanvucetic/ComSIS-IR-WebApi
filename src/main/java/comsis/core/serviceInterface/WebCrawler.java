package comsis.core.serviceInterface;

import comsis.core.model.comsis.PublicationPage;

import java.util.Set;

public interface WebCrawler {
    Set<PublicationPage> searchForPublications() throws InterruptedException;
}
