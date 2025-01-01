package tn.esprit.projetsalledemarche.Service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.Linda.user.User;
import tn.esprit.projetsalledemarche.Repository.lindarepo.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Méthode pour ajouter un utilisateur
    public User addUser(User user) {
        return userRepository.save(user);

    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    // Méthode pour supprimer un utilisateur par ID
    public void deleteUserById(int id) {
        userRepository.deleteById((long) id);
    }
    // Méthode pour mettre à jour un utilisateur
    public User updateUser(int id, User user) {
        Optional<User> existingUser = userRepository.findById((long) id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            // Mettre à jour les champs de l'utilisateur avec les nouvelles valeurs
            updatedUser.setUsername(user.getUsername());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setPortfolioBalance(user.getPortfolioBalance());
            updatedUser.setInitialBalance(user.getInitialBalance());
            updatedUser.setRoles(user.getRoles());

            return userRepository.save(updatedUser);  // Enregistrer les modifications
        }
        return null;  // Si l'utilisateur n'existe pas, retourner null
    }
    // Méthode pour récupérer un utilisateur par ID
    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);  // Retourner l'utilisateur ou null si non trouvé
    }
}
