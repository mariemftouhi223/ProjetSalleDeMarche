import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { MarketChartComponent } from './market-chart/market-chart.component';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  standalone: true,  // Ajoutez 'standalone: true' si vous utilisez Angular 14+ avec des composants autonomes
  imports: [CommonModule, RouterOutlet]  // Si votre composant est autonome
})
export class AppComponent {
  title = 'Traider';
}
