package tn.esprit.projetsalledemarche.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";
    private int userId;  // Ajout de l'ID de l'utilisateur

    public AuthResponseDTO(String accessToken, int userId) {
        this.accessToken = accessToken;
        this.userId = userId;

    }

    public void setRole(String role) {

    }
    //Usage : Envoyé au client après une connexion réussie pour qu’il puisse accéder aux ressources protégées.
}
