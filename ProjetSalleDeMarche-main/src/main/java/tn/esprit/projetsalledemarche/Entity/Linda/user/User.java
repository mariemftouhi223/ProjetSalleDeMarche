package tn.esprit.projetsalledemarche.Entity.Linda.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.projetsalledemarche.Entity.Linda.formation.Progression;
import tn.esprit.projetsalledemarche.Entity.Linda.portfolio.Portfolio;
import tn.esprit.projetsalledemarche.Entity.Linda.portfolio.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private double portfolioBalance;
    private String password;
    private String email;
    private String firstName; // Prénom
    private String lastName;
    private double initialBalance = 0.0;

    @OneToMany(mappedBy = "user")
    private Set<Position> positions; // Liste des positions de l'utilisateur

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Portfolio> portfolios;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<Role> roles = new ArrayList<>(); // Gardez ce champ pour gérer la relation Many-to-Many

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviews;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Progression> progressions;
    // Constructeur par défaut
    public User() {}
}
