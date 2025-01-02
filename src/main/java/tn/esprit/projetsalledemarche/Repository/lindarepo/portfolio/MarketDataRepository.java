package tn.esprit.projetsalledemarche.Repository.lindarepo.portfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.Linda.portfolio.MarketData;

import java.time.LocalDateTime;
import java.util.List;

public interface MarketDataRepository extends JpaRepository<MarketData, Long> {
    List<MarketData> findByTimestampBefore(LocalDateTime timestamp);
    //  @Query("SELECT md FROM MarketData md WHERE md.symbol = ?1 ORDER BY md.timestamp DESC")
    MarketData findTopBySymbolOrderByTimestampDesc(String symbol);


    List<MarketData> findBySymbol(String symbol);
}

