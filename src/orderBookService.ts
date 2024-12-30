import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Order {
  id: number;
  symbol: string;
  price: number;
  quantity: number;
  type: string; // "BID" ou "ASK"
  timestamp: string;
}

export interface OrderBook {
  bids: Order[]; // Liste des ordres d'achat
  asks: Order[]; // Liste des ordres de vente
}

@Injectable({
  providedIn: 'root',
})
export class OrderBookService {
  private baseUrl = 'http://localhost:8089/ProjetSalleDeMarche/portfolio/orders';

  constructor(private http: HttpClient) {}

  /**
   * Récupère le carnet d'ordres depuis le backend pour un symbole donné.
   * @param symbol Le symbole pour lequel récupérer le carnet d'ordres.
   * @returns Un Observable contenant les listes de BID et ASK sous forme d'OrderBook.
   */
  getOrderBook(symbol: string): Observable<OrderBook> {
    return this.http.get<OrderBook>(`${this.baseUrl}/${symbol}`);
  }
}
