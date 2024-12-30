import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PredictionService {
  private apiUrl = 'http://127.0.0.1:5000/predict'; // URL de votre backend Flask

  constructor(private http: HttpClient) {}

  getPrediction(): Observable<{ confidence: number; decision: string; timestamp: string }> {
    return this.http.get<{ confidence: number; decision: string; timestamp: string }>(this.apiUrl);
  }
}
