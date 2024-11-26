package com.trading.projet.Services;

import com.trading.projet.Entities.IndicatorsDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class IndicatorService {

    private final RestTemplate restTemplate = new RestTemplate();
    // Simulez une base de données avec une Map
    private Map<String, IndicatorsDTO> indicatorsStorage = new HashMap<>();

    public void saveIndicators(String symbol, IndicatorsDTO indicators) {
        // On utilise simplement l'indicateur passé en paramètre.
        // Sauvegarde l'indicateur sous le symbole donné
        indicatorsStorage.put(symbol, indicators);
    }

    public IndicatorsDTO getIndicators(String symbol) {
        // Retourne l'indicateur pour un symbole donné ou null si aucun indicateur n'est trouvé
        return indicatorsStorage.get(symbol);
    }
}
