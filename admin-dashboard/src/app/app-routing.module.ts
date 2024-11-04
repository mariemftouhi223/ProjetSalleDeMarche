import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component'; // Assurez-vous que ce composant existe
import { SignInComponent } from './sign-in/sign-in.component'; // Assurez-vous que ce composant existe
import { SignUpComponent } from './sign-up/sign-up.component'; // Assurez-vous que ce composant existe

const routes: Routes = [
  { path: '', component: HomeComponent }, // Route pour la page d'accueil
  { path: 'profile', component: ProfileComponent }, // Route pour le profil
  { path: 'sign-in', component: SignInComponent }, // Route pour la connexion
  { path: 'sign-up', component: SignUpComponent }, // Route pour l'inscription
  
  // Ajoutez d'autres routes si nécessaire
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
