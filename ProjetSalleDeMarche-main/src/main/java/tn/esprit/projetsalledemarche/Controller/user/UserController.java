package tn.esprit.projetsalledemarche.Controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetsalledemarche.Entity.Linda.user.User;
import tn.esprit.projetsalledemarche.Service.user.UserService;

import java.util.List;

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
    @GetMapping("/api/users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
    // Supprimer un utilisateur par ID
    @DeleteMapping("user/supprimer/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);  // Appel au service pour récupérer l'utilisateur
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);  // Si trouvé, renvoyer l'utilisateur
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Si non trouvé, renvoyer 404
        }
    }
}
