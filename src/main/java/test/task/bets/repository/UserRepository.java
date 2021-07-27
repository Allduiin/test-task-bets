package test.task.bets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.task.bets.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
