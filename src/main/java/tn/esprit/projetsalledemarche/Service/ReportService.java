package tn.esprit.projetsalledemarche.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.ProduitAssurance;
import tn.esprit.projetsalledemarche.Entity.Sinistre;
import tn.esprit.projetsalledemarche.Repository.ProduitAssuranceRepository;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ReportService implements IReportService {

    private final ProduitAssuranceRepository produitAssuranceRepository;

    @Autowired
    public ReportService(ProduitAssuranceRepository produitAssuranceRepository) {
        this.produitAssuranceRepository = produitAssuranceRepository;
    }

    @Override
    public byte[] generateProductReport(String nomProduit) {
        // Récupérer les produits d'assurance correspondant au nom
        List<ProduitAssurance> produitsAssurance = produitAssuranceRepository.findByNomProduit(nomProduit);

        if (produitsAssurance == null || produitsAssurance.isEmpty()) {
            throw new RuntimeException("Produit Assurance introuvable pour le nom : " + nomProduit);
        }

        if (produitsAssurance.size() > 1) {
            throw new RuntimeException("Plusieurs produits trouvés pour le nom : " + nomProduit);
        }

        ProduitAssurance produitAssurance = produitsAssurance.get(0); // Sélectionner le premier produit

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
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
            productTable.addCell(produitAssurance.getPrime() != null ? produitAssurance.getPrime().toString() : "N/A");
            productTable.addCell("Couverture");
            productTable.addCell(produitAssurance.getCouverture() != null ? produitAssurance.getCouverture().toString() : "N/A");
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
                sinistresTable.addCell("Etat");
                sinistresTable.addCell("Produit d'Assurance");

                for (Sinistre sinistre : produitAssurance.getSinistres()) {
                    sinistresTable.addCell(sinistre.getDateSinistre() != null ? sinistre.getDateSinistre().toString() : "N/A");
                    sinistresTable.addCell(sinistre.getMontantSinistre() != null ? sinistre.getMontantSinistre().toString() : "N/A");
                    sinistresTable.addCell(sinistre.getEtatSinistre() != null ? sinistre.getEtatSinistre() : "N/A");
                    sinistresTable.addCell(produitAssurance.getNomProduit());
                }
                document.add(sinistresTable);
            } else {
                document.add(new Paragraph("Aucun sinistre associé à ce produit."));
            }

            // Fermer le document
            document.close();

            // Enregistrement local
            Path outputPath = Paths.get("C:/Reports/");
            if (!Files.exists(outputPath)) {
                Files.createDirectories(outputPath); // Crée le répertoire s'il n'existe pas
            }

            Files.write(outputPath.resolve("rapport_" + nomProduit.replace(" ", "_") + ".pdf"), baos.toByteArray());

            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du PDF : " + e.getMessage(), e);
        }
    }
}
