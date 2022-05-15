package spring.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.Models.User;

import java.util.List;

public interface UserRepos extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.active = true")
    List<User> findAllActiveUsers();
}
