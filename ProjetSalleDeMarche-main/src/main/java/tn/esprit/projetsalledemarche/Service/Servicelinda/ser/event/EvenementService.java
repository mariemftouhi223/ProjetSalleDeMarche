package tn.esprit.projetsalledemarche.Service.Servicelinda.ser.event;

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
import tn.esprit.projetsalledemarche.Config.EmailService;
import tn.esprit.projetsalledemarche.Entity.Linda.evenment.Evenement;
import tn.esprit.projetsalledemarche.Entity.Linda.evenment.Interaction;
import tn.esprit.projetsalledemarche.Entity.Linda.user.Formation;
import tn.esprit.projetsalledemarche.Repository.lindarepo.event.EvenementRepository;
import tn.esprit.projetsalledemarche.Repository.lindarepo.event.InteractionRepository;
import tn.esprit.projetsalledemarche.Service.Servicelinda.IMP.IEvenementService;

import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EvenementService implements IEvenementService {

    @Autowired
    private EvenementRepository evenementRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private InteractionRepository interactionRepository;

    @Override
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


    @Override
    public List<Evenement> afficherTousEvenements() {
        return evenementRepository.findAll();
    }

    @Override
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

    @Override
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
}
