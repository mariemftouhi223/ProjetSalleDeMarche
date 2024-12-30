import time
import joblib
import pandas as pd
from sqlalchemy import create_engine
from sklearn.preprocessing import MinMaxScaler
from datetime import datetime

# Paramètres de connexion à la base de données
db_user = "root"
db_password = ""  # Laissez vide si vous n'avez pas de mot de passe
db_host = "localhost"
db_port = "3306"
db_name = "ProjetSalleDeMarcheintegre"

# Connexion à la base de données
try:
    engine = create_engine(f"mysql+pymysql://{db_user}:{db_password}@{db_host}:{db_port}/{db_name}?charset=utf8mb4")
    print("[INFO] Connexion à la base de données réussie.")
except Exception as e:
    print(f"[ERREUR] Échec de la connexion à la base de données : {e}")
    exit(1)

# Charger le modèle sauvegardé
try:
    model_path = 'model_indicators_updated.pkl'
    rf_model = joblib.load(model_path)
    print("[INFO] Modèle chargé avec succès.")
except FileNotFoundError:
    print("[ERREUR] Le fichier du modèle n'a pas été trouvé.")
    exit(1)
except Exception as e:
    print(f"[ERREUR] Une erreur s'est produite lors du chargement du modèle : {e}")
    exit(1)

# Fonction pour récupérer les données et effectuer les prédictions
def update_and_predict():
    try:
        # Récupérer les dernières données depuis la base de données
        query = "SELECT close, macd, rsi, target FROM indicators"
        df = pd.read_sql(query, engine)

        if df.empty:
            print("[ERREUR] Aucune donnée disponible dans la table 'indicators'.")
            return

        # Ajouter les nouvelles colonnes nécessaires pour les prédictions
        df['price_change'] = df['close'].diff()
        df['moving_average'] = df['close'].rolling(window=5).mean()
        df['rate_of_change'] = df['close'].pct_change(periods=5) * 100

        # Ajouter les bandes de Bollinger
        df['moving_avg_20'] = df['close'].rolling(window=20).mean()
        df['stddev_20'] = df['close'].rolling(window=20).std()
        df['upper_band'] = df['moving_avg_20'] + (2 * df['stddev_20'])
        df['lower_band'] = df['moving_avg_20'] - (2 * df['stddev_20'])

        # Supprimer les lignes avec des valeurs NaN
        df.dropna(inplace=True)

        if df.empty:
            print("[INFO] Pas assez de données après nettoyage pour effectuer des prédictions.")
            return

        # Prétraiter les données (normalisation)
        scaler = MinMaxScaler()
        df[['rsi', 'macd']] = scaler.fit_transform(df[['rsi', 'macd']])

        # Sélectionner les features pour la prédiction
        X_latest = df[['rsi', 'macd', 'price_change', 'moving_average', 'rate_of_change', 'upper_band', 'lower_band']]

        # Faire la prédiction avec le modèle
        prediction = rf_model.predict(X_latest)
        probabilities = rf_model.predict_proba(X_latest)

        # Interpréter la prédiction
        decision = "Maintenir"
        if prediction[-1] == 1:
            decision = "Acheter"
        elif prediction[-1] == 0:
            decision = "Vendre"

        # Afficher la décision avec le pourcentage de confiance
        confidence = max(probabilities[-1]) * 100
        print(f"[{datetime.now()}] Décision : {decision} (Confiance : {confidence:.2f}%)")

        # Sauvegarder la décision dans la base de données
        decision_data = {
            'decision': decision,
            'confidence': confidence,
            'timestamp': datetime.now()
        }
        decision_df = pd.DataFrame([decision_data])
        decision_df.to_sql('decision_table', engine, if_exists='append', index=False)
        print("[SUCCESS] Décision sauvegardée dans la base de données.")
    
    except Exception as e:
        print(f"[ERREUR] Une erreur s'est produite lors de la mise à jour ou de la prédiction : {e}")

# Boucle de mise à jour avec intervalle ajustable
interval_seconds = 5*60  # Modifier cet intervalle pour tester différentes périodes
print(f"[INFO] Démarrage de la boucle avec un intervalle de {interval_seconds} secondes.")

try:
    while True:
        update_and_predict()
        time.sleep(interval_seconds)  # Attendre avant de mettre à jour à nouveau
except KeyboardInterrupt:
    print("\n[INFO] Arrêt manuel du script.")
except Exception as e:
    print(f"[ERREUR] Une erreur inattendue s'est produite : {e}")
