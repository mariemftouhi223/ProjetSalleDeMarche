package tn.esprit.projetsalledemarche.Service.ser.portfolio;

import tn.esprit.projetsalledemarche.Entity.Linda.portfolio.Order;

import tn.esprit.projetsalledemarche.Repository.lindarepo.portfolio.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Ajoute un nouvel ordre dans la base de données.
     * @param order L'ordre à ajouter.
     * @return L'ordre enregistré avec son ID généré.
     */
    public Order addOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("L'ordre ne peut pas être null.");
        }
        order.setTimestamp(LocalDateTime.now()); // Ajout du timestamp actuel
        return orderRepository.save(order);
    }
    public List<Order> getOrderBook1(String type, String symbol) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Le type d'ordre ne peut pas être null ou vide.");
        }
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Le symbole ne peut pas être null ou vide.");
        }

        switch (type.toUpperCase()) {
            case "BID":
                return orderRepository.findBySymbolAndTypeOrderByPriceDesc(symbol, "BID");
            case "ASK":
                return orderRepository.findBySymbolAndTypeOrderByPriceAsc(symbol, "ASK");
            default:
                throw new IllegalArgumentException("Type d'ordre invalide : " + type);
        }
    }

    /**
     * Récupère les ordres selon leur type (BID ou ASK).
     * @param type Le type d'ordre (BID ou ASK).
     * @return La liste des ordres triée par prix.
     */
    public List<Order> getOrderBook(String type) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Le type d'ordre ne peut pas être null ou vide.");
        }
        switch (type.toUpperCase()) {
            case "BID":
                return orderRepository.findByTypeOrderByPriceDesc("BID");
            case "ASK":
                return orderRepository.findByTypeOrderByPriceAsc("ASK");
            default:
                throw new IllegalArgumentException("Type d'ordre invalide : " + type);
        }
    }

    /**
     * Annule un ordre en fonction de son ID.
     * @param id L'ID de l'ordre à annuler.
     */
    public void cancelOrder(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID de l'ordre ne peut pas être null.");
        }
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Aucun ordre trouvé avec l'ID : " + id);
        }
        orderRepository.deleteById(id);
    }
}

