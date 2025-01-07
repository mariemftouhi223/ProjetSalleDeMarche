package tn.esprit.projetsalledemarche.Entity.Linda.portfolio;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.projetsalledemarche.Entity.enumerations.AssetType;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // Nom de l'actif (ex: "AAPL" pour Apple)
    @Enumerated(EnumType.STRING)
    private AssetType assetType; // Type d'actif (ex: "Stock", "Bond", "Crypto", etc.)
    private String symbol;
    private Double quantity;  // Quantité de l'actif détenu
    private Double purchasePrice;  // Prix d'achat de l'actif
    private Double currentPrice;  // Prix actuel de l'actif (mis à jour en temps réel)

    // Référence au portefeuille auquel cet actif appartient
    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
}
