package tn.esprit.projetsalledemarche.Entity.Linda.formation;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.projetsalledemarche.Entity.Linda.user.Formation;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormationModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre; // Titre du module

    private String description; // Description du module

    private String theme; // Thème spécifique du module (par exemple, gestion des risques, analyse technique)

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation; // Formation associée à ce module
    @OneToMany(mappedBy = "module")
    private List<ModulePerformance> modulePerformances; // Liste des performances pour ce module
}

