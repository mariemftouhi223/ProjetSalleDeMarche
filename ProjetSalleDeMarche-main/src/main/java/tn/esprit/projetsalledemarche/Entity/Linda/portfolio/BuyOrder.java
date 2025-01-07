package tn.esprit.projetsalledemarche.Entity.Linda.portfolio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BuyOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_book_id", nullable = false)
    private OrderBook orderBook; // Référence au carnet d'ordres associé

    @Column(nullable = false)
    private double price; // Prix d'achat

    @Column(nullable = false)
    private int quantity; // Quantité totale pour ce prix
}

