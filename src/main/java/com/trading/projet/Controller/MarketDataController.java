package com.trading.projet.Controller;

import com.trading.projet.Entities.*;
import com.trading.projet.Repositories.MarketDataRepository;
import com.trading.projet.Repositories.PortfolioRepository;
import com.trading.projet.Repositories.PositionRepository;
import com.trading.projet.Services.IndicatorService;
import com.trading.projet.Services.MarketDataService;
import com.trading.projet.Services.PortfolioService;
import com.trading.projet.Services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import com.trading.projet.Entities.User;

import java.time.LocalDateTime;
import java.util.*;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.client.RestTemplate;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@CrossOrigin(origins = "http://localhost:4200")
@RestController // Changez @Controller à @RestController
@RequestMapping("/api")
public class MarketDataController {

    private  final PortfolioService portfolioService;// Assurez-vous que ce service est injecté correctement
    private final RestTemplate restTemplate;
    private final PositionService positionService;
    private final PositionRepository positionRepository;
    private final PortfolioRepository portfolioRepository;
    private final MarketDataService marketDataService;
    private final IndicatorService indicatorService;
    private final MarketDataRepository marketDataRepository;
    private List<double[]> priceHistory = new ArrayList<>();
    private String selectedSymbol;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MarketDataController(RestTemplate restTemplate,PortfolioRepository portfolioRepository,PositionRepository positionRepository, PositionService positionService, MarketDataService marketDataService, IndicatorService indicatorService, MarketDataRepository marketDataRepository, SimpMessagingTemplate messagingTemplate,PortfolioService portfolioService) {
        this.restTemplate = restTemplate;
        this.portfolioRepository=portfolioRepository;
        this.positionRepository=positionRepository;
        this.positionService = positionService;
        this.marketDataService = marketDataService;
        this.indicatorService = indicatorService;
        this.marketDataRepository = marketDataRepository;
        this.messagingTemplate = messagingTemplate;
        this.portfolioService=portfolioService;
    }

    // Envoie les mises à jour du carnet d'ordre par WebSocket
    //@Scheduled(fixedRate = 1000)
    public void sendOrderBookUpdates() {
        List<OrderDTO> orderBook = positionService.getActiveOrders();
        messagingTemplate.convertAndSend("/topic/orderBook", orderBook);
    }

    @PostMapping("/send-prices")
    public ResponseEntity<String> receivePrices(@RequestBody double[] prices) {
        // Stocker chaque prix dans la base de données
        for (double price : prices) {
            MarketData marketData = new MarketData();
            marketData.setPrice(price);
            marketData.setTimestamp(LocalDateTime.now());
            marketDataRepository.save(marketData);
        }

        // Affiche les prix dans la console
        System.out.println("Prix reçus : " + Arrays.toString(prices));
        return ResponseEntity.ok("Prix reçus avec succès !");
    }

    @GetMapping("/market-data")
    public List<MarketData> getMarketData() {
        return marketDataService.getAllMarketData();
    }

    @GetMapping("/get-prices")
    public List<double[]> getPrices() {
        return priceHistory;
    }

    @GetMapping("/get-prices/{symbol}")
    public ResponseEntity<List<MarketData>> getPrices(@PathVariable String symbol) {
        List<MarketData> marketData = marketDataService.getMarketData(symbol);

        if (marketData == null || marketData.isEmpty()) {
            return ResponseEntity.noContent().build(); // Renvoie 204 No Content
        } else {
            return ResponseEntity.ok(marketData); // Renvoie 200 OK avec les données
        }
    }

    @PostMapping("/send-prices/{symbol}")
    public ResponseEntity<Void> sendPrices(@PathVariable String symbol, @RequestBody List<Double> prices) {
        for (double price : prices) {
            MarketData marketData = new MarketData();
            marketData.setSymbol(symbol);
            marketData.setPrice(price);
            marketData.setTimestamp(LocalDateTime.now());

            marketDataRepository.save(marketData);
            System.out.println("Prix sauvegardé : " + price);
        }
        return ResponseEntity.ok().build();
    }


    @Scheduled(fixedRate = 60000) // Exécute toutes les 60 secondes
    public void removeOldData() {
        marketDataService.deleteOldData();
    }


    public void startTradingSimulation() {
        String flaskUrl = "http://localhost:5000/start-simulation";  // Changez le port si nécessaire
        String response = restTemplate.getForObject(flaskUrl, String.class);
        System.out.println("Response from Flask: " + response);
    }

