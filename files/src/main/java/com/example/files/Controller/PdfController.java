package com.example.files.Controller;
import com.example.files.Models.ProductModel;
import com.example.files.Repository.ProductRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

@Controller
public class PdfController {
	
    @Autowired
    private ProductRepository productRepository;
    // Generate PDF for a specific product
    @GetMapping("/pdf/{id}")
    public void generateProductPdf(@PathVariable Integer id, HttpServletResponse response) throws Exception {
        Optional<ProductModel> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            ProductModel product = optionalProduct.get();

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=product_" + product.getId() + ".pdf");

            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, 750);
                    contentStream.showText("Product Details");
                    contentStream.endText();

                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    int yPosition = 720;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText("Name: " + (product.getName() != null ? product.getName() : "N/A"));
                    contentStream.endText();

                    yPosition -= 20;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText("Description: " + (product.getDescription() != null ? product.getDescription() : "N/A"));
                    contentStream.endText();

                    yPosition -= 20;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText("Price: " + (product.getPrice() != null ? product.getPrice() : "N/A"));
                    contentStream.endText();

                    yPosition -= 20;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText("Expiry Date: " + (product.getExpirydate() != null ? product.getExpirydate() : "N/A"));
                    contentStream.endText();
                }

                document.save(response.getOutputStream());
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
        }
    }
}