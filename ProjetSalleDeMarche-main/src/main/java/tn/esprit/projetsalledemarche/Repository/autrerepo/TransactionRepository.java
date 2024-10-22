package tn.esprit.projetsalledemarche.Repository.autrerepo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.autre.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
