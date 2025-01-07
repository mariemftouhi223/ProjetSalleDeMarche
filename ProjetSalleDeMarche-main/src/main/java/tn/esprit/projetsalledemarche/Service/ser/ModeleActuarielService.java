//
//
//package tn.esprit.projetsalledemarche.Service.ser;
//
//import com.opencsv.CSVReader;
//import com.opencsv.exceptions.CsvException;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.nio.file.Files;
//import java.nio.file.LinkOption;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Base64;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//import javax.imageio.ImageIO;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import tn.esprit.projetsalledemarche.Entity.ModeleActuariel;
//import tn.esprit.projetsalledemarche.Repository.ModeleActuarielRepository;
//import tn.esprit.projetsalledemarche.Service.IMP.IModeleActuarielService;
//
//@Service
//public class ModeleActuarielService implements IModeleActuarielService {
//    private static final String PREDICTIONS_FOLDER = "predictions";
//    private final ModeleActuarielRepository modeleActuarielRepository;
//
//    @Autowired
//    public ModeleActuarielService(ModeleActuarielRepository modeleActuarielRepository) {
//        this.modeleActuarielRepository = modeleActuarielRepository;
//    }
//
//    public String generatePredictionChart(String nomActif) {
//        try {
//            Path filePath = Paths.get("predictions", nomActif + "_predictions.csv");
//            if (Files.notExists(filePath, new LinkOption[0])) {
//                throw new RuntimeException("Fichier de prédiction introuvable pour l'actif : " + nomActif);
//            } else {
//                XYSeries series = new XYSeries("Prédictions " + nomActif);
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                CSVReader csvReader = new CSVReader(Files.newBufferedReader(filePath));
//
//                try {
//                    List<String[]> rows = csvReader.readAll();
//
//                    for(int i = 1; i < rows.size(); ++i) {
//                        String[] row = (String[])rows.get(i);
//                        if (row.length < 2) {
//                            throw new RuntimeException("Ligne de données mal formatée dans le fichier : " + filePath);
//                        }
//
//                        Date date = dateFormat.parse(row[0]);
//                        double timestamp = (double)date.getTime();
//                        double value = Double.parseDouble(row[1]);
//                        series.add(timestamp, value);
//                    }
//                } catch (Throwable var15) {
//                    try {
//                        csvReader.close();
//                    } catch (Throwable var14) {
//                        var15.addSuppressed(var14);
//                    }
//
//                    throw var15;
//                }
//
//                csvReader.close();
//                XYSeriesCollection dataset = new XYSeriesCollection(series);
//                JFreeChart var20 = ChartFactory.createXYLineChart("Courbe de Prédictions - " + nomActif, "Date", "Valeur Prédite", dataset, PlotOrientation.VERTICAL, true, true, false);
//                BufferedImage chartImage = var20.createBufferedImage(800, 600);
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                ImageIO.write(chartImage, "png", baos);
//                byte[] imageBytes = baos.toByteArray();
//                return Base64.getEncoder().encodeToString(imageBytes);
//            }
//        } catch (CsvException | IOException var16) {
//            throw new RuntimeException("Erreur lors de la génération du graphique pour " + nomActif + " : " + var16.getMessage(), var16);
//        } catch (NumberFormatException var17) {
//            throw new RuntimeException("Erreur de format de données dans le fichier CSV : " + var17.getMessage(), var17);
//        } catch (ParseException var18) {
//            throw new RuntimeException("Erreur de parsing de la date dans le fichier CSV : " + var18.getMessage(), var18);
//        }
//    }
//
//    public List<String[]> getPredictionsByAssetName(String nomActif) {
//        Path filePath = Paths.get("predictions", nomActif + "_predictions.csv");
//        if (Files.notExists(filePath, new LinkOption[0])) {
//            throw new RuntimeException("Fichier de prédiction introuvable pour l'actif : " + nomActif);
//        } else {
//            try {
//                CSVReader csvReader = new CSVReader(Files.newBufferedReader(filePath));
//
//                List var4;
//                try {
//                    var4 = csvReader.readAll();
//                } catch (Throwable var7) {
//                    try {
//                        csvReader.close();
//                    } catch (Throwable var6) {
//                        var7.addSuppressed(var6);
//                    }
//
//                    throw var7;
//                }
//
//                csvReader.close();
//                return var4;
//            } catch (CsvException | IOException var8) {
//                throw new RuntimeException("Erreur lors de la lecture du fichier de prédiction pour " + nomActif, var8);
//            }
//        }
//    }
//
//    public List<String[]> getPredictionsByAssetName1(String nomActif, Date dateCalcul) {
//        Path filePath = Paths.get("predictions", nomActif + "_predictions.csv");
//        if (Files.notExists(filePath, new LinkOption[0])) {
//            throw new RuntimeException("Fichier de prédiction introuvable pour l'actif : " + nomActif);
//        } else {
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(dateCalcul);
//            calendar.add(3, -2);
//            Date dateStart = calendar.getTime();
//            calendar.setTime(dateCalcul);
//            calendar.add(3, 2);
//            Date dateEnd = calendar.getTime();
//            List<String[]> filteredPredictions = new ArrayList();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//            try {
//                CSVReader csvReader = new CSVReader(Files.newBufferedReader(filePath));
//
//                try {
//                    List<String[]> rows = csvReader.readAll();
//
//                    for(int i = 1; i < rows.size(); ++i) {
//                        String[] row = (String[])rows.get(i);
//                        Date predictionDate = dateFormat.parse(row[0]);
//                        if (predictionDate.compareTo(dateStart) >= 0 && predictionDate.compareTo(dateEnd) <= 0) {
//                            filteredPredictions.add(row);
//                        }
//                    }
//                } catch (Throwable var15) {
//                    try {
//                        csvReader.close();
//                    } catch (Throwable var14) {
//                        var15.addSuppressed(var14);
//                    }
//
//                    throw var15;
//                }
//
//                csvReader.close();
//                return filteredPredictions;
//            } catch (CsvException | ParseException | IOException var16) {
//                throw new RuntimeException("Erreur lors de la lecture ou du filtrage du fichier de prédiction pour " + nomActif, var16);
//            }
//        }
//    }
//
//    public List<Double> getPredictionsAroundDate(String nomActif, Date dateCalcul) {
//        long twoWeeksInMillis = 1209600000L;
//        long startDateInMillis = dateCalcul.getTime() - twoWeeksInMillis;
//        long endDateInMillis = dateCalcul.getTime() + twoWeeksInMillis;
//        List<String[]> predictions = this.getPredictionsByAssetName(nomActif);
//        List<Double> filteredPredictions = new ArrayList();
//        Iterator var11 = predictions.iterator();
//
//        while(var11.hasNext()) {
//            String[] row = (String[])var11.next();
//
//            try {
//                Date predictionDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(row[0]);
//                double value = Double.parseDouble(row[1]);
//                if (predictionDate.getTime() >= startDateInMillis && predictionDate.getTime() <= endDateInMillis) {
//                    filteredPredictions.add(value);
//                }
//            } catch (NumberFormatException | ParseException var16) {
//                System.err.println("Erreur dans le format des données à la ligne : " + var16.getMessage());
//            }
//        }
//
//        return filteredPredictions;
//    }
//
//    public BigDecimal calculerValeurEstimeeInitiale(String nomActif) {
//        List<String[]> toutesPredictions = this.getPredictionsByAssetName(nomActif);
//        double somme = 0.0;
//        int count = 0;
//        Iterator var6 = toutesPredictions.iterator();
//
//        while(var6.hasNext()) {
//            String[] row = (String[])var6.next();
//
//            try {
//                double valeurPrediction = Double.parseDouble(row[1]);
//                somme += valeurPrediction;
//                ++count;
//            } catch (NumberFormatException var10) {
//                System.out.println("Erreur de format dans les prédictions : " + var10.getMessage());
//            }
//        }
//
//        double moyenne = count > 0 ? somme / (double)count : 0.0;
//        return BigDecimal.valueOf(moyenne);
//    }
//
//    public BigDecimal calculateValeurEstimee(String nomActif, Date dateCalcul, boolean saveToDatabase) {
//        List<Double> predictionsAroundDate = this.getPredictionsAroundDate(nomActif, dateCalcul);
//        if (predictionsAroundDate.isEmpty()) {
//            throw new RuntimeException("Aucune prédiction trouvée autour de la date spécifiée.");
//        } else {
//            double sum = 0.0;
//
//            double value;
//            for(Iterator var7 = predictionsAroundDate.iterator(); var7.hasNext(); sum += value) {
//                value = (Double)var7.next();
//            }
//
//            double averagePrediction = sum / (double)predictionsAroundDate.size();
//            BigDecimal valeurEstimee = BigDecimal.valueOf(averagePrediction);
//            if (saveToDatabase) {
//                ModeleActuariel modeleActuariel = new ModeleActuariel();
//                modeleActuariel.setNomActif(nomActif);
//                modeleActuariel.setDateCalcul(dateCalcul);
//                modeleActuariel.setValeurEstimee(valeurEstimee);
//                this.modeleActuarielRepository.save(modeleActuariel);
//            }
//
//            return valeurEstimee;
//        }
//    }
//
//    public BigDecimal processAndSaveValeurEstimee(String nomActif, Date dateCalcul, boolean saveToDatabase) {
//        List<Double> predictionsAroundDate = this.getPredictionsAroundDate(nomActif, dateCalcul);
//        if (predictionsAroundDate.isEmpty()) {
//            throw new RuntimeException("Aucune prédiction trouvée autour de la date spécifiée.");
//        } else {
//            double sum = 0.0;
//
//            double value;
//            for(Iterator var7 = predictionsAroundDate.iterator(); var7.hasNext(); sum += value) {
//                value = (Double)var7.next();
//            }
//
//            double averagePrediction = sum / (double)predictionsAroundDate.size();
//            BigDecimal valeurEstimee = BigDecimal.valueOf(averagePrediction);
//            if (saveToDatabase) {
//                ModeleActuariel modeleActuariel = new ModeleActuariel();
//                modeleActuariel.setNomActif(nomActif);
//                modeleActuariel.setDateCalcul(dateCalcul);
//                modeleActuariel.setValeurEstimee(valeurEstimee);
//                this.modeleActuarielRepository.save(modeleActuariel);
//            }
//
//            return valeurEstimee;
//        }
//    }
//
//    public ModeleActuariel addModeleActuariel(ModeleActuariel modeleActuariel) {
//        return (ModeleActuariel)this.modeleActuarielRepository.save(modeleActuariel);
//    }
//
//    public ModeleActuariel getModeleActuariels(long idModele) {
//        return (ModeleActuariel)this.modeleActuarielRepository.findById(idModele).orElse((Object)null);
//    }
//
//    public List<ModeleActuariel> getAllModeleActuariels() {
//        return this.modeleActuarielRepository.findAll();
//    }
//
//    public void deleteModeleActuariels(Long idModele) {
//        this.modeleActuarielRepository.deleteById(idModele);
//    }
//
//    public ModeleActuariel updateModeleActuariels(ModeleActuariel modeleActuariel) {
//        return (ModeleActuariel)this.modeleActuarielRepository.save(modeleActuariel);
//    }
//}
