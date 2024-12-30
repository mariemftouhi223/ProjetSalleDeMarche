import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Position } from './Position';
import { Indicator } from './Indicator';

interface MarketData {
  id: number;
  symbol: string;
  price: number;
  timestamp: string;
}

@Injectable({
  providedIn: 'root'
})
export class MarketDataService {
  private apiUrl = 'http://localhost:8089/ProjetSalleDeMarche/portfolio/get-prices';

  constructor(private http: HttpClient) {}

  getMarketData(): Observable<MarketData[]> {
    return this.http.get<MarketData[]>('http://localhost:8089/ProjetSalleDeMarche/portfolio/market-data');
  }

  getPricesForSymbol(symbol: string): Observable<MarketData[]> {
    const url = `http://localhost:8089/ProjetSalleDeMarche/portfolio/get-prices/${symbol}`;
    return this.http.get<MarketData[]>(url);
  }

  getPositionsBySymbol(symbol: string): Observable<Position[]> {
    const url = `http://localhost:8089/ProjetSalleDeMarche/portfolio/${symbol}`;
    return this.http.get<Position[]>(url);
  }

  updateCurrentPrice(symbol: string, currentPrice: number): Observable<any> {
    const url = `http://localhost:8089/ProjetSalleDeMarche/portfolio/update-current-price`;
    const body = `symbol=${symbol}&currentPrice=${currentPrice}`;
    return this.http.put(url, body, { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } });
  }

  updateAllPositionsForSymbol(symbol: string, currentPrice: number): void {
    this.updateCurrentPrice(symbol, currentPrice).subscribe(
      (response) => {
        console.log('Mise à jour réussie:', response);
        // Actualise la liste des positions ici si nécessaire
      },
      (error) => {
        console.error('Erreur lors de la mise à jour:', error);
      }
    );
  }

  closePosition(id: number, currentPrice: number): Observable<any> {
    const url = `http://localhost:8089/ProjetSalleDeMarche/portfolio/positions/close/${id}`;
    const body = `currentPrice=${currentPrice}`;
    return this.http.post(url, body, {
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      responseType: 'text'
    });
  }

  getIndicators(symbol: string): Observable<Indicator[]> {
    const url = `http://localhost:8089/ProjetSalleDeMarche/portfolio/get-indicators/${symbol}`;
    return this.http.get<Indicator[]>(url);
  }

  getBalance(): Observable<any> {
    return this.http.get('http://localhost:8089/ProjetSalleDeMarche/portfolio/get-balence');
  }

  sendPrices(symbol: string, data: any): Observable<any> {
    const url = `http://localhost:8089/ProjetSalleDeMarche/portfolio/send-prices/${symbol}`;
    return this.http.post(url, data, { headers: { 'Content-Type': 'application/json' } });
  }

  setSelectedSymbol(symbol: string): Observable<any> {
    const url = `http://localhost:8089/ProjetSalleDeMarche/portfolio/set-selected-symbol`;
    return this.http.post(url, { symbol }, { headers: { 'Content-Type': 'application/json' } });
  }

  getSelectedSymbol(): Observable<string> {
    const url = `http://localhost:8089/ProjetSalleDeMarche/portfolio/get-selected-symbol`;
    return this.http.get<string>(url);
  }

  getAllSymbols(): Observable<string[]> {
    const url = `http://localhost:8089/ProjetSalleDeMarche/portfolio/get-all-symbols`;
    return this.http.get<string[]>(url);
  }

  enterPosition(position: any): Observable<any> {
    const url = `http://localhost:8089/ProjetSalleDeMarche/portfolio/enter-position`;
    return this.http.post(url, position, { headers: { 'Content-Type': 'application/json' } });
  }
}
