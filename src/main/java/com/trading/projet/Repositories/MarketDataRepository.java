package com.trading.projet.Repositories;

import com.trading.projet.Entities.MarketData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MarketDataRepository extends JpaRepository<MarketData, Long> {
    List<MarketData> findByTimestampBefore(LocalDateTime timestamp);
  //  @Query("SELECT md FROM MarketData md WHERE md.symbol = ?1 ORDER BY md.timestamp DESC")
    MarketData findTopBySymbolOrderByTimestampDesc(String symbol);


    List<MarketData> findBySymbol(String symbol);
}
