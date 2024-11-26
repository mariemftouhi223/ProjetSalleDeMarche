package com.trading.projet.Entities;
// Classe pour les mises à jour de prix
public class PriceUpdate {
    private String symbol;
    private double price;

    public PriceUpdate(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    // Getters et Setters
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
