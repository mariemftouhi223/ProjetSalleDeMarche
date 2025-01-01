package tn.esprit.projetsalledemarche.Service.portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.Linda.portfolio.Portfolio;
import tn.esprit.projetsalledemarche.Repository.lindarepo.portfolio.PortfolioRepository;

import java.util.Optional;

@Service
public class PortfolioService {
    private PortfolioRepository portfolioRepository;

    @Autowired
    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public void updateBalance(Long id, double profitOrLoss) {
        // Récupérer le portefeuille par ID utilisateur
        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Portefeuille introuvable pour l'utilisateur ID : " + id));

        // Calculer le nouveau solde
        double newBalance = portfolio.getBalence() + profitOrLoss;  // Correction du nom de la méthode

        // Mettre à jour le solde dans l'objet portefeuille
        portfolio.setBalence(newBalance);  // Correction du nom de la méthode

        // Sauvegarder les modifications
        portfolioRepository.save(portfolio);
    }

    public double getBalenceByPortfolio(Long id) {
        Optional<Portfolio> portfolio=portfolioRepository.findPortfolioById(id);
        double Balence=portfolio.get().getBalence();
        return  Balence;
    }
}
