package required_new_propagation.service.discount.coupon;

import required_new_propagation.entity.Coupon;
import required_new_propagation.vo.DiscountType;

import java.math.BigDecimal;

public interface CouponDiscountPolicy {
    DiscountType supports();
    BigDecimal getDiscountAmount(BigDecimal originalAmount, Coupon coupon);
}