    @PostMapping("/set-selected-symbol")
    public ResponseEntity<String> setSelectedSymbol(@RequestBody Map<String, String> request) {
        this.selectedSymbol = request.get("symbol"); // Récupérer le symbole du corps de la requête
        return ResponseEntity.ok("Symbole sélectionné mis à jour !");
    }

    // Endpoint pour récupérer le symbole sélectionné
    @GetMapping("/get-selected-symbol")
    public ResponseEntity<Map<String, String>> getSelectedSymbol() {
        Map<String, String> response = new HashMap<>();
        response.put("symbol", this.selectedSymbol); // Renvoyer le symbole sélectionné
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all-symbols")
    public ResponseEntity<List<String>> getAllSymbols() {
        List<String> symbols = Arrays.asList("AAPL", "GOOGL", "AMZN", "MSFT"); // Exemple de symboles
        return ResponseEntity.ok(symbols);
    }

    @PostMapping("/enter-position")
    public ResponseEntity<Position> enterPosition(
            @RequestBody Position positionRequest,
            @RequestParam Long portfolioId,
            @RequestParam Long userId) {

        Position newPosition = positionService.openPosition(
                positionRequest.getSymbol(),
                positionRequest.getQuantity(),
                positionRequest.getEntryPrice(),
                positionRequest.getCurrentPrice(),
                positionRequest.getStopLoss(),
                positionRequest.getTakeProfit(),
                positionRequest.getPositionType(),
                positionRequest.getOpenedAt(),
                positionRequest.getClosedAt(),
                portfolioId,
                userId
        );

        return ResponseEntity.ok(newPosition);
    }

    @GetMapping("/positions")
    public List<Position> getAllPositions() {
        return positionService.getAllOpenPositions();
    }
    

    @GetMapping("/{symbol}")
    public List<Position> getPositionBySymbol(@PathVariable String symbol) {
        return positionService.findBySymbol(symbol);
    }

    @PutMapping("/update-current-price")
    public ResponseEntity<?> updateCurrentPrice(@RequestParam String symbol, @RequestParam double currentPrice) {
        try {
            positionService.updateCurrentPrice(symbol, currentPrice);
            return ResponseEntity.ok().build();
        } catch (NullPointerException e) {
            // Log l'erreur pour le débogage
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour du prix : " + e.getMessage());
        }
    }

    @PostMapping("/send-indicators/{symbol}")
    public ResponseEntity<Void> sendIndicators(@PathVariable String symbol, @RequestBody IndicatorsDTO indicators) {
        indicatorService.saveIndicators(symbol, indicators);
        return ResponseEntity.ok().build(); // Retourne 200 OK
    }

    @GetMapping("/get-indicators/{symbol}")
    public ResponseEntity<IndicatorsDTO> getIndicator(@PathVariable String symbol) {
        IndicatorsDTO indicator = indicatorService.getIndicators(symbol);
        if (indicator != null) {
            return ResponseEntity.ok(indicator);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/positions/close/{id}")
    public ResponseEntity<String> closePosition(@PathVariable Long id) {
        Optional<Position> optionalPosition = positionRepository.findById(id); // Utilisez le repository ici


        if (optionalPosition.isPresent()) {
            Position position = optionalPosition.get();

            // Vérifiez que le portefeuille associé à la position est non null
            if (position.getPortfolio() == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("La position n'est pas associée à un portefeuille.");
            }

            Long portfolioId = position.getPortfolio().getId();

            // Récupérer le Portfolio associé
            Optional<Portfolio> optionalPortfolio = portfolioRepository.findById(portfolioId);
            if (optionalPortfolio.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Portfolio non trouvé pour l'ID : " + portfolioId);
            }

            Portfolio portfolio = optionalPortfolio.get();

            // Obtenir le prix actuel du marché
            double currentPrice;
            try {
                currentPrice = marketDataService.getPrice(position.getSymbol());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erreur lors de la récupération du prix actuel : " + e.getMessage());
            }

            // Mettre à jour le solde du portfolio avec le profit ou la perte
//            double profitOrLoss = position.getProfitOrLoss(currentPrice);
//            try {
//                portfolioService.updateBalance(portfolioId, profitOrLoss);
//            } catch (Exception e) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .body("Erreur lors de la mise à jour du solde du portefeuille : " + e.getMessage());
//            }

            // Fermer la position
            try {
                positionService.closePosition(position, currentPrice);

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erreur lors de la fermeture de la position : " + e.getMessage());
            }

            return ResponseEntity.ok("Position fermée avec succès.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Position non trouvée.");
    }

    @GetMapping("/get-balence")
    public double getBalence(Long id) {
        return portfolioService.getBalenceByPortfolio(id);
    }

   }





