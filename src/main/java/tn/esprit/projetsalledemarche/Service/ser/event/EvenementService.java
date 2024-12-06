package tn.esprit.projetsalledemarche.Service.ser.event;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.Linda.evenment.Evenement;
import tn.esprit.projetsalledemarche.Entity.Linda.evenment.Interaction;
import tn.esprit.projetsalledemarche.Entity.Linda.user.Formation;
import tn.esprit.projetsalledemarche.Repository.lindarepo.event.EvenementRepository;
import tn.esprit.projetsalledemarche.Repository.lindarepo.event.InteractionRepository;
import tn.esprit.projetsalledemarche.Service.IMP.IEvenementService;
import tn.esprit.projetsalledemarche.Service.ser.event.FinanceService;



import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EvenementService  {
    @Autowired
    private FinanceService financeService;
    @Autowired
    private EvenementRepository evenementRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private InteractionRepository interactionRepository;

    public Evenement ajouterEvenement(Evenement evenement) {
        // Vérification des données avant sauvegarde
        System.out.println("Nom de l'événement : " + evenement.getNomEvenement());
        System.out.println("Date de début : " + evenement.getDateDebut());
        System.out.println("Date de fin : " + evenement.getDateFin());
        System.out.println("Emails participants : " + evenement.getEmailsParticipants());

        // Sauvegarde de l'événement
        Evenement addedEvent = evenementRepository.save(evenement);

        // Retour de l'événement ajouté
        return addedEvent;
    }
    public Evenement afficherevenementParId(Long id) {
        return evenementRepository.findById(id).orElse(null);
    }



    public List<Evenement> afficherTousEvenements() {
        return evenementRepository.findAll();
    }


    public Evenement modifierEvenement(Long id, Evenement evenement) {
        Optional<Evenement> evenementExistant = evenementRepository.findById(id);
        if (evenementExistant.isPresent()) {
            Evenement e = evenementExistant.get();
            e.setNomEvenement(evenement.getNomEvenement());
            e.setDateDebut(evenement.getDateDebut());
            e.setDateFin(evenement.getDateFin());
            e.setDescription(evenement.getDescription());
            e.setIdParticipation(evenement.getIdParticipation());
            return evenementRepository.save(e);
        } else {
            return null; // ou gérer une exception
        }
    }


    public void supprimerEvenement(Long idEvenement) {
        evenementRepository.deleteById(idEvenement);
    }

    // Méthode pour récupérer la liste des calendriers
    public CalendarList getCalendars(String authorizationCode) throws Exception {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JacksonFactory.getDefaultInstance(),
                new InputStreamReader(getClass().getResourceAsStream("/credentials.json"))
        );

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                clientSecrets,
                Collections.singleton(CalendarScopes.CALENDAR_READONLY))
                .setAccessType("offline")
                .build();

        GoogleTokenResponse tokenResponse = flow.newTokenRequest(authorizationCode)
                .setRedirectUri("YOUR_REDIRECT_URI")
                .execute();

        Credential credential = flow.createAndStoreCredential(tokenResponse, "user");

        // Appel de l'API Google Calendar
        return new com.google.api.services.calendar.Calendar.Builder(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                credential)
                .setApplicationName("Nom de ton application")
                .build()
                .calendarList()
                .list()
                .execute();
    }

    // Méthodes génériques pour enregistrer les interactions
    private void enregistrerInteraction(Long userId, Long eventId, String type) {
        if (interactionRepository.findFirstByUserIdAndEventIdAndType(userId, eventId, type) == null) {
            Interaction interaction = new Interaction(userId, eventId, type, LocalDateTime.now());
            interactionRepository.save(interaction);
        }
    }



    public List<Double> prepareData(Evenement evenement) {
        List<Double> data = new ArrayList<>();
        data.add((double) evenement.getNombreParticipantsPrevus());
        data.add(impactToDouble(evenement.getImpact())); // Convertir l'impact en valeur numérique
        data.add(evenement.isSuccesPrecedent() ? 1.0 : 0.0);
        return data;
    }

    private double impactToDouble(String impact) {
        return switch (impact.toLowerCase()) {
            case "faible" -> 0.0;
            case "moyen" -> 1.0;
            case "élevé" -> 2.0;
            default -> 0.0; // Valeur par défaut
        };
    }






    public void likeEvent(Long userId, Long eventId) {
        enregistrerInteraction(userId, eventId, "like");
    }

    public void viewEvent(Long userId, Long eventId) {
        enregistrerInteraction(userId, eventId, "vue");
    }

    public void clickEvent(Long userId, Long eventId) {
        enregistrerInteraction(userId, eventId, "clic");
    }


    public void favoriteEvent(Long userId, Long eventId) {
        enregistrerInteraction(userId, eventId, "favori");
    }


    // Méthode d'inscription à un événement payant
    public String inscrireAEvent(Long idEvenement, Long idUtilisateur, String methodDePaiement) {
        Evenement evenement = evenementRepository.findById(idEvenement).orElse(null);
        if (evenement == null) {
            return "Événement non trouvé.";
        }

        // Vérifier si l'événement est payant
        if (evenement.isPayant()) {
            // Traitement du paiement via le service de finance
            boolean paiementReussi = financeService.effectuerPaiement(idUtilisateur, evenement.getPrix(), methodDePaiement);

            if (paiementReussi) {
                // Ajouter l'email de l'utilisateur dans la liste des participants
                String emailUtilisateur = getEmailUtilisateur(idUtilisateur);
                evenement.getEmailsParticipants().add(emailUtilisateur);

                // Sauvegarder l'événement mis à jour
                evenementRepository.save(evenement);

                // Envoi de la confirmation par email
                emailService.envoyerEmailConfirmation(emailUtilisateur, evenement);

                return "Inscription réussie et paiement effectué.";
            } else {
                return "Le paiement a échoué.";
            }
        } else {
            // Si l'événement est gratuit
            String emailUtilisateur = getEmailUtilisateur(idUtilisateur);
            evenement.getEmailsParticipants().add(emailUtilisateur);
            evenementRepository.save(evenement);

            return "Inscription réussie.";
        }
    }

    // Méthode pour récupérer l'email d'un utilisateur (exemple)
    private String getEmailUtilisateur(Long idUtilisateur) {
        // Logique pour récupérer l'email de l'utilisateur depuis la base de données
        return "utilisateur@example.com"; // Exemple fictif
    }
}