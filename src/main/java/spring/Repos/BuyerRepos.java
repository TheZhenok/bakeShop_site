package spring.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.Models.Buyer;

public interface BuyerRepos extends JpaRepository<Buyer, Long> {
}
