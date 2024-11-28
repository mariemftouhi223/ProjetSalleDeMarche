package tn.esprit.projetsalledemarche.Controller.evenement;


import com.google.api.services.calendar.model.CalendarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetsalledemarche.Entity.Linda.evenment.Evenement;

import tn.esprit.projetsalledemarche.Entity.Linda.user.Formation;
import tn.esprit.projetsalledemarche.Service.Servicelinda.ser.event.EvenementService;
import tn.esprit.projetsalledemarche.Service.Servicelinda.ser.FinanceService;
import tn.esprit.projetsalledemarche.Service.Servicelinda.ser.PredictionService;
import weka.classifiers.functions.LinearRegression;

import java.util.List;

@RestController
@RequestMapping("/evenements")
public class EvenementController {

    @Autowired
    private EvenementService evenementService;
    @Autowired
    private PredictionService predictionService; // Injection de PredictionService

    private FinanceService financeService;
    // Ajouter un événement
    @PostMapping("/ajouter")
    public Evenement ajouterEvenement(@RequestBody Evenement evenement) {
        return evenementService.ajouterEvenement(evenement);
    }
    @GetMapping("/afficher/{id}")
    public ResponseEntity<Evenement> afficherEvenementParId(@PathVariable Long id) {
        Evenement evenement = evenementService.afficherevenementParId(id);
        if (evenement != null) {
            return ResponseEntity.ok(evenement); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found si l'ID n'existe pas
        }
    }

    // Afficher tous les événements
    @GetMapping("/afficher")
    public List<Evenement> afficherTousEvenements() {
        return evenementService.afficherTousEvenements();
    }

    // Modifier un événement
    @PutMapping("/modifier/{id}")
    public Evenement modifierEvenement(@PathVariable("id") Long id, @RequestBody Evenement evenement) {
        return evenementService.modifierEvenement(id, evenement);
    }

    // Supprimer un événement par ID
    @DeleteMapping("/supprimer")
    public void supprimerEvenement(@RequestParam Long idEvenement) {
        evenementService.supprimerEvenement(idEvenement);
    }


    // Récupérer la liste des calendriers Google
    @GetMapping("/calendriers")
    public CalendarList getCalendars(@RequestParam String authorizationCode) throws Exception {
        return evenementService.getCalendars(authorizationCode);
    }




    @Autowired
    public EvenementController(FinanceService financeService) {
        this.financeService = financeService;
    }

    @GetMapping("/stock/{symbol}")
    public String getStockData(@PathVariable String symbol) {
        return financeService.getStockData(symbol);
    }




    @GetMapping("/defi/predire-prix")
    public String predirePrix(@RequestParam String symbol) {
        try {
            // Récupérer les données d'une action
            String stockData = financeService.getStockData(symbol);
            List<Double> prices = predictionService.prepareData(stockData); // Appel à la méthode dans PredictionService

            // Entraîner le modèle
            LinearRegression model = predictionService.trainModel(prices); // Appel à la méthode dans PredictionService

            // Faire une prédiction sur le dernier prix
            double lastPrice = prices.get(prices.size() - 1);
            double predictedPrice = predictionService.predictPrice(model, lastPrice); // Appel à la méthode dans PredictionService

            return "Le prix prédit pour " + symbol + " est : " + predictedPrice;
        } catch (Exception e) {
            return "Erreur lors de la prédiction : " + e.getMessage();
        }
    }




}

