import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';
import { importProvidersFrom } from '@angular/core'; 
import { HttpClientModule } from '@angular/common/http'; // Import du module HTTP
import { MarketChartComponent } from './app/market-chart/market-chart.component';

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes), 
    importProvidersFrom(HttpClientModule) // Import correctement fourni
  ],
}).catch(err => console.error(err));
