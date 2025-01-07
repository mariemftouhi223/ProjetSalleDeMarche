package tn.esprit.projetsalledemarche.Entity.Linda.evenment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Interaction {
    @Id
    private Long userId;

    private Long eventId;
    private String type;
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private Boolean isLiked = false;

    @Column(nullable = true)
    private String commentaire; // Assurez-vous d'avoir ce champ si utilisé

    @Column(nullable = false)
    private Boolean isSauvegarde = false; // Assurez-vous d'avoir ce champ si utilisé

    // Constructeur avec paramètres nécessaires
    public Interaction(Long userId, Long eventId, String type, LocalDateTime timestamp) {
        this.userId = userId;
        this.eventId = eventId;
        this.type = type;
        this.timestamp = timestamp;
    }

    // Constructeur sans arguments (si nécessaire)
    public Interaction() {}

    // Getters et setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId; // Méthode définie ici
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        this.isLiked = liked;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Boolean getIsSauvegarde() {
        return isSauvegarde;
    }

    public void setSauvegarder(boolean sauvegarder) {
        this.isSauvegarde = sauvegarder;
    }
}

