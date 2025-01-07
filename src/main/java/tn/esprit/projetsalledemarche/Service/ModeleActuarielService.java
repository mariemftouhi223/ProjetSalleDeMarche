package tn.esprit.projetsalledemarche.Service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.persistence.EntityNotFoundException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.ModeleActuariel;
import tn.esprit.projetsalledemarche.Repository.ModeleActuarielRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class ModeleActuarielService implements IModeleActuarielService {
    private static final String PREDICTIONS_FOLDER = "predictions"; // Dossier contenant les fichiers CSV
    private final ModeleActuarielRepository modeleActuarielRepository;

    @Autowired
    public ModeleActuarielService(ModeleActuarielRepository modeleActuarielRepository) {
        this.modeleActuarielRepository = modeleActuarielRepository;
    }

    // Génère le graphique de prédiction et retourne l'image encodée en Base64
    public String generatePredictionChart(String nomActif) {
        try {
            Path filePath = Paths.get(PREDICTIONS_FOLDER, nomActif + "_predictions.csv");

            if (Files.notExists(filePath)) {
                throw new RuntimeException("Fichier de prédiction introuvable pour l'actif : " + nomActif);
            }

            XYSeries series = new XYSeries("Prédictions " + nomActif);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            try (CSVReader csvReader = new CSVReader(Files.newBufferedReader(filePath))) {
                List<String[]> rows = csvReader.readAll();

                for (int i = 1; i < rows.size(); i++) { // Ignore la première ligne (en-têtes)
                    String[] row = rows.get(i);
                    if (row.length < 2) {
                        throw new RuntimeException("Ligne de données mal formatée dans le fichier : " + filePath);
                    }

                    // Conversion de la date en timestamp pour JFreeChart
                    Date date = dateFormat.parse(row[0]);
                    double timestamp = date.getTime();
                    double value = Double.parseDouble(row[1]);
                    series.add(timestamp, value);
                }
            }

            XYSeriesCollection dataset = new XYSeriesCollection(series);
            JFreeChart chart = ChartFactory.createXYLineChart(
                    "Courbe de Prédictions - " + nomActif,
                    "Date",
                    "Valeur Prédite",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

            // Convertir le graphique en image
            BufferedImage chartImage = chart.createBufferedImage(800, 600);

            // Encodage de l'image en Base64
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(chartImage, "png", baos);
            byte[] imageBytes = baos.toByteArray();

            return Base64.getEncoder().encodeToString(imageBytes);

        } catch (IOException | CsvException e) {
            throw new RuntimeException("Erreur lors de la génération du graphique pour " + nomActif + " : " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Erreur de format de données dans le fichier CSV : " + e.getMessage(), e);
        } catch (ParseException e) {
            throw new RuntimeException("Erreur de parsing de la date dans le fichier CSV : " + e.getMessage(), e);
        }
    }
    public List<String[]> getPredictionsByAssetName(String nomActif) {
        Path filePath = Paths.get(PREDICTIONS_FOLDER, nomActif + "_predictions.csv");

        if (Files.notExists(filePath)) {
            throw new RuntimeException("Fichier de prédiction introuvable pour l'actif : " + nomActif);
        }

        try (CSVReader csvReader = new CSVReader(Files.newBufferedReader(filePath))) {
            return csvReader.readAll();
        } catch (IOException | CsvException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier de prédiction pour " + nomActif, e);
        }
    }



    public List<String[]> getPredictionsByAssetName1(String nomActif, Date dateCalcul) {
        Path filePath = Paths.get(PREDICTIONS_FOLDER, nomActif + "_predictions.csv");

        if (Files.notExists(filePath)) {
            throw new RuntimeException("Fichier de prédiction introuvable pour l'actif : " + nomActif);
        }

        // Calcul des dates de début et de fin de la période
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateCalcul);
        calendar.add(Calendar.WEEK_OF_YEAR, -2);
        Date dateStart = calendar.getTime();

        calendar.setTime(dateCalcul);
        calendar.add(Calendar.WEEK_OF_YEAR, 2);
        Date dateEnd = calendar.getTime();

        List<String[]> filteredPredictions = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try (CSVReader csvReader = new CSVReader(Files.newBufferedReader(filePath))) {
            List<String[]> rows = csvReader.readAll();

            for (int i = 1; i < rows.size(); i++) { // Ignore la première ligne (en-têtes)
                String[] row = rows.get(i);
                Date predictionDate = dateFormat.parse(row[0]);

                // Filtrer les prédictions entre dateStart et dateEnd
                if (predictionDate.compareTo(dateStart) >= 0 && predictionDate.compareTo(dateEnd) <= 0) {
                    filteredPredictions.add(row);
                }
            }
        } catch (IOException | CsvException | ParseException e) {
            throw new RuntimeException("Erreur lors de la lecture ou du filtrage du fichier de prédiction pour " + nomActif, e);
        }

        return filteredPredictions;
    }

    public List<Double> getPredictionsAroundDate(String nomActif, Date dateCalcul) {
        // Durée de deux semaines en millisecondes
        long twoWeeksInMillis = 14 * 24 * 60 * 60 * 1000;
        long startDateInMillis = dateCalcul.getTime() - twoWeeksInMillis;
        long endDateInMillis = dateCalcul.getTime() + twoWeeksInMillis;

        List<String[]> predictions = getPredictionsByAssetName(nomActif);
        List<Double> filteredPredictions = new ArrayList<>();

        // Parcours des données de prédiction pour filtrer selon la période définie
        for (String[] row : predictions) {
            try {
                Date predictionDate = new SimpleDateFormat("yyyy-MM-dd").parse(row[0]);
                double value = Double.parseDouble(row[1]);

                // Filtre les valeurs dans l'intervalle de deux semaines autour de la date calcul
                if (predictionDate.getTime() >= startDateInMillis && predictionDate.getTime() <= endDateInMillis) {
                    filteredPredictions.add(value);
                }
            } catch (ParseException | NumberFormatException e) {
                // Log pour afficher les erreurs de parsing, sans interrompre le processus
                System.err.println("Erreur dans le format des données à la ligne : " + e.getMessage());
            }
        }
        return filteredPredictions;
    }

    public BigDecimal calculerValeurEstimeeInitiale(String nomActif) {
        List<String[]> toutesPredictions = getPredictionsByAssetName(nomActif);
        double somme = 0.0;
        int count = 0;

        for (String[] row : toutesPredictions) {
            try {
                double valeurPrediction = Double.parseDouble(row[1]);
                somme += valeurPrediction;
                count++;
            } catch (NumberFormatException e) {
                System.out.println("Erreur de format dans les prédictions : " + e.getMessage());
            }
        }

        // Calculer la moyenne et la convertir en BigDecimal pour `valeurEstimee`
        double moyenne = count > 0 ? somme / count : 0.0;
        return BigDecimal.valueOf(moyenne);
    }

    public BigDecimal calculateValeurEstimee(String nomActif, Date dateCalcul, boolean saveToDatabase) {
        // Obtenir les prédictions autour de la date
        List<Double> predictionsAroundDate = getPredictionsAroundDate(nomActif, dateCalcul);

        if (predictionsAroundDate.isEmpty()) {
            throw new RuntimeException("Aucune prédiction trouvée autour de la date spécifiée.");
        }

        // Calculer la moyenne des prédictions
        double sum = 0;
        for (double value : predictionsAroundDate) {
            sum += value;
        }
        double averagePrediction = sum / predictionsAroundDate.size();

        // Convertir en BigDecimal pour la valeur actuarielle estimée
        BigDecimal valeurEstimee = BigDecimal.valueOf(averagePrediction);

        // Enregistrer ou afficher selon le choix de l'utilisateur
        if (saveToDatabase) {
            ModeleActuariel modeleActuariel = new ModeleActuariel();
            modeleActuariel.setNomActif(nomActif);
            modeleActuariel.setDateCalcul(dateCalcul);
            modeleActuariel.setValeurEstimee(valeurEstimee);
            modeleActuarielRepository.save(modeleActuariel);
        }

        return valeurEstimee;
    }
    public BigDecimal processAndSaveValeurEstimee(String nomActif, Date dateCalcul, boolean saveToDatabase) {
        // Obtenir les prédictions autour de la date
        List<Double> predictionsAroundDate = getPredictionsAroundDate(nomActif, dateCalcul);

        if (predictionsAroundDate.isEmpty()) {
            throw new RuntimeException("Aucune prédiction trouvée autour de la date spécifiée.");
        }

        // Calculer la moyenne des prédictions pour obtenir la valeur estimée
        double sum = 0;
        for (double value : predictionsAroundDate) {
            sum += value;
        }
        double averagePrediction = sum / predictionsAroundDate.size();
        BigDecimal valeurEstimee = BigDecimal.valueOf(averagePrediction);

        // Si demandé, enregistrer dans la base de données
        if (saveToDatabase) {
            ModeleActuariel modeleActuariel = new ModeleActuariel();
            modeleActuariel.setNomActif(nomActif);
            modeleActuariel.setDateCalcul(dateCalcul);
            modeleActuariel.setValeurEstimee(valeurEstimee);
            modeleActuarielRepository.save(modeleActuariel);
        }

        return valeurEstimee;
    }

    @Override
    public ModeleActuariel addModeleActuariel(ModeleActuariel modeleActuariel) {
        return modeleActuarielRepository.save(modeleActuariel);
    }

    @Override
    public ModeleActuariel getModeleActuariels(long idModele) {
        return modeleActuarielRepository.findById(idModele).orElse(null);
    }

    @Override
    public List<ModeleActuariel> getAllModeleActuariels() {
        return modeleActuarielRepository.findAll();
    }

    @Override
    public void deleteModeleActuariels(Long idModele) {
        modeleActuarielRepository.deleteById(idModele);
    }

    @Override
    public ModeleActuariel updateModeleActuariels(ModeleActuariel updatedModele) {
        // Retrieve the old model by ID
        ModeleActuariel existingModele = modeleActuarielRepository.findById(updatedModele.getIdModele())
                .orElseThrow(() -> new EntityNotFoundException("ModeleActuariel not found with ID: " + updatedModele.getIdModele()));

        // Update fields with new values, preserving old ones if not provided
        if (updatedModele.getNomActif() != null) {
            existingModele.setNomActif(updatedModele.getNomActif());
        }
        if (updatedModele.getDateCalcul() != null) {
            existingModele.setDateCalcul(updatedModele.getDateCalcul());
        }
        if (updatedModele.getValeurEstimee() != null) {
            existingModele.setValeurEstimee(updatedModele.getValeurEstimee());
        }

        // Save and return the updated model
        return modeleActuarielRepository.save(existingModele);
    }

}
