package tn.esprit.projetsalledemarche.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.projetsalledemarche.Service.PredictionService;

import java.util.Map;

@RestController
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    // Exposer les données de prédiction via un endpoint
    @GetMapping("/api/market-data")
    public Map<String, Object> getMarketData() {
        return predictionService.getMarketData();
    }
}