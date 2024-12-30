export interface Position {
  id: number;             // Identifiant unique de la position
  symbol: string;         // Symbole de l'actif (ex: AAPL, GOOGL)
  quantity: number;       // Quantité de l'actif détenu
  entryPrice: number;     // Prix auquel la position a été ouverte
  stopLoss?: number;      // Prix de déclenchement du stop loss (optionnel)
  takeProfit?: number;    // Prix de déclenchement du take profit (optionnel)
  entryTime: Date;        // Date et heure d'entrée dans la position
  currentPrice: number;   // Prix actuel de l'actif
  positionType: 'buy' | 'sell'; // Type de position : Achat ou Vente
  profitLoss: number;      // Profit ou perte réalisée
  openedAt:Date;
  closedAt:Date;
  isOpen: boolean;
}
