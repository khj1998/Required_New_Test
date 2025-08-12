package required_new_propagation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal point;

    private String nickname;

    private String os;

    private String appVersion;

    private String status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Long getUserId() {
        return this.id;
    }

    public boolean canUsePoint(BigDecimal requestedPoint) {
        return point.compareTo(requestedPoint) >= 0;
    }

    public void usePoint(BigDecimal requestedPoint) {
        this.point = this.point.subtract(requestedPoint);
    }
}
