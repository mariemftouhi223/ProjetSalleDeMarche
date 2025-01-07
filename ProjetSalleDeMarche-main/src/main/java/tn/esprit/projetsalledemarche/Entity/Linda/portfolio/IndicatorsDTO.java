package tn.esprit.projetsalledemarche.Entity.Linda.portfolio;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "indicators") // Nom de la table dans la base de données
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IndicatorsDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "symbol")
    private String symbol;

    private Double close; // Prix de clôture
    private Double macd;  // Indicateur MACD
    private Double rsi;   // Indicateur RSI
    private Integer target; // Cible (0 ou 1 pour baissière/haussière)

    // Constructeur personnalisé pour simplifier la création d'objets
    public IndicatorsDTO(String symbol, Double close, Double macd, Double rsi, Integer target) {
        this.symbol = symbol;
        this.close = close;
        this.macd = macd;
        this.rsi = rsi;
        this.target = target;
    }
}
