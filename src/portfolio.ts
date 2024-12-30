export interface Position {
  id: number;             // Identifiant unique de la position
     // Prix de déclenchement du take profit (optionnel)
  lastUpdated: Date;        // Date et heure d'entrée dans la position
  name: string;   // Prix actuel de l'actif
  totalValue: 'buy' | 'sell'; // Type de position : Achat ou Vente
  balence: number;  
      // Profit ou perte réalisée
}
