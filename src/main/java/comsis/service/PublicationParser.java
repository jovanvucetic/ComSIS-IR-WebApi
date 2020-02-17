package comsis.service;

import comsis.core.model.Publication;
import comsis.core.model.PublicationIndexModel;

public class PublicationParser {
    public static Publication parsePublication(PublicationIndexModel model) {
        if(model == null) {
            return  null;
        }

        Publication publication = new Publication();

        publication.setId(model.getId());
        publication.setTitle(model.getTitle());
        publication.setAuthors(model.getAuthors());
        publication.setPublicationAbstract(model.getPublicationAbstract());
        publication.setDownloadPath(model.getDocumentDownloadPath());
        publication.setYear(Integer.parseInt(model.getYear()));
        publication.setKeyWords(model.getKeyWords().split(","));

        return publication;
    }
}
