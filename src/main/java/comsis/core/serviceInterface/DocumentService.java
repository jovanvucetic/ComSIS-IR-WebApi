package comsis.core.serviceInterface;


import java.util.UUID;

public interface DocumentService {
    void downloadPdfIfNotExist(UUID documentId, String downloadUrlPath);

    void parseAndSavePdfText(UUID documentId);

    String readDocumentContent(UUID documentId);
}
