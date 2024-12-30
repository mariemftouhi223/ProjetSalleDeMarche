import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { MarketChartComponent } from './market-chart/market-chart.component';
import { FormsModule } from '@angular/forms';
import { NgApexchartsModule } from 'ng-apexcharts';
@NgModule({
  declarations: [],
  imports: [BrowserModule, HttpClientModule,FormsModule,MarketChartComponent,NgApexchartsModule ]
})
export class AppModule {}
