import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';


export const routes: Routes = [

  // autres routes ici...
];

@NgModule({
  declarations: [
   
   
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes), // Correct ici
  ],
  providers: [],
  bootstrap: [] // Cela est OK si AppComponent n'est pas autonome
})
export class AppModule { }
