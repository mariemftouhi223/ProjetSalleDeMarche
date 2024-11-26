package com.trading.projet.Services;

import com.trading.projet.Entities.Portfolio;
import com.trading.projet.Entities.Position;
import com.trading.projet.Repositories.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PortfolioService {  // Renommé pour respecter les conventions de nommage

    private  PortfolioRepository portfolioRepository;

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
