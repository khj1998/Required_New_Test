package required_new_propagation.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Boolean> handleException(DomainException ex) {
        log.info("주문 요청 처리 실패, 트랜잭션 Id : {}, 사유 : {}"
                ,ex.getTransactionId(),ex.getMessage());
        return ResponseEntity.ok(false);
    }
}
