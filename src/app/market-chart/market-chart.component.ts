import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { interval, Observable, Subscription } from 'rxjs';
import { curveCatmullRom } from 'd3-shape';
import { FormsModule } from '@angular/forms';
import * as d3Shape from 'd3-shape';

import { MarketDataService } from '../../market-data.service'; // Ajustez le chemin en fonction de votre structure de projet
import { ChangeDetectorRef } from '@angular/core';
import { Color, ScaleType } from '@swimlane/ngx-charts';
import { Indicator } from '../../Indicator';
import { Router, RouterOutlet } from '@angular/router';
import { RouterModule } from '@angular/router';
import {  NO_ERRORS_SCHEMA } from '@angular/core';
import { ChangeDetectionStrategy } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { AppRoutingModule } from '../app.routes';
import { Position } from '../../Position';
import { Order, OrderBookService } from '../../orderBookService';
import { NavbarComponent } from '../navbar/navbar.component';
import { PredictionService } from '../prediction.service';
interface MarketData {
  id: number;
  symbol: string;
  price: number;
  timestamp: string;
}

interface SeriesData {
  name: string; // Date ou autre identifiant de la série
  value: number; // Valeur associée à la date
  color?: string; // Couleur de la série (optionnel)
}

@Component({
  standalone: true,
  selector: 'app-market-chart',
  templateUrl: './market-chart.component.html',
  styleUrls: ['./market-chart.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [NgxChartsModule, FormsModule, CommonModule,NavbarComponent]
})
export class MarketChartComponent implements OnInit, OnDestroy {
  //indicators: Indicator[] = [];
  indicators: string[] = ['RSI', 'MACD'];
  selectedIndicator: string = '';
  rsiData: { name: string; value: number[] }[] = [];
  macdData:{ name: string; value: number[] }[] = [];
  view: [number, number] = [1500, 600]; // Taille du graphique
  symbols = ['AAPL', 'GOOGL', 'BTC', 'USD']; // Liste des symboles disponibles
  selectedSymbol: string = this.symbols[0]; // Symbole par défaut
  positions: any[] = [];
  chartData: { name: string; series: SeriesData[] }[] = [];
  minYValue: number = Number.MAX_VALUE; // Valeur minimale de l'axe Y
  maxYValue: number = Number.MIN_VALUE; // Valeur maximale de l'axe Y
  curve: any; // Utiliser une courbe lissée (spline)
  private subscription: Subscription = new Subscription();
  
  private recentPrices: number[] = []; // Stocke les 10 dernières valeurs
  previousPrices: { [symbol: string]: number | null } = {}; // Stocker le prix précédent par symbole
  currentPrice: number = 0;
  entryLine: number | null = null;
  // Options pour le graphique
  showXAxis: boolean = true;
  showYAxis: boolean = true;
  gradient: boolean = false;
  showLegend: boolean = true; // Afficher la légende
  showLabels: boolean = true;
  autoScale: boolean = true;
  public chartOptions: any;
  symbol: string = ''; // Le symbole de l'actif
  quantity: number = 0;
  entryPrice: number = 0;
  prediction: any = null;
  error: string | null = null;
  isLoading: boolean = true;
loadingProgress: number = 0;
predictionData: { confidence: number; decision: string; timestamp: string } | null = null;
predictionChartData: any[] = [];
  stopLoss: number = 0;
  takeProfit: number = 0;
  positionType: string = 'buy';
  buyOrders: any[] = []; // Liste des ordres d'achat
  sellOrders: any[] = []; // Type de position (achat ou vente)
  gainsLosses: any[] = [];
  balance: number=0;
  colorScheme: Color = {
    name: 'cool', // Nom du schéma
    selectable: true, // Permet à l'utilisateur de sélectionner un schéma
    group: ScaleType.Ordinal, // Peut être 'Ordinal', 'Linear', 'Logarithmic', etc.
    domain: ['#00ff00', '#ff0000'] // Définir votre tableau de couleurs ici (vert et rouge)
  };
  currentSignal: { type: string; message: string } | null = null;
  tpPrice: number = 0;
  slPrice: number = 0;
  private intervalId: any;
  positionAction: string = ''; // Variable qui définit si l'action est 'buy' ou 'sell'
  isFormVisible: boolean = false;
  bids: Order[] = []; // Initialisez comme tableau vide
  asks: Order[] = [];
  private pollingInterval: any;
  selectedInterval: string='';
  constructor(private marketDataService: MarketDataService,
              private predictionService:PredictionService,
              private orderBookService: OrderBookService,
              private http: HttpClient,
              private cdr: ChangeDetectorRef,
              private router: Router) {
                console.log(this.router.config);
    this.curve = curveCatmullRom;
    // Initialiser les prix précédents pour chaque symbole
    this.symbols.forEach(symbol => this.previousPrices[symbol] = null);
  }
  loadBalance() {
    this.marketDataService.getBalance().subscribe(
      (balance) => {
        this.balance = balance; // Assurez-vous que le service retourne le bon solde
      },
      (error) => {
        console.error('Erreur lors du chargement du solde :', error);
      }
    );
  }
  setTimeInterval(interval: string) {
    this.selectedInterval = interval;
    this.fetchDataForInterval(interval);
}

fetchDataForInterval(interval: string) {
    this.http.get(`http://localhost:8089/ProjetSalleDeMarche/portfolio/chart-data?interval=${interval}`).subscribe((data: any) => {
        this.chartData = this.formatChartData1(data);
    });
}
formatChartData1(data: any): any[] {
  // Vérifier que les données existent et sont dans le bon format
  if (!data || !Array.isArray(data)) {
      console.error("Invalid data format received:", data);
      return [];
  }

  // Transformer les données pour qu'elles soient compatibles avec le graphique
  return data.map((item: any) => {
      return {
          name: new Date(item.timestamp).toLocaleString(), // Convertit le timestamp en une date lisible
          value: item.price, // Assigne la valeur du prix
      };
  });
}


  loadEntryLine() {
    this.entryLine = this.calculateEntryLine(); // Calculer ou récupérer la ligne d'entrée
  }
  onIndicatorChange(indicator: string): void {
    this.selectedIndicator = indicator;
  }
  initializeChartOptions(marketData: MarketData[]): void {
    const categories = marketData.map(data => new Date(data.timestamp).toISOString());
    const seriesData = marketData.map(data => data.price);
  
    this.chartOptions = {
      view: [1200, 600],
      colorScheme: {
        domain: ['#60a5fa', '#fbbf24', '#34d399', '#f87171', '#818cf8', '#a78bfa']
      },
      animations: true,
      gradient: true, // Active le dégradé
      showXAxis: true,
      showYAxis: true,
      showLegend: true,
      autoScale: true,
      curve: d3Shape.curveMonotoneX, // Courbe plus fluide
      xAxisTickFormatting: (value: string) => {
        return new Date(value).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
      },
      yAxisTickFormatting: (value: number) => {
        return `${value.toLocaleString('en-US', { maximumFractionDigits: 2 })} $`;
      },
      showDataLabel: true, // Affiche les points de données
    };
}


  // Exemple de méthode pour calculer la ligne d'entrée
  calculateEntryLine(): number | null {
    // Logique pour déterminer la ligne d'entrée en fonction des positions
    const lastPosition = this.positions[this.positions.length - 1];
    return lastPosition ? lastPosition.entryPrice : null; // Assurez-vous que entryPrice est défini
  }
  ngOnInit() {
    this.fetchData(); // Charger les données initiales
    this.subscription = interval(5000).subscribe(() => this.fetchData());
    setInterval(() => {
      this.loadIndicators();
    }, 2000);
    this.loadOrderBook();
    this.startPolling();
    this.simulatePredictionLoading();
     // Récupérer les données toutes les 10 secondes
     this.loadPositions(this.selectedSymbol);
    this.startUpdatingPosition(); // Démarrer la mise à jour des positions
    this.updatePositionPrices();
      // Mettre à jour les positions toutes les 5 secondes
       // Charger les positions initiales
    this.sendSelectedSymbol(); // Envoyer le symbole sélectionné
    this.loadEntryLine(); // Charger la ligne d'entrée
    this.loadBalance(); // Charger le solde
}
  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
      clearInterval(this.intervalId);
    }
  }
  simulatePredictionLoading(): void {
    this.prediction = null; // Réinitialise la prédiction
    this.error = null; // Réinitialise les erreurs
    this.loadingProgress = 0; // Réinitialise la barre de progression

    const interval = setInterval(() => {
      if (this.loadingProgress < 100) {
        this.loadingProgress += 10;
      } else {
        clearInterval(interval);
        this.getPrediction(); // Appelle l'API une fois le chargement terminé
      }
    }, 500); // Incrémente la barre toutes les 500 ms
  }
  getPrediction(): void {
    this.predictionService.getPrediction().subscribe({
      next: (data) => {
        this.prediction = data;
        this.error = null;
      },
      error: (err) => {
        console.error('Erreur lors de la récupération de la prédiction :', err);
        this.error = 'Impossible de récupérer la prédiction.';
      }
    });
  }
  openPositionForm(action: string): void {
    if (this.isFormVisible && this.positionAction === action) {
      this.isFormVisible = false; // Masquer le formulaire
    } else {
      // Sinon, afficher le formulaire et définir l'action (achat ou vente)
      this.positionAction = action;
      this.isFormVisible = true; // Afficher le formulaire
    }  
    // Optionnel: pré-remplir des valeurs en fonction de l'action (par exemple, prix d'entrée, symbole...)
    if (action === 'buy') {
      this.positionType = 'buy'; // Par défaut, afficher "buy"
      // Vous pouvez pré-remplir d'autres champs ici si nécessaire
    } else if (action === 'sell') {
      this.positionType = 'sell'; // Par défaut, afficher "sell"
      // Vous pouvez pré-remplir d'autres champs ici si nécessaire
    }
  }
  loadIndicators(): void {
    this.marketDataService.getIndicators(this.selectedSymbol).subscribe(
      (data: Indicator[]) => {
        console.log(`Indicateurs reçus :`, data);
  
        // Effacer les anciennes données
        this.rsiData = [];
        this.macdData = [];
  
        data.forEach((indicator) => {
          // Assurez-vous que indicator.rsi et indicator.macd sont des tableaux de nombres
          this.rsiData.push({
            name: new Date(indicator.timestamp).toLocaleTimeString(),  // Temps basé sur le timestamp
            value: [indicator.rsi],  // En supposant que rsi est une seule valeur
          });
          this.macdData.push({
            name: new Date(indicator.timestamp).toLocaleTimeString(),  // Temps basé sur le timestamp
            value: [indicator.macd],  // En supposant que macd est une seule valeur
          });
        });
  
        // Vérification des données après ajout
        console.log('RSI Data:', this.rsiData);
        console.log('MACD Data:', this.macdData);
  
        // Limiter à 50 derniers points
        if (this.rsiData.length > 50) this.rsiData.splice(0, this.rsiData.length - 50);
        if (this.macdData.length > 50) this.macdData.splice(0, this.macdData.length - 50);
      },
      (error) => {
        console.error('Erreur lors de la récupération des indicateurs', error);
      }
    );
  }
  
  
  

  formatChartData(data: Indicator[], key: keyof Indicator, label: string): any[] {
    return data.map((item) => ({
      name: item.timestamp, // Timestamp comme axe X
      value: item[key], // RSI ou MACD comme valeur
    }));
  }
  fetchData() {
  const pricesUrl = `http://localhost:8089/ProjetSalleDeMarche/portfolio/get-prices/${this.selectedSymbol}`;
  const indicatorsUrl = `http://localhost:8089/ProjetSalleDeMarche/portfolio/get-indicators/${this.selectedSymbol}`;

  // Récupérer les données de marché
  this.http.get<MarketData[]>(pricesUrl).subscribe(
    (marketData: MarketData[]) => {
      console.log('Données de marché reçues :', marketData);

      if (marketData && marketData.length > 0) {
        // Récupérer les indicateurs une fois les données de marché disponibles
        this.initializeChartOptions(marketData);
        this.http.get<Indicator[]>(indicatorsUrl).subscribe(
          (indicators: Indicator[]) => {
            console.log('Indicateurs reçus :', indicators);

            // Mise à jour du graphique avec les données de marché et les indicateurs
            this.updateChartData(marketData, indicators);
           // this.generateTradingSignal(indicators);
          },
          (error: any) => {
            console.error('Erreur lors de la récupération des indicateurs :', error);
          }
        );
      } else {
        console.warn('Aucune donnée de marché récupérée.');
      }
    },
    (error: any) => {
      console.error('Erreur lors de la récupération des données de marché :', error);
    }
  );
}
generateTradingSignal(indicators: Indicator[]) {
  if (!indicators || indicators.length === 0) {
    console.error('Aucun indicateur disponible pour générer un signal.');
    this.currentSignal = null;
    return;
  }

  const latestIndicator = indicators[indicators.length - 1];
  const target = latestIndicator.target;

  let type = ''; // 'buy' ou 'sell'
  let message = ''; // Message du signal

  // Vérification des signaux RSI
  if (latestIndicator.rsi < 30) {
    type = 'buy';
    message = `Acheter (RSI < 30) - ${target === 1 ? 'Cible atteinte' : 'Cible non atteinte'}`;
    this.enterPosition();
  } else if (latestIndicator.rsi > 70) {
    type = 'sell';
    message = `Vendre (RSI > 70) - ${target === 1 ? 'Cible atteinte' : 'Cible non atteinte'}`;
    this.enterPosition();
  }



  // Met à jour le signal courant avec le type et le message
  this.currentSignal = { type, message };
}


