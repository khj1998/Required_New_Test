package required_new_propagation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import required_new_propagation.entity.OrderFailLog;

public interface OrderFailLogRepository extends JpaRepository<OrderFailLog,Long> {
}
