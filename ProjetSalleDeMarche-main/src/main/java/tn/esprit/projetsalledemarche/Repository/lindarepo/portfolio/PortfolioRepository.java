package tn.esprit.projetsalledemarche.Repository.lindarepo.portfolio;

import tn.esprit.projetsalledemarche.Entity.Linda.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.Linda.portfolio.Portfolio;

import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio ,Long> {

    Optional<Portfolio> findByUserId(int user_id);
    Optional<Portfolio> findByUser(User user);
    Optional<Portfolio> findPortfolioById(Long id);


}