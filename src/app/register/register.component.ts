import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  imports: [FormsModule, RouterModule, CommonModule],
  standalone: true
})
export class RegisterComponent {
  username: string = '';
  email: string = '';
  password: string = '';
  firstName: string = ''; // Nouveau champ pour le prénom
  lastName: string = '';  // Nouveau champ pour le nom


  constructor(private http: HttpClient, private router: Router) {}

  register() {
    const registerData = {
      username: this.username,
      email: this.email,
      password: this.password,
      firstName: this.firstName, // Inclure le prénom
      lastName: this.lastName   // Inclure le nom

    };

    // Envoi de la requête POST en acceptant une réponse texte
    this.http.post('http://localhost:8089/ProjetSalleDeMarche/api/auth/register', registerData, { responseType: 'text' })
      .subscribe(response => {
        alert(response);  // Affiche le message de succès retourné par le backend
        this.router.navigate(['/login']);  // Redirection vers la page de connexion après l'enregistrement
      }, error => {
        console.error('Registration failed', error);
        alert('Registration failed');  // Affiche un message d'erreur si l'enregistrement échoue
      });
  }
}
