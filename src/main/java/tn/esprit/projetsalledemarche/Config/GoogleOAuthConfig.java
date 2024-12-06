package tn.esprit.projetsalledemarche.Config;
import com.google.api.client.json.JsonFactory;


import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.InputStreamReader;
import java.util.Collections;

public class GoogleOAuthConfig {
    private static final String CLIENT_SECRET_FILE = "/client_secret.json"; // Chemin vers le fichier

    public static GoogleCredentials getCredentials(NetHttpTransport HTTP_TRANSPORT) throws Exception {
        // Utiliser JacksonFactory de l'API Google
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        // Charger les informations d'identification de client
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory,
                new InputStreamReader(GoogleOAuthConfig.class.getResourceAsStream(CLIENT_SECRET_FILE)));

        // Configurer le flux d'autorisation
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, jsonFactory, clientSecrets, Collections.singleton(CalendarScopes.CALENDAR))
                .setAccessType("offline")
                .build();

        // Obtenir les informations d'identification via GoogleCredentials
        return GoogleCredentials.fromStream(GoogleOAuthConfig.class.getResourceAsStream(CLIENT_SECRET_FILE));
    }
}
