package required_new_propagation.service.discount.coupon;

import org.springframework.stereotype.Component;
import required_new_propagation.entity.Coupon;
import required_new_propagation.vo.DiscountType;

import java.math.BigDecimal;

@Component
public class CouponRateDiscountPolicy implements CouponDiscountPolicy {
    @Override
    public BigDecimal getDiscountAmount(BigDecimal originalAmount, Coupon coupon) {
        return coupon.getDiscountAmountForRatePolicy(originalAmount);
    }

    @Override
    public DiscountType supports() {
        return DiscountType.PERCENTAGE;
    }
}
