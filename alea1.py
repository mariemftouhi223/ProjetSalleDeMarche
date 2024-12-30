import time
import numpy as np
import pandas as pd
import requests

# Clé API Alpha Vantage
api_key = 'TB2MS9VQS1XNROG8'
BASE_URL = 'https://www.alphavantage.co/query'

# Fonction pour récupérer les données d'Alpha Vantage
# Fonction pour récupérer l'historique des données intrajournalières
def fetch_intraday_history(symbol):
    url = f"{BASE_URL}?function=TIME_SERIES_INTRADAY&symbol={symbol}&interval=1min&apikey={api_key}&outputsize=full"
    try:
        response = requests.get(url)
        if response.status_code == 200:
            data = response.json()
            timeseries = data.get("Time Series (1min)", {})
            if not timeseries:
                print("[ERROR] Aucune donnée historique trouvée pour ce symbole.")
                return []

            # Convertir les données en DataFrame
            df = pd.DataFrame.from_dict(timeseries, orient='index')
            df = df.rename(columns={
                "1. open": "open",
                "2. high": "high",
                "3. low": "low",
                "4. close": "close",
                "5. volume": "volume"
            })
            df.index = pd.to_datetime(df.index)  # Convertir les index en datetime
            df = df.sort_index()  # Trier par date croissante
            print(f"[INFO] Historique des données récupéré pour {symbol} :\n{df.head()}")
            return df
        else:
            print(f"[ERROR] Échec de la récupération de l'historique : {response.status_code} - {response.text}")
            return []
    except Exception as e:
        print(f"[ERROR] Erreur lors de la récupération de l'historique : {e}")
        return []


# Fonction pour générer des prix aléatoires basés sur la moyenne des prix
def generate_random_prices(mean, std_dev, num_samples):
    prices = np.abs(np.random.normal(mean, std_dev, num_samples)).round(2)
    print(f"[INFO] Prix aléatoires générés : {prices}")
    return prices

# Fonction pour calculer les indicateurs basés sur la moyenne des prix
def calcul_indicateurs(prices, previous_close):
    average_price = np.mean(prices)
    print(f"[INFO] Moyenne des prix : {average_price}")
    df = pd.DataFrame({'close': prices})
    df['EMA_6'] = df['close'].ewm(span=6, adjust=False).mean()
    df['EMA_14'] = df['close'].ewm(span=14, adjust=False).mean()
    df['MACD'] = df['EMA_6'] - df['EMA_14']
    macd_value = df['MACD'].iloc[-1]
    delta = df['close'].diff(1)
    gain = np.where(delta > 0, delta, 0)
    loss = np.where(delta < 0, -delta, 0)
    avg_gain = pd.Series(gain).rolling(window=14).mean()
    avg_loss = pd.Series(loss).rolling(window=14).mean()
    rs = avg_gain / avg_loss
    rsi_value = 100 - (100 / (1 + rs)).iloc[-1]
    last_close = prices[-1]
    target = 1 if last_close > previous_close else 0
    result = {
        'close': round(float(average_price)),
        'macd': round(float(macd_value), 2),
        'rsi': round(float(rsi_value), 2),
        'target': int(target)
    }
    print("[DEBUG] Données prêtes à être envoyées :", result)
    return result

# Fonctions pour envoyer les données au backend (inchangées)
def envoyer_indicateurs(data, symbol):
    data['symbol'] = symbol
    url = f'http://localhost:8089/ProjetSalleDeMarche/portfolio/send-indicators/{symbol}'
    headers = {'Content-Type': 'application/json'}
    try:
        print(f"[INFO] Envoi des données à {url} avec payload : {data}")
        response = requests.post(url, json=data, headers=headers)
        if response.status_code == 200:
            print("[SUCCESS] Données des indicateurs envoyées avec succès !")
        else:
            print(f"[ERROR] Échec de l'envoi des indicateurs : {response.status_code} - {response.text}")
    except Exception as e:
        print(f"[ERROR] Erreur lors de l'envoi des indicateurs : {e}")

# Fonction pour récupérer le symbole sélectionné depuis le frontend
def get_selected_symbol():
    url = 'http://localhost:8089/ProjetSalleDeMarche/portfolio/get-selected-symbol'
    try:
        response = requests.get(url)
        if response.status_code == 200:
            symbol = response.json().get('symbol')
            print(f"[INFO] Symbole récupéré : {symbol}")
            return symbol
        else:
            print(f"[ERROR] Échec de la récupération du symbole : {response.status_code} - {response.text}")
            return None
    except Exception as e:
        print(f"[ERROR] Erreur lors de la récupération du symbole : {e}")
        return None

def envoyer_prix(prices, symbol):
    url = f'http://localhost:8089/ProjetSalleDeMarche/portfolio/send-prices/{symbol}'
    headers = {'Content-Type': 'application/json'}
    try:
        print(f"[INFO] Envoi des prix à {url} avec payload : {prices.tolist()}")
        response = requests.post(url, json=prices.tolist(), headers=headers)
        if response.status_code == 200:
            print("[SUCCESS] Prix envoyés avec succès !")
        else:
            print(f"[ERROR] Échec de l'envoi des prix : {response.status_code} - {response.text}")
    except Exception as e:
        print(f"[ERROR] Erreur lors de l'envoi des prix : {e}")
# Boucle principale modifiée
try:
    previous_close = 0
    last_api_call = 0
    average_price = 0
    historical_data = None  # Stockage des données historiques

    while True:
        symbol = get_selected_symbol()
        if not symbol:
            print("[ERROR] Impossible de récupérer le symbole, arrêt de l'envoi.")
            break

        # Récupération de l'historique au démarrage ou toutes les 24 heures
        if historical_data is None or time.time() - last_api_call >= 24 * 60 * 60:
            historical_data = fetch_intraday_history(symbol)
            last_api_call = time.time()

        current_time = time.time()
        if current_time - last_api_call >= 15 * 60:  # Appel API toutes les 15 minutes
            prices = fetch_intraday_history(symbol)  # Remplacez ici
            if not prices:
                print("[WARNING] Données API indisponibles. Génération de données aléatoires.")
                random_prices = generate_random_prices(average_price, 15, 30)
            else:
                average_price = np.mean(prices['close'].astype(float))  # Assurez-vous d'extraire la colonne 'close'
                random_prices = prices['close'].astype(float).values  # Utilisez la colonne 'close'
                last_api_call = current_time
        else:
            random_prices = generate_random_prices(average_price, 15, 30)

        indicators_data = calcul_indicateurs(random_prices, previous_close)
        envoyer_prix(random_prices, symbol)
        envoyer_indicateurs(indicators_data, symbol)
        previous_close = random_prices[-1]

        # Optionnel : Envoyer les données historiques au backend
        if historical_data is not None and isinstance(historical_data, pd.DataFrame) and 'close' in historical_data.columns:
            envoyer_prix(historical_data['close'].values, symbol)
        else:
            print("[WARNING] Données historiques invalides ou vides.")

        time.sleep(10)

except KeyboardInterrupt:
    print("[INFO] Arrêt du programme.")
