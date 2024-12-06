package tn.esprit.projetsalledemarche.Service.ser.portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.Linda.portfolio.OrderDTO;
import tn.esprit.projetsalledemarche.Entity.Linda.portfolio.Portfolio;
import tn.esprit.projetsalledemarche.Entity.Linda.portfolio.Position;
import tn.esprit.projetsalledemarche.Entity.Linda.user.User;
import tn.esprit.projetsalledemarche.Repository.lindarepo.portfolio.MarketDataRepository;
import tn.esprit.projetsalledemarche.Repository.lindarepo.portfolio.PortfolioRepository;
import tn.esprit.projetsalledemarche.Repository.lindarepo.portfolio.PositionRepository;
import tn.esprit.projetsalledemarche.Repository.lindarepo.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PositionService {
    @Autowired
    PortfolioService portfolioService;
    MarketDataService marketDataService;
    MarketDataRepository marketDataRepository;
    UserRepository userRepository;
    PortfolioRepository portfolioRepository;
    @Autowired
    private PositionRepository positionRepository;
    public  PositionService(PositionRepository positionRepository,MarketDataRepository marketDataRepository, MarketDataService marketDataService,UserRepository userRepository, PortfolioService portfolioService, PortfolioRepository portfolioRepository){
        this.portfolioService=portfolioService;
        this.marketDataService=marketDataService;
        this.marketDataRepository=marketDataRepository;
        this.userRepository=userRepository;
        this.portfolioRepository=portfolioRepository;
        this.positionRepository = positionRepository;
    }
//    public List<Position> getAllOpenPositions(){
//        return this.positionRepository.findByIsOpenTrue();
//    }

    public List<Position> getAllOpenPositions() {
        List<Position> openPositions = this.positionRepository.findByIsOpenTrue();
        return openPositions; // Retourner la liste des positions avec les mises à jour
    }



    //    public Double getCurrentMarketPrice(String symbol) {
//
//        return marketDataService.getCurrentMarketPrice(symbol);
//    }
    public Double updateCurrentPrice(String symbol, double currentPrice) {
        // Récupérer toutes les positions basées sur le symbole
        List<Position> positions = positionRepository.findBySymbol(symbol);

        // Vérifier si la liste de positions est vide
        if (positions.isEmpty()) {
            throw new RuntimeException("Position not found for symbol: " + symbol);
        }

        // Mettre à jour le prix courant pour chaque position avec le même symbole
        for (Position position : positions) {
            // Vérifier si le portfolio est valide
            if (position.getPortfolio() == null || position.getPortfolio().getId() == null) {
                throw new RuntimeException("Portfolio not found for position with symbol: " + symbol);
            }
            position.setCurrentPrice(currentPrice); // Mettez à jour le prix courant
            positionRepository.save(position); // Sauvegarder les changements
        }

        return currentPrice; // Retourne le prix actuel pour l'utiliser ailleurs si nécessaire
    }


    public List<Position> findBySymbol(String symbol) {
        return positionRepository.findBySymbol(symbol);
    }


    public Position openPosition(String symbol, int quantity, double entryPrice, double currentPrice, double stopLoss, double takeProfit, String positionType, LocalDateTime openedAt, LocalDateTime closedAt, Long portfolioId, Long userId) {
        // Rechercher le Portfolio par son ID
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found"));

        // Rechercher l'Utilisateur par son ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Position position = new Position();
        position.setSymbol(symbol);
        position.setQuantity(quantity);
        position.setCurrentPrice(currentPrice);
        position.setEntryPrice(entryPrice);
        position.setStopLoss(stopLoss);
        position.setTakeProfit(takeProfit);
        position.setPositionType(positionType);
        position.setOpen(true);
        position.setEntryTime(LocalDateTime.now());
        position.setOpenedAt(openedAt != null ? openedAt : LocalDateTime.now());
        position.setClosedAt(closedAt);
        position.setPortfolio(portfolio);
        position.setUser(user);

        // Calculer profitOrLoss à l'aide de la méthode getProfitOrLoss
        // position.setProfitOrLoss(position.getProfitOrLoss(currentPrice));

        return positionRepository.save(position);
    }


    public List<OrderDTO> getActiveOrders() {
        // Récupérer toutes les positions ouvertes depuis le dépôt
        List<Position> positions = positionRepository.findByIsOpenTrue();

        // Convertir chaque Position en OrderDTO pour simplifier l'affichage
        return positions.stream()
                .map(this::convertToOrderDTO) // Transformation en DTO
                .toList();
    }

    // Méthode de conversion Position -> OrderDTO
    private OrderDTO convertToOrderDTO(Position position) {
        return new OrderDTO(
                position.getSymbol(),
                position.getEntryPrice(),
                position.getQuantity(),
                position.getPositionType().equalsIgnoreCase("buy") ? "BID" : "ASK",
                position.getEntryTime()
        );
    }

    public void closePosition(Position position, double currentPrice) {
        double profitOrLoss = currentPrice - position.getEntryPrice();

        // Mettre à jour le solde du portefeuille
        portfolioService.updateBalance(position.getPortfolio().getId(), profitOrLoss);

        // Mettre à jour les détails de la position
        position.setClosedAt(LocalDateTime.now());
        position.setOpen(false);
        // Ou position.setIsOpen(false) si c'est un Boolean

        // Enregistrer la position mise à jour
        positionRepository.save(position);
    }

    public Optional<Position> getPositionById(Long id) {
        return positionRepository.findById(id);
    }


}
