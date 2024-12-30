from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression
import simulator
import matplotlib.pyplot as plt
import numpy as np
# Fonction pour préparer les données
def prepare_data(df):
    df['returns'] = df['close'].astype(float).pct_change()
    df['target'] = df['returns'].shift(-1)
    df = df.dropna()
    return df

# Exemple d'utilisation
if __name__ == "__main__":
    symbol = 'AAPL'  # Symbole de l'action
    stock_data = simulator.get_stock_data(symbol)
    prepared_data = prepare_data(stock_data)
    
    X = prepared_data[['returns']]
    y = prepared_data['target']
    
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
    
    model = LinearRegression()
    model.fit(X_train, y_train)
    
    # Prédiction
    predictions = model.predict(X_test)
 # Indices pour les jours
days = np.arange(1, len(predictions) + 1)

# Tracer les résultats
plt.figure(figsize=(10, 5))
plt.plot(days, predictions, marker='o', linestyle='-')
plt.title('Prédictions des Rendements Futurs')
plt.xlabel('Jour')
plt.ylabel('Rendement Prévu')
plt.grid()
plt.show()


actual_returns = [...]  # Remplace par tes valeurs réelles
predicted_returns = predictions

residuals = actual_returns - predicted_returns

plt.figure(figsize=(10, 5))
plt.scatter(predicted_returns, residuals)
plt.axhline(y=0, color='r', linestyle='--')
plt.title('Graphique des Résidus')
plt.xlabel('Prédictions')
plt.ylabel('Résidus')
plt.grid()
plt.show()
