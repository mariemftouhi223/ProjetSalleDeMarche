package tn.esprit.projetsalledemarche.Controller.evenement;


import com.google.api.services.calendar.model.CalendarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetsalledemarche.Entity.Linda.evenment.Evenement;

import tn.esprit.projetsalledemarche.Service.ser.event.EvenementService;

import tn.esprit.projetsalledemarche.Service.ser.event.FinanceService;
import weka.classifiers.functions.LinearRegression;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/evenements")
public class EvenementController {


    @Autowired
    private EvenementService evenementService;

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



    private double impactToDouble(String impact) {
        switch (impact.toLowerCase()) {
            case "faible":
                return 0.0;
            case "moyen":
                return 1.0;
            case "élevé":
                return 2.0;
            default:
                return 0.0;
        }
    }





    // Endpoint pour inscrire un utilisateur à un événement payant
    @PostMapping("/inscription/{idEvenement}/{idUtilisateur}")
    public ResponseEntity<String> inscrireAEvent(@PathVariable Long idEvenement,
                                                 @PathVariable Long idUtilisateur,
                                                 @RequestParam String methodDePaiement) {
        String resultat = evenementService.inscrireAEvent(idEvenement, idUtilisateur, methodDePaiement);
        return ResponseEntity.ok(resultat);
    }








}

