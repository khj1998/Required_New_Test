package required_new_propagation.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import required_new_propagation.dto.req.OrderRequest;
import required_new_propagation.dto.res.CouponResponse;
import required_new_propagation.entity.Coupon;
import required_new_propagation.exception.DomainException;
import required_new_propagation.repository.CouponRepository;
import required_new_propagation.service.discount.coupon.CouponDiscountPolicy;
import required_new_propagation.service.discount.coupon.CouponDiscountPolicyFactory;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    private final CouponDiscountPolicyFactory couponDiscountPolicyFactory;

    @Transactional
    public CouponResponse getCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new DomainException("","존재하지 않는 쿠폰입니다."));

        return CouponResponse.of(coupon);
    }

    @Transactional
    public BigDecimal getCouponDiscountAmount(BigDecimal originalAmount, OrderRequest req) {
        Long couponId = req.getCouponId();
        String transactionId = req.getTransactionId();

        log.info("코드 {} 쿠폰 적용을 시작합니다. 시작 전 결재 금액 : {}",couponId,originalAmount);

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new DomainException(transactionId,"존재하지 않는 쿠폰입니다."));

        if (!coupon.isActive()) {
            throw new DomainException(transactionId,"유효하지 않은 쿠폰입니다.");
        }

        if (!coupon.isWithinValidPeriod()) {
            throw new DomainException(transactionId,"사용 가능한 기간이 기간이 아닙니다.");
        }

        CouponDiscountPolicy couponDiscountPolicy = couponDiscountPolicyFactory.getDiscountPolicy(coupon,transactionId);
        BigDecimal couponDiscountAmount = couponDiscountPolicy.getDiscountAmount(originalAmount,coupon);

        coupon.markUsedCoupon();
        log.info("ID : {} 쿠폰 적용을 완료.",couponId);

        return couponDiscountAmount;
    }
}
