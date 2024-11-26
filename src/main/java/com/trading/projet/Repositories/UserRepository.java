package com.trading.projet.Repositories;

import com.trading.projet.Entities.Portfolio;
import com.trading.projet.Entities.Position;

import com.trading.projet.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

}
