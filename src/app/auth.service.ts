import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private authTokenSubject = new BehaviorSubject<string | null>(null);
  private readonly tokenKey = 'auth_token';
  private readonly apiUrl = 'http://localhost:8089/ProjetSalleDeMarche/api/auth'; // URL d'authentification

  constructor(private http: HttpClient) {
    this.initializeToken();
  }

  // Initialiser le token au chargement
  private initializeToken(): void {
    const token = localStorage.getItem(this.tokenKey);
    if (token) {
      if (!this.estTokenExpire(token)) {
        this.authTokenSubject.next(token); // Initialiser le token si valide
      } else {
        this.logout(); // Supprimer le token expiré
      }
    }
  }

  // Vérifie si le token est expiré
  estTokenExpire(token: string): boolean {
    try {
      const payload = JSON.parse(atob(token.split('.')[1])); // Décoder le payload du JWT
      const expirationDate = payload.exp * 1000; // Expiration en millisecondes
      return expirationDate < Date.now(); // Comparer à la date actuelle
    } catch (error) {
      console.error('Erreur lors de la vérification du token:', error);
      return true; // Considérer comme expiré en cas d'erreur
    }
  }

  // Récupérer le token actuel
  getToken(): string | null {
    return this.authTokenSubject.getValue();
  }

  // Méthode de connexion
  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, { username, password }).pipe(
      tap((response) => {
        const token = response.accessToken?.trim();
        if (token) {
          localStorage.setItem(this.tokenKey, token); // Stocker le token
          this.authTokenSubject.next(token); // Mettre à jour le BehaviorSubject
        } else {
          throw new Error('Aucun jeton JWT reçu.');
        }
      }),
      catchError((error) => {
        console.error('Erreur de connexion:', error);
        return throwError(() => new Error(error.error?.message || 'Échec de la connexion.'));
      })
    );
  }

  // Déconnexion
  logout(): void {
    localStorage.removeItem(this.tokenKey); // Supprimer le token du stockage local
    this.authTokenSubject.next(null); // Réinitialiser le BehaviorSubject
  }
  

  // Vérifie si l'utilisateur est connecté
  isLoggedIn(): boolean {
    const token = this.getToken();
    return !!token && !this.estTokenExpire(token); // Vérifie l'existence et la validité du token
  }

  // Gestion des erreurs standardisée
  private handleError(error: any): Observable<never> {
    console.error('Erreur HTTP:', error);
    return throwError(() => new Error(error.error?.message || 'Erreur serveur.'));
  }
 
  
}
