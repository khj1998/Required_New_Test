package required_new_propagation.service.discount.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import required_new_propagation.entity.Coupon;
import required_new_propagation.exception.DomainException;
import required_new_propagation.vo.DiscountType;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CouponDiscountPolicyFactory {
    private final List<CouponDiscountPolicy> discountPolicies;

    public CouponDiscountPolicy getDiscountPolicy(Coupon coupon,String transactionId) {
        DiscountType targetDiscountType = coupon.getDiscountType();

        return discountPolicies.stream()
                .filter(policy -> policy.supports() == targetDiscountType)
                .findFirst()
                .orElseThrow(() -> new DomainException(transactionId,"지원되는 쿠폰 할인 정책이 아닙니다."));
    }
}
