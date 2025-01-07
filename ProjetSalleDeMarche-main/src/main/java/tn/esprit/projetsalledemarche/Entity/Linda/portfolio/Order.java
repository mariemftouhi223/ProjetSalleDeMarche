package tn.esprit.projetsalledemarche.Entity.Linda.portfolio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Génération automatique de l'ID
    private Long id;
    private String symbol; // Symbole de l’actif
    private double price;  // Prix d'entrée de la position
    private int quantity;  // Quantité
    private String type;   // "BID" ou "ASK"
    private LocalDateTime timestamp; // Temps d'entrée en position
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_book_id", nullable = false)
    private OrderBook orderBook; // Référence au carnet d'ordres associé
    public Order(String symbol, double price, int quantity, String type, LocalDateTime timestamp) {
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
        this.timestamp = timestamp;
    }

    public Order(double price, int quantity, String type, LocalDateTime timestamp) {
        this.price = price;
        this.quantity = quantity;
        this.type = type;
        this.timestamp = timestamp;
    }
}
