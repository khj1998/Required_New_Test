package required_new_propagation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import required_new_propagation.exception.DomainException;
import required_new_propagation.vo.DiscountType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column
    private BigDecimal discountRate;

    @Column
    private BigDecimal discountAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;

    @Column(nullable = false)
    private LocalDateTime validFrom;

    @Column(nullable = false)
    private LocalDateTime validUntil;

    @Column(nullable = false)
    private boolean isActive;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Long getCouponId() {
        return this.id;
    }

    public DiscountType getDiscountType() {
        return this.discountType;
    }

    public boolean isWithinValidPeriod() {
        LocalDateTime now = LocalDateTime.now();
        return !now.isBefore(this.validFrom) && !now.isAfter(this.validUntil);
    }

    public BigDecimal getDiscountAmountForAmountPolicy(BigDecimal originalAmount) {
        return this.discountAmount.min(originalAmount);
    }

    public BigDecimal getDiscountAmountForRatePolicy(BigDecimal originalAmount) {
        return originalAmount.multiply(this.discountRate)
                .divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP);
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void markUsedCoupon() {
        this.isActive = false;
    }
}
