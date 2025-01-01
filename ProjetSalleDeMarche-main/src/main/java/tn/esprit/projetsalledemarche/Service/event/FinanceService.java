package tn.esprit.projetsalledemarche.Service.event;

import com.google.api.client.util.Value;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import weka.classifiers.functions.LinearRegression;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Attribute;
import java.util.ArrayList;
import java.util.List;

@Service
public class FinanceService {

    @Value("${alpha.vantage.api.key}")
    private String apiKey;

    private final String API_URL = "https://www.alphavantage.co/query";

    // Méthode pour récupérer les données de prix d'une action
    public String getStockData(String symbol) {
        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL + "?function=TIME_SERIES_DAILY&symbol=" + symbol + "&apikey=" + apiKey;
        String response = restTemplate.getForObject(url, String.class);
        System.out.println("Données récupérées : " + response); // Ajoutez cette ligne pour déboguer
        return response;
    }

    // Méthode pour préparer les données de prix de clôture
    public List<Double> prepareData(String stockData) {
        List<Double> prices = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(stockData);

        // Vérifiez si "Time Series (Daily)" existe
        if (jsonObject.has("Time Series (Daily)")) {
            JSONObject timeSeries = jsonObject.getJSONObject("Time Series (Daily");

            // Parcourir les dates pour obtenir les prix de clôture
            for (String date : timeSeries.keySet()) {
                JSONObject dailyData = timeSeries.getJSONObject(date);
                double closingPrice = dailyData.getDouble("4. close");
                prices.add(closingPrice);
            }
        } else {
            System.out.println("Aucune donnée de prix trouvée pour le symbole : " + stockData);
        }
        return prices;
    }


    // Méthode pour entraîner le modèle de régression linéaire
    public LinearRegression trainModel(List<Double> prices) throws Exception {
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("closingPrice"));

        // Créer un ensemble d'instances
        Instances dataset = new Instances("StockPrices", attributes, prices.size());

        for (double price : prices) {
            Instance instance = new DenseInstance(1);
            instance.setValue(attributes.get(0), price);
            dataset.add(instance);
        }

        dataset.setClassIndex(0); // Définir la classe

        // Entraîner le modèle de régression linéaire
        LinearRegression model = new LinearRegression();
        model.buildClassifier(dataset);
        return model;
    }

    // Méthode pour prédire le prix en utilisant le modèle
    public double predictPrice(LinearRegression model, double previousPrice) throws Exception {
        Instance instance = new DenseInstance(1);
        instance.setValue(0, previousPrice);
        return model.classifyInstance(instance);
    }

    public boolean effectuerPaiement(Long idUtilisateur, double montant, String methodePaiement) {
        // Exemple de logique fictive pour simuler un paiement réussi
        System.out.println("Paiement effectué pour l'utilisateur " + idUtilisateur
                + " avec la méthode " + methodePaiement
                + " pour un montant de " + montant);
        return true; // Retourne true pour indiquer que le paiement est réussi
    }


}