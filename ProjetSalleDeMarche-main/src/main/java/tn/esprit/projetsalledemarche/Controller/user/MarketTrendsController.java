package tn.esprit.projetsalledemarche.Controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tn.esprit.projetsalledemarche.Entity.Linda.user.RecommandationResponse;
import tn.esprit.projetsalledemarche.Service.MarketTrendsService;

import java.util.List;
@RequestMapping("/recommendations")

@RestController
public class MarketTrendsController {
    @Autowired
    private MarketTrendsService marketDataService;

    // Endpoint pour obtenir toutes les recommandations
    @GetMapping("/recommendation")
    public List<RecommandationResponse> getRecommendation() {
        return marketDataService.getRecommandation();  // Retourne une liste de recommandations
    }
}
