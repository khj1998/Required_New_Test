package required_new_propagation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import required_new_propagation.entity.Coupon;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
}
