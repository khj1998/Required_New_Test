package required_new_propagation.exception;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {
    private final String transactionId;
    private final String message;

    public DomainException(String transactionId,String message) {
        super(message);
        this.transactionId = transactionId;
        this.message = message;
    }
}
