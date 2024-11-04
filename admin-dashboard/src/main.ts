import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component'; // Chemin vers votre AppComponent
import { AppRoutingModule } from './app/app-routing.module'; // Importer le module de routage

bootstrapApplication(AppComponent, {
  providers: [AppRoutingModule],
});
