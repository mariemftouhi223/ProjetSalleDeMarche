//package tn.esprit.projetsalledemarche.Controller;
//
//
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Paragraph;
//import com.itextpdf.layout.element.Table;
//import com.itextpdf.layout.properties.TextAlignment;
//import com.itextpdf.layout.properties.UnitValue;
//import java.io.ByteArrayOutputStream;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Iterator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import tn.esprit.projetsalledemarche.Entity.ProduitAssurance;
//import tn.esprit.projetsalledemarche.Entity.Sinistre;
//import tn.esprit.projetsalledemarche.Repository.ProduitAssuranceRepository;
//
//@RestController
//@RequestMapping({"/api"})
//@CrossOrigin({"*"})
//public class ReportController {
//    private final ProduitAssuranceRepository produitAssuranceRepository;
//
//    @Autowired
//    public ReportController(ProduitAssuranceRepository produitAssuranceRepository) {
//        this.produitAssuranceRepository = produitAssuranceRepository;
//    }
//
//    @GetMapping({"/pdf/{nomProduit}"})
//    public ResponseEntity<byte[]> generatePdfByProductName(@PathVariable String nomProduit) {
//        ProduitAssurance produitAssurance = this.produitAssuranceRepository.findByNomProduit(nomProduit);
//        if (produitAssurance == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body((Object)null);
//        } else {
//            try {
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//                ResponseEntity var18;
//                try {
//                    PdfWriter writer = new PdfWriter(baos);
//                    PdfDocument pdf = new PdfDocument(writer);
//                    Document document = new Document(pdf);
//                    Paragraph title = (Paragraph)((Paragraph)((Paragraph)(new Paragraph("Rapport du Produit d'Assurance")).setTextAlignment(TextAlignment.CENTER)).setBold()).setFontSize(18.0F);
//                    document.add(title);
//                    document.add(new Paragraph("\n"));
//                    Paragraph sectionTitle = (Paragraph)((Paragraph)(new Paragraph("Détails du Produit")).setBold()).setFontSize(14.0F);
//                    document.add(sectionTitle);
//                    Table productTable = (Table)(new Table(UnitValue.createPercentArray(new float[]{2.0F, 3.0F}))).setWidth(UnitValue.createPercentValue(100.0F));
//                    productTable.addCell("Nom du Produit");
//                    productTable.addCell(produitAssurance.getNomProduit());
//                    productTable.addCell("Prime");
//                    productTable.addCell(produitAssurance.getPrime().toString());
//                    productTable.addCell("Couverture");
//                    productTable.addCell(produitAssurance.getCouverture().toString());
//                    productTable.addCell("Type d'Assurance");
//                    productTable.addCell(produitAssurance.getAtype().toString());
//                    productTable.addCell("Profil ID");
//                    productTable.addCell(String.valueOf(produitAssurance.getProfil().getId_Profil()));
//                    document.add(productTable);
//                    document.add(new Paragraph("\n"));
//                    Paragraph footer;
//                    if (produitAssurance.getSinistres() != null && !produitAssurance.getSinistres().isEmpty()) {
//                        footer = (Paragraph)((Paragraph)(new Paragraph("Sinistres Associés")).setBold()).setFontSize(14.0F);
//                        document.add(footer);
//                        Table sinistresTable = (Table)(new Table(UnitValue.createPercentArray(new float[]{2.0F, 2.0F, 2.0F, 2.0F}))).setWidth(UnitValue.createPercentValue(100.0F));
//                        sinistresTable.addCell("Date du Sinistre");
//                        sinistresTable.addCell("Montant");
//                        sinistresTable.addCell("État");
//                        sinistresTable.addCell("Produit d'Assurance");
//                        Iterator var12 = produitAssurance.getSinistres().iterator();
//
//                        while(var12.hasNext()) {
//                            Sinistre sinistre = (Sinistre)var12.next();
//                            sinistresTable.addCell(sinistre.getDateSinistre().toString());
//                            sinistresTable.addCell(sinistre.getMontantSinistre().toString());
//                            sinistresTable.addCell(sinistre.getEtatSinistre());
//                            sinistresTable.addCell(produitAssurance.getNomProduit());
//                        }
//
//                        document.add(sinistresTable);
//                    } else {
//                        document.add(new Paragraph("Aucun sinistre associé à ce produit."));
//                    }
//
//                    LocalDateTime var10002 = LocalDateTime.now();
//                    footer = (Paragraph)((Paragraph)(new Paragraph("Généré le : " + var10002.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))).setTextAlignment(TextAlignment.CENTER)).setFontSize(10.0F);
//                    document.add(footer);
//                    document.close();
//                    HttpHeaders headers = new HttpHeaders();
//                    headers.setContentType(MediaType.APPLICATION_PDF);
//                    headers.setContentDispositionFormData("attachment", "rapport_" + nomProduit.replace(" ", "_") + ".pdf");
//                    var18 = ((ResponseEntity.BodyBuilder)ResponseEntity.ok().headers(headers)).contentType(MediaType.APPLICATION_PDF).body(baos.toByteArray());
//                } catch (Throwable var15) {
//                    try {
//                        baos.close();
//                    } catch (Throwable var14) {
//                        var15.addSuppressed(var14);
//                    }
//
//                    throw var15;
//                }
//
//                baos.close();
//                return var18;
//            } catch (Exception var16) {
//                var16.printStackTrace();
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((Object)null);
//            }
//        }
//    }
//}
