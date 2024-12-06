from transformers import pipeline
from flask import Flask, request, jsonify

# Charger le pipeline d'analyse de sentiment
sentiment_analysis = pipeline("sentiment-analysis")

# Créer une instance Flask
app = Flask(__name__)

@app.route("/analyze-sentiment", methods=["POST"])
def analyze_sentiment():
    # Récupérer le texte du corps de la requête
    data = request.get_json()
    text = data.get("text", "")
    
    # Analyser le sentiment
    result = sentiment_analysis(text)
    
    # Retourner le résultat
    return jsonify(result)

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
