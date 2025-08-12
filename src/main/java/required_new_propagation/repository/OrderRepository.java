package required_new_propagation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import required_new_propagation.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
