// sign-up.component.ts
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
})
export class SignUpComponent {
  constructor(private router: Router) {}

  onSignUp() {
    // Logique d'inscription
    // Par exemple : appel à un service d'inscription
    const signupSuccessful = true; // Remplacez cela par votre logique réelle

    if (signupSuccessful) {
      this.router.navigate(['/sign-in']); // Redirige vers la page de connexion
    } else {
      // Gérer l'échec de l'inscription (afficher un message d'erreur, etc.)
    }
  }
}
