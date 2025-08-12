package required_new_propagation.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import required_new_propagation.entity.Product;
import required_new_propagation.entity.User;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderFailureEvent {
    private User user;
    private Product product;
    private String transactionId;
    private String message;

    public static OrderFailureEvent from(User user,Product product,String transactionId, String message) {
        return OrderFailureEvent.builder()
                .user(user)
                .product(product)
                .transactionId(transactionId)
                .message(message)
                .build();
    }
}
