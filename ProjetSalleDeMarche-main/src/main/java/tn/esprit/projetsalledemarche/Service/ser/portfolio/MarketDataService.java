package tn.esprit.projetsalledemarche.Service.ser.portfolio;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.Linda.portfolio.MarketData;
import tn.esprit.projetsalledemarche.Repository.lindarepo.portfolio.MarketDataRepository;
import tn.esprit.projetsalledemarche.Repository.lindarepo.portfolio.PortfolioRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MarketDataService {

    @Autowired
    private MarketDataRepository marketDataRepository;
    @Autowired
    private PortfolioRepository portfolioRepository;
    public void savePrices(String symbol, List<Double> prices)  {
        for (double price : prices) {
            MarketData marketData = new MarketData();
            marketData.setSymbol(symbol); // Associer le symbole à chaque donnée de marché
            marketData.setPrice(price);

            marketData.setTimestamp(LocalDateTime.now());
            marketDataRepository.save(marketData); // Sauvegarder dans la base de données
        }
    }

    public List<MarketData> getDataByInterval(String interval) {
        LocalDateTime startTime = calculateStartTime(interval);
        return marketDataRepository.findByTimestampAfter(startTime);
    }

    private LocalDateTime calculateStartTime(String interval) {
        switch (interval) {
            case "1min":
                return LocalDateTime.now().minusMinutes(1);
            case "5min":
                return LocalDateTime.now().minusMinutes(5);
            case "1H":
                return LocalDateTime.now().minusHours(1);
            case "5H":
                return LocalDateTime.now().minusHours(5);
            case "1J":
                return LocalDateTime.now().minusDays(1);
            case "7J":
                return LocalDateTime.now().minusDays(7);
            case "1Mois":
                return LocalDateTime.now().minusMonths(1);
            default:
                return LocalDateTime.now().minusDays(1);
        }
    }
    public double getPrice(String symbol) {
        List<MarketData> marketDataList = marketDataRepository.findBySymbol(symbol);
        if (marketDataList.isEmpty()) {
            return 0.0; // Aucun prix trouvé
        }
        // Retourne le dernier prix
        return marketDataList.get(marketDataList.size() - 1).getPrice();
    }
    public Double findCurrentMarketPrice(String symbol) {
        // Récupérer le dernier enregistrement de données de marché pour le symbole donné
        MarketData marketData = marketDataRepository.findTopBySymbolOrderByTimestampDesc(symbol);

        // Vérifiez si des données de marché ont été trouvées
        if (marketData != null) {
            Double currentPrice = marketData.getPrice();
            System.out.println("Current market price for " + symbol + ": " + currentPrice); // Affiche le prix dans la console
            return currentPrice; // Retournez le prix actuel
        } else {
            throw new RuntimeException("No market data found for symbol: " + symbol);
        }
    }

    @Transactional
    public void deleteOldData() {
        LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(15);
        List<MarketData> oldData = marketDataRepository.findByTimestampBefore(fiveMinutesAgo);
        marketDataRepository.deleteAll(oldData);
    }
    public List<MarketData> getAllMarketData() {
        return marketDataRepository.findAll();
    }


    public List<MarketData> getMarketData(String symbol) {
        return marketDataRepository.findBySymbol(symbol);
    }



    public List<MarketData> getAllPrices() {
        return marketDataRepository.findAll();
    }
}
