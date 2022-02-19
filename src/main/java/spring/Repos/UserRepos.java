package spring.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.Models.User;

public interface UserRepos extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
