package tn.esprit.projetsalledemarche.Repository.lindarepo.portfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.Linda.portfolio.Portfolio;

import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio ,Long> {

    Optional<Portfolio> findByUserId(Long userId);

    Optional<Portfolio> findPortfolioById(Long id);


}