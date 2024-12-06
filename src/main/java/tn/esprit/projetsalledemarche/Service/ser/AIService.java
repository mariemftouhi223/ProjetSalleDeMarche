package tn.esprit.projetsalledemarche.Service.ser;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AIService {

    private static final String IA_API_URL = "http://localhost:5000/recommandations"; // L'URL de votre API Flask

    public String getRecommandations() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(IA_API_URL, String.class);
        return response.getBody();
    }
}
