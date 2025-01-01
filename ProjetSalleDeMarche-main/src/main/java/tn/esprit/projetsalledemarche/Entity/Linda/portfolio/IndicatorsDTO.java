package tn.esprit.projetsalledemarche.Entity.Linda.portfolio;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
public class IndicatorsDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double close;
    private Double macd; // Changement en Double
    private Double rsi;  // Changement en Double
    private Integer target; // Changement en Integer

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "indicators_id") // La colonne qui contient la clé étrangère
    private List<MarketData> marketDataList;

    // Constructeurs
    public IndicatorsDTO(Double close, Double macd, Double rsi, Integer target) {
        this.close = close;
        this.macd = macd;
        this.rsi = rsi;
        this.target = target;
    }

    public IndicatorsDTO() {
    }
}
