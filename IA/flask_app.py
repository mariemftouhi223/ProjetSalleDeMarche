from flask import Flask, jsonify, request
from flask_cors import CORS
import pandas as pd
from sqlalchemy import create_engine
from sklearn.preprocessing import MinMaxScaler
import joblib
from datetime import datetime

# Initialiser Flask
app = Flask(__name__)
CORS(app)

# Paramètres de connexion à la base de données
db_user = "root"
db_password = ""  # Laissez vide si vous n'avez pas de mot de passe
db_host = "localhost"
db_port = "3306"
db_name = "ProjetSalleDeMarcheintegre"

# Connexion à la base de données
engine = create_engine(f"mysql+pymysql://{db_user}:{db_password}@{db_host}:{db_port}/{db_name}?charset=utf8mb4")

# Charger le modèle sauvegardé
model_path = 'model_indicators_updated.pkl'
rf_model = joblib.load(model_path)

@app.route('/predict', methods=['GET', 'POST'])
def predict():
    try:
        print("[INFO] Requête reçue pour la prédiction.")

        # Récupérer les données depuis la base de données
        query = "SELECT close, macd, rsi, target FROM indicators"
        df = pd.read_sql(query, engine)

        if df.empty:
            print("[ERROR] Aucune donnée disponible dans la table 'indicators'.")
            return jsonify({"error": "Aucune donnée disponible dans la table 'indicators'."}), 400

        # Ajouter les colonnes nécessaires
        df['price_change'] = df['close'].diff()
        df['moving_average'] = df['close'].rolling(window=5).mean()
        df['rate_of_change'] = df['close'].pct_change(periods=5) * 100
        df['moving_avg_20'] = df['close'].rolling(window=20).mean()
        df['stddev_20'] = df['close'].rolling(window=20).std()
        df['upper_band'] = df['moving_avg_20'] + (2 * df['stddev_20'])
        df['lower_band'] = df['moving_avg_20'] - (2 * df['stddev_20'])
        df.dropna(inplace=True)

        if df.empty:
            print("[ERROR] Pas assez de données après nettoyage.")
            return jsonify({"error": "Pas assez de données après nettoyage."}), 400

        # Prétraiter les données
        scaler = MinMaxScaler()
        df[['rsi', 'macd']] = scaler.fit_transform(df[['rsi', 'macd']])

        # Préparer les features pour la prédiction
        X_latest = df[['rsi', 'macd', 'price_change', 'moving_average', 'rate_of_change', 'upper_band', 'lower_band']]

        # Faire la prédiction
        prediction = rf_model.predict(X_latest)
        probabilities = rf_model.predict_proba(X_latest)

        # Décision
        decision = "Maintenir"
        if prediction[-1] == 1:
            decision = "Acheter"
        elif prediction[-1] == 0:
            decision = "Vendre"

        confidence = max(probabilities[-1]) * 100

        print(f"[INFO] Décision : {decision} (Confiance : {confidence:.2f}%)")

        # Sauvegarder la décision dans la base de données
        decision_data = {
            'decision': decision,
            'confidence': confidence,
            'timestamp': datetime.now()
        }
        decision_df = pd.DataFrame([decision_data])
        decision_df.to_sql('decision_table', engine, if_exists='append', index=False)
        print("[SUCCESS] Décision sauvegardée dans la base de données.")

        # Retourner la réponse
        return jsonify({
            "decision": decision,
            "confidence": confidence,
            "timestamp": decision_data['timestamp']
        }), 200

    except Exception as e:
        print(f"[ERROR] Une erreur s'est produite : {str(e)}")
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True, port=5000)
