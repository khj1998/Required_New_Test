package required_new_propagation.service.discount.coupon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import required_new_propagation.entity.Coupon;
import required_new_propagation.vo.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CouponDiscountPolicyTest {
    @Nested
    @DisplayName("올바른 할인 정책 클래스가 선택되는지 테스트")
    class DiscountPolicyFactoryTest {

        private CouponDiscountPolicyFactory policyFactory;

        @BeforeEach
        void setUp() {
            List<CouponDiscountPolicy> policies = List.of(
                    new CouponRateDiscountPolicy(),
                    new CouponAmountDiscountPolicy()
            );
            policyFactory = new CouponDiscountPolicyFactory(policies);
        }

        @Test
        @DisplayName("정률 할인 쿠폰에 대해 CouponRateDiscountPolicy를 반환")
        void should_return_RateDiscountPolicy_for_PercentageCoupon() {
            Coupon percentageCoupon = createCoupon(DiscountType.PERCENTAGE, new BigDecimal("10"), null);
            String transactionId = UUID.randomUUID().toString();

            CouponDiscountPolicy policy = policyFactory.getDiscountPolicy(percentageCoupon, transactionId);

            assertThat(policy).isInstanceOf(CouponRateDiscountPolicy.class);
        }

        @Test
        @DisplayName("정액 할인 쿠폰에 대해 CouponAmountDiscountPolicy를 반환해야 한다")
        void should_return_AmountDiscountPolicy_for_FixedAmountCoupon() {
            Coupon fixedAmountCoupon = createCoupon(DiscountType.FIXED_AMOUNT, null, new BigDecimal("1000"));
            String transactionId = UUID.randomUUID().toString();

            CouponDiscountPolicy policy = policyFactory.getDiscountPolicy(fixedAmountCoupon, transactionId);

            assertThat(policy).isInstanceOf(CouponAmountDiscountPolicy.class);
        }
    }

    
    @Nested
    @DisplayName("정책에 따라 할인 금액이 정확하게 계산되는지 테스트")
    class DiscountCalculationTest {

        @DisplayName("정률 할인 정책 할인액 테스트")
        @ParameterizedTest(name = "원본 금액 {0}원에서 10% 할인을 적용하면 {1}원이 할인되어야 한다")
        @CsvSource({
                "10000, 1000",
                "5555, 556",   // 소수점 반올림 케이스
                "1000, 100",
                "0, 0"
        })
        void should_calculate_discount_correctly_for_PercentagePolicy(String originalAmountStr, String expectedDiscountStr) {
            BigDecimal originalAmount = new BigDecimal(originalAmountStr);
            BigDecimal expectedDiscount = new BigDecimal(expectedDiscountStr);

            Coupon coupon = createCoupon(DiscountType.PERCENTAGE, new BigDecimal("10"), null);
            CouponRateDiscountPolicy policy = new CouponRateDiscountPolicy();

            BigDecimal calculatedDiscount = policy.getDiscountAmount(originalAmount, coupon);

            assertThat(calculatedDiscount).isEqualByComparingTo(expectedDiscount);
        }


        @DisplayName("정액 할인 정책 할인액 테스트")
        @ParameterizedTest(name = "할인 금액 만큼만 할인액이 계산되어야 한다.")
        @CsvSource({
                "10000, 1000", 
                "1000, 1000",  
                "500, 500",   
                "0, 0"
        })
        void should_calculate_discount_correctly_for_AmountPolicy(String originalAmountStr, String expectedDiscountStr) {
            BigDecimal originalAmount = new BigDecimal(originalAmountStr);
            BigDecimal expectedDiscount = new BigDecimal(expectedDiscountStr);

            Coupon coupon = createCoupon(DiscountType.FIXED_AMOUNT, null, new BigDecimal("1000"));
            CouponAmountDiscountPolicy policy = new CouponAmountDiscountPolicy();

            BigDecimal calculatedDiscount = policy.getDiscountAmount(originalAmount, coupon);

            assertThat(calculatedDiscount).isEqualByComparingTo(expectedDiscount);
        }
    }

    private Coupon createCoupon(DiscountType type, BigDecimal rate, BigDecimal amount) {
        return Coupon.builder()
                .id(1L)
                .code("TEST-COUPON")
                .discountType(type)
                .discountRate(rate)
                .discountAmount(amount)
                .validFrom(LocalDateTime.now().minusDays(1))
                .validUntil(LocalDateTime.now().plusDays(1))
                .isActive(true)
                .build();
    }
}