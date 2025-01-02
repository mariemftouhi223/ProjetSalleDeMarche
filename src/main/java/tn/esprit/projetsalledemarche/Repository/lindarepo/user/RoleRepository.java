package tn.esprit.projetsalledemarche.Repository.lindarepo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projetsalledemarche.Entity.Linda.user.Role;

import java.util.Optional;
@Repository
public interface RoleRepository  extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);}
