import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { priceUpdate } from './priceUpdate'; // Assurez-vous que le chemin d'importation est correct

@Injectable({
  providedIn: 'root'
})
export class ServicesTsService {

  readonly API_URL = 'http://localhost:8083/ws';

  constructor() { }

  // Méthode pour obtenir les prix actualisés


  // Méthode pour gérer les erreurs
  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Unknown error!';
    if (error.error instanceof ErrorEvent) {
      // Erreur client
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Erreur du serveur
      errorMessage = `Error Code: ${error.status}, Message: ${error.message}`;
    }
    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}
