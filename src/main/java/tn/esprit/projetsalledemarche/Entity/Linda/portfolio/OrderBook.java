package tn.esprit.projetsalledemarche.Entity.Linda.portfolio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_books")
public class OrderBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;  // Symbole de l’actif

    @OneToMany(mappedBy = "orderBook", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> buyOrders = new ArrayList<>(); // Ordres d'achat

    @OneToMany(mappedBy = "orderBook", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> sellOrders = new ArrayList<>(); // Ordres de vente

    public OrderBook(String symbol) {
        this.symbol = symbol;
    }

    // Getters and setters
}

