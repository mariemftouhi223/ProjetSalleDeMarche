package tn.esprit.projetsalledemarche.Repository.lindarepo.portfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.Linda.portfolio.OrderBook;

import java.util.Optional;

public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {
    Optional<OrderBook> findBySymbol(String symbol);
}

