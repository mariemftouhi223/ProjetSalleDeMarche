package tn.esprit.projetsalledemarche.Entity.Linda.portfolio;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.projetsalledemarche.Entity.Linda.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // Nom du portfolio
    private Double balence;  // Valeur totale du portefeuille

    // Liste d'actifs (reliée à une entité Asset)
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Asset> assets;  // Actifs détenus dans le portfolio

    // Liste d'actifs (reliée à une entité Asset)
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Position> positions;  // Actifs détenus dans le portfolio

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
    // Date de la dernière mise à jour
    private LocalDateTime lastUpdated;
}