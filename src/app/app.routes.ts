import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MarketChartComponent } from './market-chart/market-chart.component';
import { OrderBookComponent } from './order-book/order-book.component'; // Assurez-vous que ce chemin est correct
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';

export const routes: Routes = [
  { path: 'MarketChartComponent', component: MarketChartComponent }, // Route par défaut
  { path: 'order-book', component: OrderBookComponent }, 
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
 
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)], // Attention à utiliser `forRoot` ici
  exports: [RouterModule]
})
export class AppRoutingModule {}
