package tn.esprit.projetsalledemarche.Service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class SentimentService {

    private static final String API_URL = "http://127.0.0.1:5000/analyze-sentiment";

    public String analyzeSentiment(String text) {
        // Configurer les en-têtes
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Créer le corps de la requête
        String requestBody = "{\"text\":\"" + text + "\"}";
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // Appeler l'API Python
        RestTemplate restTemplate = new RestTemplate();

        try {
            JsonNode response = restTemplate.exchange(API_URL, HttpMethod.POST, request, JsonNode.class).getBody();

            // Retourner le sentiment (positif/négatif/neutral)
            if (response != null && response.isArray()) {
                return response.get(0).get("label").asText();
            }
        } catch (HttpClientErrorException e) {
            // Gestion d'erreur si l'API Flask retourne une erreur HTTP
            e.printStackTrace();
        } catch (Exception e) {
            // Gestion d'autres erreurs (par exemple, problème de connexion)
            e.printStackTrace();
        }

        // Retourner "Unknown" en cas d'erreur
        return "Unknown";
    }
}