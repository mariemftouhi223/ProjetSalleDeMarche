package tn.esprit.projetsalledemarche.Repository.lindarepo.portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projetsalledemarche.Entity.Linda.portfolio.IndicatorsDTO;

import java.util.List;
import java.util.Optional;
@Repository
public interface IndicatorRepository extends JpaRepository<IndicatorsDTO, Long> {
    List<IndicatorsDTO> findBySymbol(String symbol);

}