// Méthode pour afficher le signal avec la couleur appropriée
displaySignal(message: string, color: string) {
  const signalElement = document.getElementById('signal'); // Assurez-vous qu'un élément avec cet ID existe dans le template
  if (signalElement) {
    signalElement.textContent = message;
    signalElement.style.color = color;
  }
}


  
loadPositions(symbol: string): void {
  this.http.get<Position[]>('http://localhost:8089/ProjetSalleDeMarche/portfolio/positions')
    .subscribe({
      next: (positions: Position[]) => {
        // Filtrer les positions par le symbole
        this.positions = positions.filter(position => position.symbol === symbol);
        this.updatePositionPrices(); // Calcul initial du profit/perte
      },
      error: (error) => {
        console.error('Erreur lors de la récupération des positions', error);
      }
    });
}

  updateChartData(newData: MarketData[], indicators: Indicator[]) {
    // Si aucune donnée n'est fournie, ne rien faire
    if (!newData || newData.length === 0) return;
  
    newData.forEach(item => {
      if (item.timestamp && item.price && item.symbol) {
        const formattedData = this.formatData(item);
        const timestamp = formattedData.timestamp;
        const price = formattedData.price;
  
        this.currentPrice = price;
  
        // Vérifie si la série pour l'actif existe
        let existingSeries = this.chartData.find(series => series.name === item.symbol);
        if (!existingSeries) {
          existingSeries = { name: item.symbol, series: [] };
          this.chartData.push(existingSeries);
        }
  
        // Mise à jour ou ajout d'un point pour le prix
        const existingPointIndex = existingSeries.series.findIndex(point => point.name === timestamp);
        if (existingPointIndex >= 0) {
          // Mise à jour du point existant
          existingSeries.series[existingPointIndex].value = price;
          existingSeries.series[existingPointIndex].color = this.getPointColor(price, item.symbol); // Mettre à jour la couleur
        } else {
          // Ajout d'un nouveau point
          const pointColor = this.getPointColor(price, item.symbol);
          existingSeries.series.push({ name: timestamp, value: price, color: pointColor });
        }
  
        // Met à jour le prix précédent pour ce symbole
        this.previousPrices[item.symbol] = price;
        this.updateYAxisLimits(price);
      }
    });
    this.addEntryLineToChart();
    // Force la mise à jour du graphique
    this.chartData = [...this.chartData]; 
    this.cdr.detectChanges();
    this.loadBalance();
  
    // Ajoute la ligne d'entrée au graphique
    
  }
  
 // Nouvelle méthode pour ajouter une ligne d'entrée continue sur toute la période
 addEntryLineToChart() {
  if (this.entryLine !== null && this.entryLine !== undefined) {
      // Vérifie si la série "Entry Line" existe déjà, sinon la crée
      let entrySeries = this.chartData.find(data => data.name === 'Entry Line');
      if (!entrySeries) {
          entrySeries = { name: 'Entry Line', series: [] };
          this.chartData.push(entrySeries);
      }

      // Crée une ligne continue avec les mêmes timestamps que les autres séries
      entrySeries.series = this.chartData
          .flatMap(series => series.series) // Récupérer tous les points de timestamp
          .map(item => ({
              name: item.name, // Timestamp
              value: this.entryLine ?? 0, // Valeur constante, jamais null
              color: this.entryLine! > (item.value ?? 0) ? '#00ff00' : '#ff0000' // Vert si au-dessus, rouge sinon
          }));
  }
}
addEntryLineToChart1() {
  if (this.entryLine !== null && this.entryLine !== undefined) {
  this.addLineToChart1('Entry Line', this.entryPrice);

  // Ajoute TP uniquement s'il a une valeur valide
  if (this.takeProfit !== null && this.takeProfit !== undefined && this.takeProfit > 0) {
    this.addLineToChart1('Take Profit', this.takeProfit);
    console.log(`Ligne "Take Profit" ajoutée avec la valeur: ${this.takeProfit}`);
  } else {
    console.warn('Take Profit n\'est pas valide.'); // Avertissement
  }
  // Ajoute SL uniquement s'il a une valeur valide
  if (this.stopLoss !== null && this.stopLoss !== undefined && this.stopLoss > 0) {
    this.addLineToChart1('Stop Loss', this.stopLoss);
    console.log(`Ligne "Stop Loss" ajoutée avec la valeur: ${this.stopLoss}`);
  } else {
    console.warn('Stop Loss n\'est pas valide.'); // Avertissement
  }

  // Déclenche la détection de changements pour mettre à jour l'affichage
  this.cdr.detectChanges();
}
}
// Fonction générique pour ajouter une ligne avec un nom et une valeur donnés
addLineToChart1(lineName: string, price: number) {
  if (price !== null && price !== undefined) {
    let series = this.chartData.find(data => data.name === lineName);
    if (!series) {
      series = { name: lineName, series: [] };
      this.chartData.push(series);
    }
    // Crée une série avec la valeur constante sur tous les timestamps
    series.series = this.chartData
      .flatMap(data => data.series)
      .map(item => ({
        name: item.name,
        value: price,
        color: this.getLineColor1(lineName, price, item.value)
      }));

    console.log(`Ligne "${lineName}" ajoutée avec la valeur: ${price}`);
  } else {
    console.warn(`Valeur de ${lineName} invalide : ${price}`); // Avertir si la valeur est invalide
  }
}
// Fonction pour déterminer la couleur des lignes
getLineColor1(lineName: string, linePrice: number, itemValue: number | undefined): string {
  switch (lineName) {
    case 'Entry Line':
      return '#ff0000'; // Rouge pour l'Entry Line
    case 'Take Profit':
      return '#0000ff'; // Bleu pour TP
    case 'Stop Loss':
      return '#000000'; // Noir pour SL
    default:
      return '#000000'; // Noir par défaut
  }
}
  getPointColor(price: number, symbol: string): string {
    // Comparez le prix actuel au prix précédent pour déterminer la couleur
    const previousPrice = this.previousPrices[symbol];
    if (previousPrice !== null) {
      if (price > previousPrice) {
        return '#00ff00'; // Vert pour une augmentation
      } else if (price < previousPrice) {
        return '#ff0000'; // Rouge pour une diminution
      }
    }
    return '#000000'; // Noir par défaut si aucune tendance n'est détectée
  }
  
  formatData(item: MarketData) {
    const timestamp = new Date(item.timestamp).toISOString(); // Format uniforme pour l'axe X
    const price = item.price;
    return { timestamp, price };
  }

  updateYAxisLimits(newPrice: number) {
    this.minYValue = Math.min(this.minYValue, newPrice);
    this.maxYValue = Math.max(this.maxYValue, newPrice);

    // Ajustement des limites de l'axe Y avec une marge
    this.minYValue -= 10; // Exemple d'ajustement
    this.maxYValue += 10; // Exemple d'ajustement
  }
  
  xAxisTickFormatting = (value: any) => {
    return new Date(value).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit' });
  };

  yAxisTickFormatting = (value: any) => {
    return value.toFixed(2); // Formate les valeurs à deux décimales
  };

  onSymbolChange(symbol: string) {
    this.chartData = [];
    this.selectedSymbol = symbol; // Stocker le symbole sélectionné
    this.fetchData(); // Récupérer les prix lorsque l'utilisateur change de symbole
    console.log(`Symbole sélectionné: ${this.selectedSymbol}`); // Log du symbole sélectionné
    
    this.sendSelectedSymbol(); // Envoyer au backend
    
    // Rafraîchir les positions pour le nouveau symbole sélectionné
    this.loadPositions(this.selectedSymbol);  // Filtrer et récupérer les positions du symbole sélectionné
    
    this.updatePositionPrices(); // Mettre à jour le profit/perte ou d'autres informations sur les positions
  }
  
  sendSelectedSymbol() {
    this.http.post<string>(
      'http://localhost:8089/ProjetSalleDeMarche/portfolio/set-selected-symbol',
      { symbol: this.selectedSymbol },
      { responseType: 'text' as 'json', observe: 'body' }
    ).subscribe({
      next: (response) => {
        console.log('Réponse du backend : ', response);  // Affiche le message de succès
        if (response === "Symbole sélectionné mis à jour !") {
          // Logique après la mise à jour du symbole
          console.log('Le symbole a été mis à jour.');
        }
      },
      error: (error) => {
        console.error('Erreur lors de l\'envoi du symbole sélectionné', error);
      }
    });
  }
      
  enterPosition() {
    const currentPrice = this.getCurrentPrice(); // Méthode pour obtenir le prix actuel, assurez-vous qu'elle est définie
    const payload = {
        symbol: this.selectedSymbol,
        quantity: this.quantity,
        entryPrice: this.entryPrice || currentPrice,  // Utiliser entryPrice ou currentPrice
        stopLoss: this.stopLoss,
        takeProfit: this.takeProfit,
        positionType: this.positionType,
        currentPrice: currentPrice,
        openedAt: new Date(), // Assurez-vous d'inclure openedAt
        closedAt: null, // ou une valeur par défaut si la position est encore ouverte
    };

    // Mettre à jour entryLine seulement après avoir obtenu le prix d'entrée
    this.entryLine = payload.entryPrice;
    this.isFormVisible = false;
    // Envoi de la requête POST avec les paramètres dans l'URL
    this.http.post(`http://localhost:8089/ProjetSalleDeMarche/portfolio/enter-position?portfolioId=3&userId=1`, payload)
        .subscribe(
            (response) => {
                console.log('Position ouverte avec succès', response);
                this.updateGainsLosses();
                this.addEntryLineToChart1();
            },
            (error) => {
                console.error('Erreur lors de l\'ouverture de la position', error);
                this.entryLine = null; // ou toute autre valeur par défaut
            }
        );
}
getYPosition(entryPrice: number): number {
  // Assurez-vous que chartData est défini et n'est pas vide
  if (!this.chartData || this.chartData.length === 0) {
      return 0; // Retourne une valeur par défaut si chartData est vide
  }

  // Récupère tous les prix des séries de données
  const prices = this.chartData.flatMap(data => data.series.map(s => s.value));

  // Calcule les prix minimum et maximum
  const minPrice = Math.min(...prices);
  const maxPrice = Math.max(...prices);

  // Vérifiez si minPrice et maxPrice sont différents
  if (minPrice === maxPrice) {
      return 0; // Évite une division par zéro si tous les prix sont identiques
  }

  // Calcule la position Y en fonction du prix d'entrée
  const chartHeight = 300; // Hauteur de votre graphique, à ajuster
  const yPosition = chartHeight - ((entryPrice - minPrice) / (maxPrice - minPrice)) * chartHeight;

  return yPosition;
}
goToOrderBook() {
  this.router.navigate(['/order-book']);
}
  getCurrentPrice(): number | null {
    const latestData = this.chartData.find(data => data.name === this.selectedSymbol);
    if (latestData && latestData.series.length > 0) {
      return latestData.series[latestData.series.length - 1].value; // Obtenir le dernier prix
    }
    return null; // Retourner null si aucune donnée n'est trouvée
  }
  startUpdatingPosition() {
    // Mettre à jour les prix toutes les 50 ms
    this.subscription.add(
      interval(50).subscribe(() => {
        this.updatePositionPrices(); // Appel de la méthode pour mettre à jour les prix et calculer le profit/perte
      })
    );
  }
  updatePositionPrices() {
    const currentPrice = this.getCurrentPrice();

    if (currentPrice !== null) {
      this.positions = this.positions.map(position => {
        const profitOrLoss = (currentPrice - position.entryPrice) * position.quantity;
        return {
          ...position,
          currentValue: currentPrice,
          profitOrLoss: profitOrLoss
        };
      });

      // Forcer la mise à jour de la vue après modification des positions
      this.cdr.detectChanges(); // Si vous êtes dans un environnement Angular, utilisez ceci pour forcer la mise à jour
    } else {
      console.error('Impossible de récupérer le prix actuel.');
    }
  }
  updateGainsLosses() {
    this.gainsLosses = this.positions.map(position => ({
      name: position.symbol,
      value: position.profitLoss,
    }));
  }
  closePosition(id: number, currentPrice: number) {
    // Récupérer la position en cours à partir de l'ID
    const position = this.positions.find(position => position.id === id);
  
    if (position) {
      // Calculer le profit ou la perte
      const profitOrLoss = (currentPrice - position.entryPrice) * position.quantity; // Si vous avez une quantité associée à la position
  
      // Formatage du profit ou de la perte en devise (ici EUR, changez selon vos besoins)
      const formattedProfitOrLoss = new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD'
      }).format(profitOrLoss);
      
  
      // Afficher un message basé sur le profit ou la perte
      let message = '';
      if (profitOrLoss >= 0) {
        message = `Vous avez gagné ${formattedProfitOrLoss}`;
      } else {
        message = `Vous avez perdu ${formattedProfitOrLoss}`;
      }
  
      // Afficher l'alerte avec le message
      alert(`La position a été fermée. ${message}`);
  
      // Mettre à jour la balance du portefeuille
      this.loadBalance();
      this.loadPositions(this.selectedSymbol);
  
      // Marquer la position comme fermée ou la supprimer du tableau frontend
      const index = this.positions.findIndex(position => position.id === id);
      if (index !== -1) {
        this.positions.splice(index, 1); // Supprimer la position du tableau frontend
        console.log(`Position avec ID ${id} supprimée du tableau.`);
      } else {
        console.warn(`Position avec ID ${id} non trouvée dans le tableau.`);
      }
    } else {
      console.warn(`Position avec ID ${id} non trouvée.`);
    }
  }
  
  

private loadOrderBook(): void {
  console.log('Envoi de la requête pour récupérer le carnet d\'ordres pour le symbole:', this.selectedSymbol);
  const apiUrl = `http://localhost:8089/ProjetSalleDeMarche/portfolio/orders/${this.selectedSymbol}`;
  console.log('URL utilisée pour l\'API:', apiUrl);

  this.orderBookService.getOrderBook(this.selectedSymbol).subscribe({
    next: (data) => {
      console.log('Réponse des ordres reçue:', data);
      // Vérifiez que les données existent avant de les assigner
      this.buyOrders = data.bids || [];
      this.sellOrders = data.asks || [];
    },
    error: (err) => {
      console.error('Erreur lors de la récupération du carnet d\'ordres:', err.message);
      console.error('Erreur complète:', err);
    }
  });
}

  /**
   * Démarre le polling toutes les 3 secondes
   */
  startPolling(): void {
    this.pollingInterval = setInterval(() => {
      this.loadOrderBook();
    }, 3000); // Tous les 3 secondes
  }





}
