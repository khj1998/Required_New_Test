package required_new_propagation.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import required_new_propagation.dto.req.OrderRequest;
import required_new_propagation.entity.Coupon;
import required_new_propagation.repository.CouponRepository;
import required_new_propagation.service.discount.coupon.CouponAmountDiscountPolicy;
import required_new_propagation.service.discount.coupon.CouponDiscountPolicy;
import required_new_propagation.service.discount.coupon.CouponDiscountPolicyFactory;
import required_new_propagation.service.discount.coupon.CouponRateDiscountPolicy;
import required_new_propagation.vo.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {
    private CouponService couponService;

    @Mock
    private CouponRepository couponRepository;

    private CouponDiscountPolicyFactory couponDiscountPolicyFactory;

    @BeforeEach
    void setUp() {
        List<CouponDiscountPolicy> policies = List.of(
                new CouponAmountDiscountPolicy(),
                new CouponRateDiscountPolicy()
        );

        couponDiscountPolicyFactory = new CouponDiscountPolicyFactory(policies);

        couponService = new CouponService(couponRepository,couponDiscountPolicyFactory);
    }

    @Test
    @DisplayName("금액 할인 테스트, 1천원 만큼 할인액이 발생하는지 확인")
    void applyAmountDiscountCoupon_Success() {
        BigDecimal originalAmount = new BigDecimal("10000");
        String couponCode = "AMOUNT1000";

        OrderRequest orderRequest = OrderRequest.builder()
                .couponCode(couponCode)
                .transactionId(UUID.randomUUID().toString())
                .build();

        Coupon amountCoupon = Coupon.builder()
                .code(couponCode)
                .discountType(DiscountType.FIXED_AMOUNT)
                .discountAmount(BigDecimal.valueOf(1000))
                .discountRate(null)
                .isActive(true)
                .validFrom(LocalDateTime.now().minusDays(1))
                .validUntil(LocalDateTime.now().plusDays(1))
                .build();

        when(couponRepository.findByCode(couponCode)).thenReturn(Optional.of(amountCoupon));

        BigDecimal discountAmount = couponService.getCouponDiscountAmount(originalAmount, orderRequest);

        assertThat(discountAmount).isEqualByComparingTo(new BigDecimal("1000"));
    }

    @Test
    @DisplayName("비율 할인 테스트, 20%만큼 금액의 할인액이 발생하는지 확인")
    void applyRateDiscountCoupon_Success() {
        BigDecimal originalAmount = new BigDecimal("10000");
        String couponCode = "Rate20";

        OrderRequest orderRequest = OrderRequest.builder()
                .couponCode(couponCode)
                .transactionId(UUID.randomUUID().toString())
                .build();

        Coupon rateCoupon = Coupon.builder()
                .code(couponCode)
                .discountType(DiscountType.PERCENTAGE)
                .discountAmount(null)
                .discountRate(BigDecimal.valueOf(20))
                .isActive(true)
                .validFrom(LocalDateTime.now().minusDays(1))
                .validUntil(LocalDateTime.now().plusDays(1))
                .build();

        when(couponRepository.findByCode(couponCode)).thenReturn(Optional.of(rateCoupon));

        BigDecimal discountAmount = couponService.getCouponDiscountAmount(originalAmount, orderRequest);

        assertThat(discountAmount).isEqualByComparingTo(new BigDecimal("2000"));
    }
}