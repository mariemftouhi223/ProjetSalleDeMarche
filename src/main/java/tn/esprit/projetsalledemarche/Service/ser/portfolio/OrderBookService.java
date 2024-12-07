package tn.esprit.projetsalledemarche.Service.ser.portfolio;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.projetsalledemarche.Entity.Linda.portfolio.Order;
import tn.esprit.projetsalledemarche.Entity.Linda.portfolio.OrderBook;
import tn.esprit.projetsalledemarche.Repository.lindarepo.portfolio.OrderBookRepository;
import tn.esprit.projetsalledemarche.Repository.lindarepo.portfolio.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderBookService {

    private final OrderBookRepository orderBookRepository;
    private final OrderRepository orderRepository;

    public OrderBookService(OrderBookRepository orderBookRepository, OrderRepository orderRepository) {
        this.orderBookRepository = orderBookRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void addOrderToOrderBook(String symbol, double price, int quantity, String type) {
        // Trouver le carnet d'ordres pour le symbole ou en créer un nouveau
        OrderBook orderBook = orderBookRepository.findBySymbol(symbol)
                .orElseGet(() -> {
                    OrderBook newOrderBook = new OrderBook(symbol);
                    return orderBookRepository.save(newOrderBook);
                });

        // Déterminer la liste cible en fonction du type
        List<Order> targetOrders = type.equalsIgnoreCase("ASK")
                ? orderBook.getSellOrders() // Liste des ordres de vente
                : orderBook.getBuyOrders(); // Liste des ordres d'achat

        // Chercher un ordre existant avec le même prix
        Optional<Order> existingOrderOpt = targetOrders.stream()
                .filter(order -> order.getPrice() == price)
                .findFirst();

        if (existingOrderOpt.isPresent()) {
            // Mettre à jour la quantité de l'ordre existant
            Order existingOrder = existingOrderOpt.get();
            existingOrder.setQuantity(existingOrder.getQuantity() + quantity);
        } else {
            // Créer un nouvel ordre si aucun ordre existant n'est trouvé
            Order newOrder = new Order(symbol, price, quantity, type, LocalDateTime.now());
            newOrder.setOrderBook(orderBook);
            targetOrders.add(newOrder); // Ajouter le nouvel ordre à la liste cible
        }

        // Sauvegarder le carnet d'ordres avec les modifications
        orderBookRepository.save(orderBook);
    }
}
