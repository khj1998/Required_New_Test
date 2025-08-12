package required_new_propagation.dto.req;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderRequest {
    private Long productId;
    private Long userId;
    private int quantity;
    private BigDecimal requestedPoint;
    private String transactionId;
}
