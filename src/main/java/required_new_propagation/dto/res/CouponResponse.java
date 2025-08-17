package required_new_propagation.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import required_new_propagation.entity.Coupon;
import required_new_propagation.vo.DiscountType;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponResponse {
    private Long couponId;
    private DiscountType discountType;

    public static CouponResponse of(Coupon coupon) {
        return CouponResponse.builder()
                .couponId(coupon.getCouponId())
                .discountType(coupon.getDiscountType())
                .build();
    }
}
