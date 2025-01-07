//package tn.esprit.projetsalledemarche.Controller;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import tn.esprit.projetsalledemarche.Entity.ModeleActuariel;
//import tn.esprit.projetsalledemarche.Service.IMP.IModeleActuarielService;
//
//@RestController
//@RequestMapping({"/api"})
//@CrossOrigin({"*"})
//public class ModeleActuarielController {
//    @Autowired
//    IModeleActuarielService modeleActuarielService;
//
//    public ModeleActuarielController() {
//    }
//
//    @GetMapping({"/predictions/chart/{nomActif}"})
//    public ResponseEntity<String> getPredictionChart(@PathVariable String nomActif) {
//        try {
//            String chartBase64 = this.modeleActuarielService.generatePredictionChart(nomActif);
//            return new ResponseEntity(chartBase64, HttpStatus.OK);
//        } catch (RuntimeException var3) {
//            return new ResponseEntity(var3.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping({"/calculateValeurEstimee"})
//    public ResponseEntity<Map<String, Object>> calculateValeurEstimee(@RequestParam String nomActif, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateCalcul, @RequestParam boolean saveToDatabase) {
//        try {
//            BigDecimal valeurEstimee = this.modeleActuarielService.processAndSaveValeurEstimee(nomActif, dateCalcul, saveToDatabase);
//            Map<String, Object> response = new HashMap();
//            response.put("nomActif", nomActif);
//            response.put("dateCalcul", dateCalcul);
//            response.put("valeurEstimee", valeurEstimee);
//            response.put("savedToDatabase", saveToDatabase);
//            return ResponseEntity.ok(response);
//        } catch (RuntimeException var6) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", var6.getMessage()));
//        }
//    }
//
//    @GetMapping({"/predictions/{nomActif}"})
//    public ResponseEntity<?> getPredictionsByAssetName(@PathVariable String nomActif) {
//        try {
//            List<String[]> rawPredictions = this.modeleActuarielService.getPredictionsByAssetName(nomActif);
//            List<Map<String, Object>> predictions = new ArrayList();
//            Iterator var4 = rawPredictions.iterator();
//
//            while(var4.hasNext()) {
//                String[] row = (String[])var4.next();
//                if (row.length == 2 && this.isNumeric(row[1])) {
//                    Map<String, Object> entry = new HashMap();
//                    entry.put("Date", row[0]);
//                    entry.put("Prediction", Double.parseDouble(row[1]));
//                    predictions.add(entry);
//                }
//            }
//
//            return new ResponseEntity(predictions, HttpStatus.OK);
//        } catch (RuntimeException var7) {
//            return new ResponseEntity("Erreur lors de la récupération des prédictions : " + var7.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    private boolean isNumeric(String strNum) {
//        if (strNum == null) {
//            return false;
//        } else {
//            try {
//                Double.parseDouble(strNum);
//                return true;
//            } catch (NumberFormatException var3) {
//                return false;
//            }
//        }
//    }
//
//    @PostMapping({"/modelactuariel/ajout"})
//    ModeleActuariel addModeleActuariel(@RequestBody ModeleActuariel modeleActuariel) {
//        return this.modeleActuarielService.addModeleActuariel(modeleActuariel);
//    }
//
//    @GetMapping({"/modelactuariel/{id}"})
//    ModeleActuariel retrieveModeleActuariels(@PathVariable("id") long idModele) {
//        return this.modeleActuarielService.getModeleActuariels(idModele);
//    }
//
//    @GetMapping({"/modelactuariel"})
//    public ResponseEntity<List<ModeleActuariel>> getAllModeleActuariels() {
//        List<ModeleActuariel> modeleActuariels = this.modeleActuarielService.getAllModeleActuariels();
//        return modeleActuariels != null && !modeleActuariels.isEmpty() ? ResponseEntity.ok(modeleActuariels) : ResponseEntity.notFound().build();
//    }
//
//    @DeleteMapping({"/modelactuariel/delete/{id}"})
//    void deleteModeleActuariels(@PathVariable("id") Long idModele) {
//        this.modeleActuarielService.deleteModeleActuariels(idModele);
//    }
//
//    @PutMapping({"/modelactuariel/update"})
//    ModeleActuariel updateModeleActuariels(@RequestBody ModeleActuariel modeleActuariel) {
//        return this.modeleActuarielService.updateModeleActuariels(modeleActuariel);
//    }
//}
