package tn.esprit.projetsalledemarche.Repository.lindarepo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.Linda.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
