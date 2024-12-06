package tn.esprit.projetsalledemarche.Entity.Linda.portfolio;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


public class IndicatorsDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double close ;
    private Double macd; // Change de List<Double> à Double
    private Double rsi;  // Change de List<Double> à Double
    private Integer target; // Change de List<Integer> à Integer
    @OneToMany(mappedBy = "indicators", cascade = CascadeType.ALL)
    private List<MarketData> marketDataList;
    // Constructeur
    public IndicatorsDTO(Double close,Double macd, Double rsi, Integer target) {
        this.close = close;
        this.macd = macd;
        this.rsi = rsi;
        this.target = target;
    }
    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }
    // Getters et Setters
    public Double getMacd() {
        return macd;
    }

    public void setMacd(Double macd) {
        this.macd = macd;
    }

    public Double getRsi() {
        return rsi;
    }

    public void setRsi(Double rsi) {
        this.rsi = rsi;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }
}
