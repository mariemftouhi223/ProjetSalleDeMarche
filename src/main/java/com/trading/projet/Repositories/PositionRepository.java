package com.trading.projet.Repositories;

import com.trading.projet.Entities.MarketData;
import com.trading.projet.Entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {

    List<Position> findByIsOpenTrue();
    List<Position> findBySymbol(String symbol);

    Optional<Position> getPositionById(Long id);
}
