package tn.esprit.projetsalledemarche.security;

import org.springframework.security.web.firewall.FirewalledRequest;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import jakarta.servlet.http.HttpServletRequest;

public class CustomHttpFirewall extends StrictHttpFirewall {
    @Override
    public FirewalledRequest getFirewalledRequest(HttpServletRequest request) throws RequestRejectedException {
        // Appel à la méthode parente pour appliquer la logique de firewall de base
        FirewalledRequest firewalledRequest = super.getFirewalledRequest(request);

        // Logique personnalisée pour autoriser certains caractères comme "%0A"
        String requestUri = firewalledRequest.getRequestURI();

        // Si l'URL contient "%0A", on peut décider de l'accepter ou de la traiter d'une manière spéciale
        if (requestUri.contains("%0A")) {
            // Par exemple, vous pouvez remplacer "%0A" par un caractère autorisé ou le traiter différemment
            // Si vous souhaitez permettre ces caractères, vous pouvez simplement ignorer cette vérification.
            return firewalledRequest; // Retourner sans erreur
        }

        // Sinon, le comportement par défaut (comme pour les autres caractères) est appliqué
        return firewalledRequest;
    }
}
