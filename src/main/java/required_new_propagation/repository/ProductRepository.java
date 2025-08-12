package required_new_propagation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import required_new_propagation.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
