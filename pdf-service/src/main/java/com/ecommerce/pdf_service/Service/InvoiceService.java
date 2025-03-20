// src/main/java/com/example/ecommerce/service/InvoiceService.java
package com.ecommerce.pdf_service.Service;

import com.ecommerce.pdf_service.Entity.Invoice;
import com.ecommerce.pdf_service.Repository.InvoiceRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice createInvoice(Invoice invoice) {
        // Generate PDF and store it in the invoice object
        ByteArrayOutputStream pdfStream = generatePdf(invoice);
        invoice.setPdfData(pdfStream.toByteArray());

        // Save invoice to MongoDB
        return invoiceRepository.save(invoice);
    }

    public ByteArrayOutputStream generatePdf(Invoice invoice) {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Add content to the PDF
            document.add(new Paragraph("Invoice", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24)));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Invoice ID: " + invoice.getId()));
            document.add(new Paragraph("Order ID: " + invoice.getOrderId()));
            document.add(new Paragraph("Customer Name: " + invoice.getCustomerName()));
            document.add(new Paragraph("Product: " + invoice.getProduct()));
            document.add(new Paragraph("Amount: $" + invoice.getAmount()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Thank you for your purchase!"));

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return outputStream;
    }

    public byte[] getPdfById(String id) {
        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        return (invoice != null) ? invoice.getPdfData() : null;
    }
}