// sign-in.component.ts
import { Component } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
})
export class SignInComponent {
  // Logique pour la connexion
  constructor(private router: Router) {}

  onSignIn() {
    // Logique de connexion
    // Par exemple : appel à un service d'authentification
    const loginSuccessful = true; // Remplacez cela par votre logique réelle

    if (loginSuccessful) {
      this.router.navigate(['/']); // Redirige vers le tableau de bord
    } else {
      // Gérer l'échec de la connexion (afficher un message d'erreur, etc.)
    }
  }
}
