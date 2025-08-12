package required_new_propagation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import required_new_propagation.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
