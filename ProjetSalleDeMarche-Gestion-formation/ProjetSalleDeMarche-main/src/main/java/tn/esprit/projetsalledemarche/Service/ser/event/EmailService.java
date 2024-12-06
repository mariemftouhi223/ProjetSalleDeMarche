package tn.esprit.projetsalledemarche.Service.ser.event;

import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.Linda.evenment.Evenement;

@Service("emailConfirmationService") // Nom unique pour ce service
public class EmailService {

    public void envoyerEmailConfirmation(String emailUtilisateur, Evenement evenement) {
        // Logique pour envoyer un email de confirmation
        System.out.println("Email de confirmation envoyé à : " + emailUtilisateur);
        System.out.println("Inscription confirmée pour l'événement : " + evenement.getNomEvenement());
    }
}
