package tn.esprit.projetsalledemarche.Repository.lindarepo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetsalledemarche.Entity.Linda.Quiz;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
}
