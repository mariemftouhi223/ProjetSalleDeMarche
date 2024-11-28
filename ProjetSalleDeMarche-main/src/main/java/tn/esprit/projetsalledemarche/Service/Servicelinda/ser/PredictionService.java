package tn.esprit.projetsalledemarche.Service.Servicelinda.ser;

import org.json.JSONObject;
import weka.classifiers.functions.LinearRegression;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Attribute;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PredictionService {

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

        // Journaliser la liste des prix
        System.out.println("Prix récupérés : " + prices);
        return prices;
    }



    public LinearRegression trainModel(List<Double> prices) throws Exception {
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("closingPrice"));

        Instances dataset = new Instances("StockPrices", attributes, prices.size());

        for (Double price : prices) {
            Instance instance = new DenseInstance(1);
            instance.setValue(attributes.get(0), price);
            dataset.add(instance);
        }

        dataset.setClassIndex(0); // Définir la classe

        LinearRegression model = new LinearRegression();
        model.buildClassifier(dataset);
        return model;
    }

    public double predictPrice(LinearRegression model, double previousPrice) throws Exception {
        Instance instance = new DenseInstance(1);
        instance.setValue(0, previousPrice);
        return model.classifyInstance(instance);
    }
}
