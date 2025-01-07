package tn.esprit.projetsalledemarche.Controller;
import org.springframework.format.annotation.DateTimeFormat;
import tn.esprit.projetsalledemarche.Entity.ModeleActuariel;
import tn.esprit.projetsalledemarche.Entity.ProduitAssurance;//
import tn.esprit.projetsalledemarche.Service.IModeleActuarielService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetsalledemarche.Service.IProduitAssuranceService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ModeleActuarielController {
    @Autowired
    IModeleActuarielService modeleActuarielService;


  /*  @GetMapping("/predictions/chart/{nomActif}")
    public ResponseEntity<byte[]> getPredictionChart(@PathVariable String nomActif) {
        byte[] chartImage = modeleActuarielService.generatePredictionChart(nomActif);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(chartImage.length);

        return new ResponseEntity<>(chartImage, headers, HttpStatus.OK);
    }
*/

    @GetMapping("/predictions/chart/{nomActif}")
    public ResponseEntity<String> getPredictionChart(@PathVariable String nomActif) {
        try {
            String chartBase64 = modeleActuarielService.generatePredictionChart(nomActif);
            return new ResponseEntity<>(chartBase64, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/calculateValeurEstimee")
    public ResponseEntity<Map<String, Object>> calculateValeurEstimee(
            @RequestParam String nomActif,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateCalcul,
            @RequestParam boolean saveToDatabase) {

        try {
            String baseNomActif = nomActif.replaceAll("\\d+$", "");

            BigDecimal valeurEstimee = modeleActuarielService.processAndSaveValeurEstimee(baseNomActif, dateCalcul, saveToDatabase);

            // Préparer la réponse avec les détails
            Map<String, Object> response = new HashMap<>();
            response.put("nomActif", nomActif);
            response.put("dateCalcul", dateCalcul);
            response.put("valeurEstimee", valeurEstimee);
            response.put("savedToDatabase", saveToDatabase);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }



    @GetMapping("/predictions/{nomActif}")
    public ResponseEntity<?> getPredictionsByAssetName(@PathVariable String nomActif) {
        try {
            List<String[]> rawPredictions = modeleActuarielService.getPredictionsByAssetName(nomActif);

            // Transformer en un format JSON plus lisible : Liste d'objets avec des clés
            List<Map<String, Object>> predictions = new ArrayList<>();
            for (String[] row : rawPredictions) {
                // Vérifier que la ligne contient bien les deux valeurs attendues
                if (row.length == 2 && isNumeric(row[1])) {
                    Map<String, Object> entry = new HashMap<>();
                    entry.put("Date", row[0]);
                    entry.put("Prediction", Double.parseDouble(row[1]));
                    predictions.add(entry);
                }
            }

            return new ResponseEntity<>(predictions, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Erreur lors de la récupération des prédictions : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Fonction utilitaire pour vérifier si une chaîne est numérique
    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    @PostMapping("/modelactuariel/ajout")
    ModeleActuariel addModeleActuariel(@RequestBody ModeleActuariel modeleActuariel) {
        return modeleActuarielService.addModeleActuariel(modeleActuariel);
    }

    @GetMapping("/modelactuariel/{id}")
    ModeleActuariel retrieveModeleActuariels(@PathVariable("id") long idModele) {
        return modeleActuarielService.getModeleActuariels(idModele);
    }


    @GetMapping("/modelactuariel")
    public ResponseEntity<List<ModeleActuariel>> getAllModeleActuariels() {
        List<ModeleActuariel> modeleActuariels = modeleActuarielService.getAllModeleActuariels();
        if (modeleActuariels != null && !modeleActuariels.isEmpty()) {
            return ResponseEntity.ok(modeleActuariels);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/modelactuariel/delete/{id}")
    void deleteModeleActuariels(@PathVariable("id") Long idModele) {
        modeleActuarielService.deleteModeleActuariels(idModele);
    }

    @PutMapping("/modelactuariel/update")
    ModeleActuariel updateModeleActuariels(@RequestBody ModeleActuariel modeleActuariel) {
        return modeleActuarielService.updateModeleActuariels(modeleActuariel);
    }

}
