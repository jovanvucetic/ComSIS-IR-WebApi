package comsis.service;

import comsis.core.model.Publication;
import comsis.core.serviceInterface.DocumentService;
import comsis.core.serviceInterface.PublicationIndexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class IrPublicationIndexer implements PublicationIndexer {

    @Autowired
    private DocumentService documentService;

    @Override
    public void indexPublications(Collection<Publication> publications) {
        publications.forEach(publication -> indexPublication(publication));
    }

    private void indexPublication(Publication publication){
        UUID publicationId = publication.getId();
        documentService.downloadPdfIfNotExist(publicationId, publication.getDownloadPath());
        String publicationPdfText = documentService.readPdf(publicationId);
    }
}
