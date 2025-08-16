package required_new_propagation.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import required_new_propagation.entity.OrderFailLog;
import required_new_propagation.repository.OrderFailLogRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderFailureEventListener {
    private final OrderFailLogRepository orderFailLogRepository;

    @Async("myAsyncExecutor")
    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handle(OrderFailureEvent event) {
        log.info("주문 실패 로그 기록 user id={}, product id={}, transaction id={}",
                event.getUser().getUserId(), event.getProduct().getProductId(), event.getTransactionId());

        OrderFailLog failedOrder = OrderFailLog.builder()
                .user(event.getUser())
                .product(event.getProduct())
                .transactionId(event.getTransactionId())
                .message(event.getMessage())
                .build();

        orderFailLogRepository.save(failedOrder);
    }
}
