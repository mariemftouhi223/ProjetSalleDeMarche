package com.trading.projet.Repositories;

import com.trading.projet.Entities.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface PortfolioRepository extends JpaRepository <Portfolio ,Long> {

    Optional<Portfolio> findByUserId(Long userId);

    Optional<Portfolio> findPortfolioById(Long id);


}

