package comsis.service;

import comsis.core.serviceInterface.DocumentService;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.UUID;

@Service
public class IrDocumentService implements DocumentService {

    private String documentRepositoryFolderPath;

    public IrDocumentService(@Value("${document.repository.folder.path}")String documentRepositoryFolderPath) {
        this.documentRepositoryFolderPath = documentRepositoryFolderPath;
    }

    @Override
    public String getDocumentPath(UUID documentId) {
        return documentRepositoryFolderPath + "\\" + documentId + ".pdf";
    }

    @Override
    public void downloadPdfIfNotExist(UUID documentId, String downloadUrlPath) {
        String documentPath = getDocumentPath(documentId);

        File pdf = new File(documentPath);
        if(pdf.exists()) {
            return;
        }

        try (BufferedInputStream in = new BufferedInputStream(new URL(downloadUrlPath).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(documentPath)) {
                byte dataBuffer[] = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
        } catch (IOException e) {
            System.out.println("Pdf download failed for " + documentId + " ----> " + downloadUrlPath);
        }
    }

    @Override
    public String readPdf(UUID documentId) {
        PDFParser parser = null;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        PDFTextStripper pdfStripper;

        String parsedText = "";
        String fileName = getDocumentPath(documentId);
        File file = new File(fileName);
        try {
            parser = new PDFParser(new RandomAccessFile(file, "r"));
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);
            parsedText.replaceAll("[^A-Za-z0-9. ]+", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (cosDoc != null)
                    cosDoc.close();
                if (pdDoc != null)
                    pdDoc.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        return parsedText;
    }
}
