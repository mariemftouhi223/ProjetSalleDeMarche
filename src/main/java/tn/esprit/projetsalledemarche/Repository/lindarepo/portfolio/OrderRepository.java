package tn.esprit.projetsalledemarche.Repository.lindarepo.portfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projetsalledemarche.Entity.Linda.portfolio.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByTypeOrderByPriceAsc(String type); // Pour "ASK"
    List<Order> findByTypeOrderByPriceDesc(String type); // Pour "BID"
    List<Order> findBySymbolAndTypeOrderByPriceDesc(String symbol, String type);

    List<Order> findBySymbolAndTypeOrderByPriceAsc(String symbol, String type);
}
