package tn.esprit.projetsalledemarche.Service.ser.portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tn.esprit.projetsalledemarche.Entity.Linda.portfolio.IndicatorsDTO;
import tn.esprit.projetsalledemarche.Repository.lindarepo.portfolio.IndicatorRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class IndicatorService {
    private final RestTemplate restTemplate = new RestTemplate();
    // Simulez une base de données avec une Map
    private Map<String, IndicatorsDTO> indicatorsStorage = new HashMap<>();
    @Autowired
    private IndicatorRepository indicatorRepository;


    public void saveIndicator(String symbol, IndicatorsDTO indicators) {
        // Assigner le symbole à l'indicateur avant de le sauvegarder
        indicators.setSymbol(symbol);
        indicatorRepository.save(indicators);
    }

    public List<IndicatorsDTO> getIndicators(String symbol) {
        return indicatorRepository.findBySymbol(symbol);
    }


}
