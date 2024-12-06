package tn.esprit.projetsalledemarche.Controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetsalledemarche.Entity.Linda.user.User;
import tn.esprit.projetsalledemarche.Service.ser.user.EmailService;
import tn.esprit.projetsalledemarche.Service.ser.user.UserService;
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    // Endpoint pour ajouter un nouvel utilisateur
    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User addedUser = userService.addUser(user);
        return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
    }








        @GetMapping("/test")
        public String testEmail() {
            try {
                emailService.sendConfirmationEmail("mariem.ftouhi@esprit.tn", "Mimicha");
                return "Email envoyé avec succès !";
            } catch (Exception e) {
                return "Erreur : " + e.getMessage();
            }
        }
    }




