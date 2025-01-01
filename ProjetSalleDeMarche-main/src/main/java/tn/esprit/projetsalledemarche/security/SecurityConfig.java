package tn.esprit.projetsalledemarche.security;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // Utilisez ce package pour Spring MVC
import org.springframework.web.filter.CorsFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthEntryPoint authEntryPoint;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthEntryPoint authEntryPoint) {
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Appliquer la configuration CORS
                .csrf(csrf -> csrf.disable()) // Désactiver CSRF si nécessaire
                .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Pas de session
                .authorizeRequests(authz -> authz
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Swagger
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll() // Permettre l'enregistrement et la connexion sans JWT
                        .requestMatchers("/formations/afficher").permitAll()
                        .requestMatchers("/formations/supprimer/{id}").permitAll()
                        .requestMatchers("/formations/afficher/{id}").permitAll()
                        .requestMatchers("/formations/modifier/{id}").permitAll()
                        .requestMatchers("/formations/ajouter").permitAll()
                        .requestMatchers("/portfolio/send-prices").permitAll() // Autoriser l'envoi des prix sans authentification
                        .requestMatchers("/portfolio/get-prices").permitAll() // Autoriser la récupération des prix sans authentification
                        .requestMatchers("/portfolio/get-selected-symbol").permitAll() // Autoriser sans authentification
                        .requestMatchers("/portfolio/get-all-symbols").permitAll() // Autoriser sans authentification
                        .requestMatchers("/portfolio/positions").permitAll() // Accès libre à l'endpoint des positions
                        .requestMatchers("/portfolio/get-market-data").permitAll() // Récupération des données du marché sans authentification
                        .requestMatchers("/portfolio/send-indicators/**").permitAll() // Permettre l'envoi des indicateurs
                        .requestMatchers("/portfolio/get-indicators/**").permitAll() // Permettre la récupération des indicateurs
                        .requestMatchers("/portfolio/enter-position").permitAll() // Accès libre pour entrer des positions
                        .requestMatchers("/portfolio/positions/close/**").permitAll() // Accès libre pour fermer une position
                        .requestMatchers("/evenements").permitAll()
                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/reviews/**").permitAll() // Permettre l'accès aux avis (ajouter ** pour tous les sous-chemins)

                        .anyRequest().authenticated()); // Toute autre requête nécessite une authentification

        // Ajout du filtre JWT personnalisé avant l'authentification par mot de passe
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200"); // Angular app
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public HttpFirewall customHttpFirewall() {
        return new CustomHttpFirewall();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }
}
