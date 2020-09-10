package by.masarnovsky.releasedemon.repository;

import by.masarnovsky.releasedemon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLogin(String login);
}
