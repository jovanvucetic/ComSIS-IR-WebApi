package comsis.core.serviceInterface;


import java.util.UUID;

public interface DocumentService {
    String getDocumentPath(UUID documentId);

    void downloadPdfIfNotExist(UUID documentId, String downloadUrlPath);

    String readPdf(UUID documentId);
}
