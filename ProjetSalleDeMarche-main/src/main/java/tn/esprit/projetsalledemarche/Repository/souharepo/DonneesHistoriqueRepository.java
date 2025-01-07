package tn.esprit.projetsalledemarche.Repository.souharepo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.Souha.DonneesHistoriques;

public interface DonneesHistoriqueRepository extends JpaRepository<DonneesHistoriques, Long> {
}
