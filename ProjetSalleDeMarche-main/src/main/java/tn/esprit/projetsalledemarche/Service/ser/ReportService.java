////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//package tn.esprit.projetsalledemarche.Service.ser;
//
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Paragraph;
//import com.itextpdf.layout.element.Table;
//import com.itextpdf.layout.properties.TextAlignment;
//import com.itextpdf.layout.properties.UnitValue;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import tn.esprit.projetsalledemarche.Entity.ProduitAssurance;
//import tn.esprit.projetsalledemarche.Entity.Sinistre;
//import tn.esprit.projetsalledemarche.Repository.ProduitAssuranceRepository;
//import tn.esprit.projetsalledemarche.Service.IMP.IReportService;
//
//import java.io.ByteArrayOutputStream;
//import java.nio.file.Files;
//import java.nio.file.OpenOption;
//import java.nio.file.Paths;
//import java.util.Iterator;
//
//@Service
//public class ReportService  {
//    @Autowired
//    private final ProduitAssuranceRepository produitAssuranceRepository;
//
//    @Autowired
//    public ReportService(ProduitAssuranceRepository produitAssuranceRepository) {
//        this.produitAssuranceRepository = produitAssuranceRepository;
//    }
//
//    public byte[] generateProductReport(String nomProduit) {
//        ProduitAssurance produitAssurance = this.produitAssuranceRepository.findByNomProduit(nomProduit);
//        if (produitAssurance == null) {
//            throw new RuntimeException("Produit Assurance introuvable pour le nom : " + nomProduit);
//        } else {
//            try {
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//                byte[] var17;
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
//                    document.add(productTable);
//                    document.add(new Paragraph("\n"));
//                    if (produitAssurance.getSinistres() != null && !produitAssurance.getSinistres().isEmpty()) {
//                        Paragraph sinistresTitle = (Paragraph)((Paragraph)(new Paragraph("Sinistres Associés")).setBold()).setFontSize(14.0F);
//                        document.add(sinistresTitle);
//                        Table sinistresTable = (Table)(new Table(UnitValue.createPercentArray(new float[]{2.0F, 2.0F, 2.0F, 2.0F}))).setWidth(UnitValue.createPercentValue(100.0F));
//                        sinistresTable.addCell("Date du Sinistre");
//                        sinistresTable.addCell("Montant");
//                        sinistresTable.addCell("Etat");
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
//                    document.close();
//                    Files.write(Paths.get("C:/Reports/rapport_" + nomProduit.replace(" ", "_") + ".pdf"), baos.toByteArray(), new OpenOption[0]);
//                    var17 = baos.toByteArray();
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
//                return var17;
//            } catch (Exception var16) {
//                throw new RuntimeException("Erreur lors de la génération du PDF : " + var16.getMessage(), var16);
//            }
//        }
//    }
//}
