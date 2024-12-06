package tn.esprit.projetsalledemarche.Controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetsalledemarche.Entity.ProduitAssurance;
import tn.esprit.projetsalledemarche.Entity.Sinistre;
import tn.esprit.projetsalledemarche.Repository.ProduitAssuranceRepository;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ReportController {

    private final ProduitAssuranceRepository produitAssuranceRepository;

    @Autowired
    public ReportController(ProduitAssuranceRepository produitAssuranceRepository) {
        this.produitAssuranceRepository = produitAssuranceRepository;
    }

    @GetMapping("/pdf/{nomProduit}")
    public ResponseEntity<byte[]> generatePdfByProductName(@PathVariable String nomProduit) {
        // Recherche du produit par nom
        ProduitAssurance produitAssurance = produitAssuranceRepository.findByNomProduit(nomProduit);
        if (produitAssurance == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // Création du document PDF
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Titre principal
            Paragraph title = new Paragraph("Rapport du Produit d'Assurance")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold()
                    .setFontSize(18);
            document.add(title);

            // Ajouter un espace
            document.add(new Paragraph("\n"));

            // Section : Détails du Produit
            Paragraph sectionTitle = new Paragraph("Détails du Produit")
                    .setBold()
                    .setFontSize(14);
            document.add(sectionTitle);

            Table productTable = new Table(UnitValue.createPercentArray(new float[]{2, 3}))
                    .setWidth(UnitValue.createPercentValue(100));
            productTable.addCell("Nom du Produit");
            productTable.addCell(produitAssurance.getNomProduit());
            productTable.addCell("Prime");
            productTable.addCell(produitAssurance.getPrime().toString());
            productTable.addCell("Couverture");
            productTable.addCell(produitAssurance.getCouverture().toString());
            productTable.addCell("Type d'Assurance");
            productTable.addCell(produitAssurance.getAtype().toString());
            productTable.addCell("Profil ID");
            productTable.addCell(String.valueOf(produitAssurance.getProfil().getId_Profil()));
            document.add(productTable);

            // Ajouter un espace
            document.add(new Paragraph("\n"));

            // Section : Sinistres associés
            if (produitAssurance.getSinistres() != null && !produitAssurance.getSinistres().isEmpty()) {
                Paragraph sinistresTitle = new Paragraph("Sinistres Associés")
                        .setBold()
                        .setFontSize(14);
                document.add(sinistresTitle);

                Table sinistresTable = new Table(UnitValue.createPercentArray(new float[]{2, 2, 2, 2}))
                        .setWidth(UnitValue.createPercentValue(100));
                sinistresTable.addCell("Date du Sinistre");
                sinistresTable.addCell("Montant");
                sinistresTable.addCell("État");
                sinistresTable.addCell("Produit d'Assurance");

                for (Sinistre sinistre : produitAssurance.getSinistres()) {
                    sinistresTable.addCell(sinistre.getDateSinistre().toString());
                    sinistresTable.addCell(sinistre.getMontantSinistre().toString());
                    sinistresTable.addCell(sinistre.getEtatSinistre());
                    sinistresTable.addCell(produitAssurance.getNomProduit());
                }
                document.add(sinistresTable);
            } else {
                document.add(new Paragraph("Aucun sinistre associé à ce produit."));
            }

            // Ajouter le pied de page
            Paragraph footer = new Paragraph("Généré le : " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(10);
            document.add(footer);

            // Fermer le document
            document.close();

            // Configurer les en-têtes de la réponse
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "rapport_" + nomProduit.replace(" ", "_") + ".pdf");

            // Retourner le fichier PDF sous forme de tableau de bytes
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(baos.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
