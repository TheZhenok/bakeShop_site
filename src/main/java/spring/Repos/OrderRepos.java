package spring.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.Models.Order;
import spring.Models.Product;

public interface OrderRepos extends JpaRepository<Order, Long> {
}
