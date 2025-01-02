package tn.esprit.projetsalledemarche.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";

    public AuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRole(String role) {

    }
    //Usage : Envoyé au client après une connexion réussie pour qu’il puisse accéder aux ressources protégées.
}
