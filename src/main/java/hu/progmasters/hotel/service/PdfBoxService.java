package hu.progmasters.hotel.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.*;
import java.io.IOException;

@Service
@Transactional
public class PdfBoxService {

    public byte[] generatePdf(String title, String content) {
        // Create a PDF document
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        Color background = new Color(30, 26, 26);
        Color font = new Color(249, 195, 7);

        // Create content for the PDF based on the DTO
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setNonStrokingColor(background);
            contentStream.addRect(0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
            contentStream.fill();
            contentStream.beginText();
            contentStream.setNonStrokingColor(font);
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 15);
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText(title);
            contentStream.endText();
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 600);
            contentStream.showText(content);
            contentStream.endText();
        } catch (Exception e) {
            throw new RuntimeException("Content creation for the PDF was not successful");
        }

        // Save the PDF to a byte array
        ByteArrayOutputStream savedPdf = null;
        try {
            savedPdf = new ByteArrayOutputStream();
            document.save(savedPdf);
            document.close();
        } catch (IOException e) {
            throw new RuntimeException("PDF could not be saved to byte array");
        }

        return savedPdf.toByteArray();
    }
}
