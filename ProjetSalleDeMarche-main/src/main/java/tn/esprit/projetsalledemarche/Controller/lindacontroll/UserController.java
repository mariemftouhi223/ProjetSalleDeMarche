package tn.esprit.projetsalledemarche.Controller.lindacontroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.projetsalledemarche.Entity.Linda.User;
import tn.esprit.projetsalledemarche.Service.Servicelinda.UserService;
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    // Endpoint pour ajouter un nouvel utilisateur
    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User addedUser = userService.addUser(user);
        return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
    }
}
