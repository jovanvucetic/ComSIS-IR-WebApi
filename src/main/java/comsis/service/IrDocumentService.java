package comsis.service;

import comsis.core.serviceInterface.DocumentService;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class IrDocumentService implements DocumentService {

    private String documentRepositoryFolderPath;

    public IrDocumentService(@Value("${document.repository.folder.path}")String documentRepositoryFolderPath) {
        this.documentRepositoryFolderPath = documentRepositoryFolderPath;
    }

    @Override
    public void downloadPdfIfNotExist(UUID documentId, String downloadUrlPath) {
        String documentPath = getPdfPath(documentId);

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
    public void parseAndSavePdfText(UUID documentId) {
        String pdfContent = parsePdfTextContent(documentId);

        if(pdfContent.equals("")) {
            return;
        }

        String txtFilePath = getTxtFilePath(documentId);
        File file = new File(txtFilePath);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(pdfContent);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String readDocumentContent(UUID documentId) {
        String txtFilePath = getTxtFilePath(documentId);

        String data = "";
        try {
            data = new String(Files.readAllBytes(Paths.get(txtFilePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    private String parsePdfTextContent(UUID documentId) {
        PDFParser parser;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        PDFTextStripper pdfStripper;

        String parsedText = "";
        String filePath = getPdfPath(documentId);
        File file = new File(filePath);
        try {
            parser = new PDFParser(new RandomAccessFile(file, "r"));
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);
            parsedText.replaceAll("[^A-Za-z0-9. ]+", "");
        } catch (Exception e) {
            System.out.println("Document cannot be found for document id: " + documentId);
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

    private String getPdfPath(UUID documentId) {
        return documentRepositoryFolderPath + "\\" + documentId + ".pdf";
    }

    private String getTxtFilePath(UUID documentId) {return documentRepositoryFolderPath + "\\" + documentId + ".txt";}
}
