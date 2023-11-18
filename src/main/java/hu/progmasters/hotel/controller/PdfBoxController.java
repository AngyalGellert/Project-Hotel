package hu.progmasters.hotel.controller;

import hu.progmasters.hotel.dto.request.Request1;
import hu.progmasters.hotel.service.PdfBoxService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/pdf")
public class PdfBoxController {

    private PdfBoxService pdfBoxService;

    @GetMapping
    public ResponseEntity<byte[]> generatePdf(@RequestBody Request1 request) throws Exception {
        // Generate the PDF using the PdfBoxService
        byte[] pdfBytes = pdfBoxService.generatePdf(request.getTitle(), request.getContent());

        // Set up the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "generated.pdf");

        // Return the PDF as a byte array in the response
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}
