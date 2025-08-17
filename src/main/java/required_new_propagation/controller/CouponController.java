package required_new_propagation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import required_new_propagation.dto.res.CouponResponse;
import required_new_propagation.service.CouponService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class CouponController {
    private final CouponService couponService;

    @GetMapping
    public ResponseEntity<CouponResponse> getCoupon(@Param("couponId") Long couponId) {
        return ResponseEntity.ok(couponService.getCoupon(couponId));
    }
}
