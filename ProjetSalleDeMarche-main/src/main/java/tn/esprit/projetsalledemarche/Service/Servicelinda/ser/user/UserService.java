package tn.esprit.projetsalledemarche.Service.Servicelinda.ser.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetsalledemarche.Entity.Linda.user.User;
import tn.esprit.projetsalledemarche.Repository.lindarepo.user.UserRepository;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Méthode pour ajouter un utilisateur
    public User addUser(User user) {
        return userRepository.save(user);
    }
}
