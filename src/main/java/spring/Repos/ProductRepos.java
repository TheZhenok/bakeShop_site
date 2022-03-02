package spring.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.Models.Product;

public interface ProductRepos extends JpaRepository<Product, Long> {
}
