package tn.esprit.projetsalledemarche.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tn.esprit.projetsalledemarche.Entity.Linda.user.RecommandationResponse;

import java.util.List;

@Service
public class MarketTrendsService {

    @Autowired
    private RestTemplate restTemplate;

    public List<RecommandationResponse> getRecommandation() {
        // URL de l'API Flask sans paramètre de symbole
        String url = "http://localhost:5001/best_symbols";  // Assurez-vous que l'API Flask soit accessible depuis Spring

        // Appel à l'API Flask pour récupérer une liste de recommandations
        ResponseEntity<List<RecommandationResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RecommandationResponse>>() {}
        );

        // Retourne la liste des recommandations avec les nouveaux champs (symbol et performance)
        return response.getBody();
    }
}
