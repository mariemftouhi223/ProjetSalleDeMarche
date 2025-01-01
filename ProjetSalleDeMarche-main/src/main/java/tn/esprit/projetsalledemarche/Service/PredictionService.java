package tn.esprit.projetsalledemarche.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
@Service

public class PredictionService {

    @Autowired
    private RestTemplate restTemplate;

    // Méthode pour récupérer les données financières depuis l'API Flask
    public Map<String, Object> getMarketData() {
        // URL de l'API Flask pour obtenir les données financières
        String url = "http://localhost:6000/data";  // Assurez-vous que l'API Flask soit accessible depuis Spring

        try {
            // Appel à l'API Flask pour récupérer les données financières
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            // Retourne les données financières sous forme de map
            return response.getBody();

        } catch (Exception e) {
            // Gestion des erreurs en cas d'échec de la requête
            System.out.println("Erreur lors de la récupération des données de l'API Flask : " + e.getMessage());
            return null;
        }
    }
}