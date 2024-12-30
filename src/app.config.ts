// src/app/app.config.ts
import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes'; // Assurez-vous que le chemin est correct

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes)]
};
