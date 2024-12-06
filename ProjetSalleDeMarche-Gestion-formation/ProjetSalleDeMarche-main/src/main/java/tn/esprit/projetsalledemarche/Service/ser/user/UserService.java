package tn.esprit.projetsalledemarche.Service.ser.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.Linda.user.User;
import tn.esprit.projetsalledemarche.Repository.lindarepo.user.UserRepository;
@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {
        // Vérifiez l'encodage du mot de passe
        System.out.println("Mot de passe avant encodage : " + user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Sauvegarde de l'utilisateur
        User savedUser = userRepository.save(user);
        System.out.println("Utilisateur sauvegardé : " + savedUser.getUsername());

        // Envoi de l'email de confirmation
        try {
            emailService.sendConfirmationEmail(savedUser.getEmail(), savedUser.getUsername());
            System.out.println("Email envoyé à : " + savedUser.getEmail());
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
        }

        return savedUser;
    }
}