package tn.esprit.projetsalledemarche.Service.Servicelinda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.Linda.User;
import tn.esprit.projetsalledemarche.Repository.lindarepo.UserRepository;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Méthode pour ajouter un utilisateur
    public User addUser(User user) {
        return userRepository.save(user);
    }
}
