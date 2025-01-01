package tn.esprit.projetsalledemarche.Entity.Linda.portfolio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.projetsalledemarche.Entity.Linda.user.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol; // Le symbole de l'actif (ex: AAPL, BTC)
    private double entryPrice; // Prix d'entrée dans la position
    private int quantity; // Quantité achetée
    private double stopLoss; // Niveau de Stop Loss
    private double takeProfit; // Niveau de Take Profit
    private String positionType; // "buy" ou "sell"
    @Column(columnDefinition = "TINYINT(1)")
    private boolean isOpen = true; // La position est ouverte par défaut
    private double currentPrice;
    private double closePrice;
    private double profitOrLoss;
    // Timestamp pour l'entrée en position
    private LocalDateTime entryTime;
    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    @JsonIgnore
    private Portfolio portfolio;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    private LocalDateTime openedAt;
    private LocalDateTime closedAt;
    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
//    public double getProfitOrLoss(Double currentPrice) {
//        if (currentPrice == null) {
//            throw new IllegalArgumentException("Le prix actuel ne peut pas être nul.");
//        }
//        return (currentPrice - this.entryPrice) * this.quantity;
//    }
}