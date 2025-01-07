package tn.esprit.projetsalledemarche.Repository.lindarepo.portfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.Linda.portfolio.Position;

import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {

    List<Position> findByIsOpenTrue();
    List<Position> findBySymbol(String symbol);

    Optional<Position> getPositionById(Long id);
}
