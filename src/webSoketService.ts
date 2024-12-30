import { Injectable } from '@angular/core';
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private stompClient: any;
  private orderBookSubject = new BehaviorSubject<any[]>([]);

  // Observable que vous pouvez souscrire dans le composant
  public orderBook$ = this.orderBookSubject.asObservable();

  constructor() {
    this.initializeWebSocketConnection();
  }

  private initializeWebSocketConnection() {
    const socket = new SockJS('http://localhost:8083/ws'); // Assurez-vous que le chemin est correct
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, (frame: any) => {
      console.log('Connected: ' + frame);

      // S'abonner au topic /topic/orderBook
      this.stompClient.subscribe('/topic/orderBook', (message: any) => {
        if (message.body) {
          const orders = JSON.parse(message.body);
          this.orderBookSubject.next(orders); // Mettre à jour l’observable
        }
      });
    }, (error: any) => {
      console.error('STOMP error', error);
      // Vous pouvez éventuellement gérer les reconnections ici
    });

    // Événement de déconnexion
    socket.onclose = () => {
      console.log('WebSocket connection closed');
      // Vous pouvez ajouter une logique pour essayer de reconnecter ici
    };
  }

  // Optionnel : Méthode pour se déconnecter proprement
  public disconnect() {
    if (this.stompClient) {
      this.stompClient.disconnect(() => {
        console.log('Disconnected');
      });
    }
  }
}
