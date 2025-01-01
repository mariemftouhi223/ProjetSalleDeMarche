package tn.esprit.projetsalledemarche.Entity.Linda.portfolio;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor

public class OrderDTO {
    private String symbol; // Symbole de l’actif
    private double price;  // Prix d'entrée de la position
    private int quantity;  // Quantité
    private String type;   // "BID" ou "ASK"
    private LocalDateTime timestamp; // Temps d'entrée en position
}