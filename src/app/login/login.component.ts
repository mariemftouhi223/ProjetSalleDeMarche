import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common'; //

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  imports: [FormsModule, RouterModule, CommonModule]  ,
  standalone: true

})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  login() {
    const loginData = { username: this.username, password: this.password };
  
    this.http.post('http://localhost:8089/ProjetSalleDeMarche/api/auth/login', loginData)
      .subscribe((response: any) => {
        console.log('Login response:', response); 
  
        // Vérifiez si le token et le type sont présents dans la réponse
        const token = `${response.tokenType}${response.tokenType.trim() ? ' ' : ''}${response.accessToken}`;
        
        // Sauvegarde du token correctement formaté dans localStorage
        localStorage.setItem('auth_token', token);
        console.log('Token sauvegardé dans localStorage:', token);
  
        this.router.navigate(['/dashboard/default']);  // Redirection après la connexion
      }, error => {
        console.error('Login failed', error);
        alert(`Login failed: ${error.statusText || 'Unknown error'}`);  // Affichage d'un message plus détaillé
      });
  }
  
}
