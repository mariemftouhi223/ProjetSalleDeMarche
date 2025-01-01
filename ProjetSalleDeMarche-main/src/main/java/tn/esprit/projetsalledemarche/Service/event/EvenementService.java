package tn.esprit.projetsalledemarche.Service.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.Linda.evenment.Evenement;
import tn.esprit.projetsalledemarche.Entity.Linda.user.User;
import tn.esprit.projetsalledemarche.Repository.lindarepo.event.EvenementRepository;
import tn.esprit.projetsalledemarche.Repository.lindarepo.event.InteractionRepository;
import tn.esprit.projetsalledemarche.Repository.lindarepo.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EvenementService {

    @Autowired
    private EvenementRepository evenementRepository;

    private static final Logger logger = LoggerFactory.getLogger(EvenementService.class);

    @Autowired
    private InteractionRepository interactionRepository;

    @Autowired
    private UserRepository userRepository;

    // Méthode pour ajouter un événement
    public Evenement ajouterEvenement(Evenement evenement) {
        System.out.println("Nom de l'événement : " + evenement.getNomEvenement());
        System.out.println("Date de début : " + evenement.getDateDebut());
        System.out.println("Date de fin : " + evenement.getDateFin());

        Evenement addedEvent = evenementRepository.save(evenement);

        return addedEvent;
    }

    // Méthode pour afficher un événement par son ID
    public Evenement afficherevenementParId(Long id) {
        return evenementRepository.findById(id).orElse(null);
    }

    // Méthode pour afficher tous les événements
    public List<Evenement> afficherTousEvenements() {
        return evenementRepository.findAll();
    }

    // Méthode pour modifier un événement
    public Evenement modifierEvenement(Long id, Evenement evenement) {
        Optional<Evenement> evenementExistant = evenementRepository.findById(id);
        if (evenementExistant.isPresent()) {
            Evenement e = evenementExistant.get();
            e.setNomEvenement(evenement.getNomEvenement());
            e.setDateDebut(evenement.getDateDebut());
            e.setDateFin(evenement.getDateFin());
            e.setDescription(evenement.getDescription());
            e.setIdParticipation(evenement.getIdParticipation());
            e.setPrix(evenement.getPrix());  // Mise à jour du prix
            return evenementRepository.save(e);
        } else {
            return null;
        }
    }

    // Méthode pour supprimer un événement
    public void supprimerEvenement(Long idEvenement) {
        evenementRepository.deleteById(idEvenement);
    }

    public String inscrireUtilisateurAEvent(Long idEvenement, Long idUtilisateur) {
        // Récupérer l'événement par son ID
        Evenement evenement = evenementRepository.findById(idEvenement).orElse(null);
        if (evenement == null) {
            return "Événement non trouvé.";
        }

        // Récupérer l'utilisateur par son ID
        User utilisateur = userRepository.findById(idUtilisateur).orElse(null);
        if (utilisateur == null) {
            return "Utilisateur non trouvé.";
        }

        // Récupérer le solde actuel du portefeuille de l'utilisateur
        double soldeAvant = utilisateur.getPortfolioBalance();

        // Vérifier si l'événement a un prix (même si l'événement n'est plus marqué comme payant)
        if (evenement.getPrix() > 0) {
            // Vérifier le solde de l'utilisateur
            if (soldeAvant < evenement.getPrix()) {
                return "Solde insuffisant pour vous inscrire à cet événement.";
            }

            // Déduire le prix de l'événement du solde de l'utilisateur
            double nouveauSolde = soldeAvant - evenement.getPrix();
            utilisateur.setPortfolioBalance(nouveauSolde);

            // Sauvegarder les informations de l'utilisateur avec le solde mis à jour
            userRepository.save(utilisateur);
        }

        // Ajouter l'événement à la liste des événements de l'utilisateur
        utilisateur.getEvenements().add(evenement);

        // Sauvegarder l'événement et l'utilisateur après la mise à jour
        evenementRepository.save(evenement);
        userRepository.save(utilisateur);

        // Solde après inscription
        double soldeApres = utilisateur.getPortfolioBalance();

        // Retourner un message avec les soldes avant et après l'inscription
        return "Inscription réussie à l'événement: " + evenement.getNomEvenement() + ". " +
                "Solde avant inscription: " + soldeAvant + "$, Solde après inscription: " + soldeApres + "$.";
    }




}
