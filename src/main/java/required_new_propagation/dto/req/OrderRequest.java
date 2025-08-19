package required_new_propagation.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    private Long productId;
    private Long userId;
    private int quantity;
    private BigDecimal requestedPoint;
    private Long couponId;
    private String transactionId;
}
