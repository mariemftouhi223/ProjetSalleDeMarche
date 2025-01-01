package tn.esprit.projetsalledemarche.Repository.lindarepo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projetsalledemarche.Entity.Linda.user.User;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);



}
