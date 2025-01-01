package tn.esprit.projetsalledemarche.Service.user;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.Linda.user.Formation;
import tn.esprit.projetsalledemarche.Entity.Linda.user.User;
import tn.esprit.projetsalledemarche.Repository.lindarepo.formation.FormationRepository;
import tn.esprit.projetsalledemarche.Repository.lindarepo.user.UserRepository;

import java.util.List;


@Service
public class FormationService  {
    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private UserRepository userRepository;


    public Formation ajouterFormation(Formation formation) {
        return formationRepository.save(formation);
    }



    public List<Formation> afficherToutesFormations() {
        return formationRepository.findAll();
    }
    public Formation afficherFormationParId(Long id) {
        return formationRepository.findById(id).orElse(null);
    }



    public Formation modifierFormation(Long id, Formation formation) {
        Formation existingFormation = formationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Formation not found with id " + id));

        existingFormation.setTitre(formation.getTitre());
        existingFormation.setDescription(formation.getDescription());
        existingFormation.setDateCreation(formation.getDateCreation());
        existingFormation.setPrix(formation.getPrix());  // Mise à jour du prix

        return formationRepository.save(existingFormation);
    }

    /**
     * Supprime une formation par ID.
     * @param idFormation L'identifiant de la formation à supprimer.
     */

    public void supprimerFormation(Long idFormation) {
        // Récupérer la formation
        Formation formation = formationRepository.findById(idFormation)
                .orElseThrow(() -> new EntityNotFoundException("Formation not found with id " + idFormation));

        // Supprimer les relations d'inscription dans la table `user_formations`
        for (User utilisateur : formation.getUsers()) {
            utilisateur.getFormations().remove(formation);  // Retirer la formation de la liste de formations de l'utilisateur
            userRepository.save(utilisateur);  // Sauvegarder l'utilisateur après modification
        }

        // Supprimer la formation
        formationRepository.deleteById(idFormation);
    }
    // Search formations by title or description
    public List<Formation> searchFormations(String keyword) {
        return formationRepository.searchFormations(keyword);
    }
    public String inscrireUtilisateurAFormation(Long idFormation, Long idUtilisateur) {
        // Récupérer la formation par son ID
        Formation formation = formationRepository.findById(idFormation).orElse(null);
        if (formation == null) {
            return "Formation non trouvée.";
        }

        // Récupérer l'utilisateur par son ID
        User utilisateur = userRepository.findById(idUtilisateur).orElse(null);
        if (utilisateur == null) {
            return "Utilisateur non trouvé.";
        }

        // Récupérer le solde actuel du portefeuille de l'utilisateur
        double soldeAvant = utilisateur.getPortfolioBalance();

        // Vérification si l'utilisateur est déjà inscrit à cette formation
        if (formation.getUsers().contains(utilisateur)) {
            return "L'utilisateur est déjà inscrit à cette formation.";
        }

        // Si la formation est payante, vérifier le solde de l'utilisateur
        if (formation.getPrix() > 0) {
            if (soldeAvant < formation.getPrix()) {
                return "Solde insuffisant pour vous inscrire à cette formation.";
            }

            // Déduire le prix de la formation du solde de l'utilisateur
            double nouveauSolde = soldeAvant - formation.getPrix();
            utilisateur.setPortfolioBalance(nouveauSolde);

            // Sauvegarder les informations de l'utilisateur avec le solde mis à jour
            userRepository.save(utilisateur);
        }

        // Ajouter l'utilisateur à la liste des participants de la formation
        formation.getUsers().add(utilisateur);

        // Ajouter la formation à la liste des formations de l'utilisateur
        utilisateur.getFormations().add(formation);

        // Sauvegarder la formation et l'utilisateur après la mise à jour
        formationRepository.save(formation);
        userRepository.save(utilisateur);

        // Solde après inscription
        double soldeApres = utilisateur.getPortfolioBalance();

        // Retourner un message avec les soldes avant et après l'inscription
        return "Inscription réussie à la formation: " + formation.getTitre() + ". " +
                "Solde avant inscription: " + soldeAvant + "$, Solde après inscription: " + soldeApres + "$.";
    }



}
