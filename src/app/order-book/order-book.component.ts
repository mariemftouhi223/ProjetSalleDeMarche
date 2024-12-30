import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // Nécessaire pour `*ngFor`
import { WebSocketService } from '../../webSoketService';

@Component({
  selector: 'app-order-book',
  templateUrl: './order-book.component.html',
  styleUrls: ['./order-book.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  standalone: true, // Permet d'utiliser `imports` directement
  imports: [CommonModule], // Ajout de CommonModule pour utiliser *ngFor
})
export class OrderBookComponent implements OnInit {
  orders: any[] = [];

  constructor(private webSocketService: WebSocketService) {}

  ngOnInit() {
    // Souscrire au flux d'ordres
    this.webSocketService.orderBook$.subscribe((data) => {
      this.orders = data;
      console.log('Ordres reçus:', this.orders);
    });
  }
}
