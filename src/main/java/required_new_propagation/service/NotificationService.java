package required_new_propagation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import required_new_propagation.exception.DomainException;

import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    @Async("myAsyncExecutor")
    public void sendNotification(Long userId, String transactionId) {
        log.info("[ Notification ] 알림 발송 시작 (비동기) - Transaction ID: {}", transactionId);

        try {
            Thread.sleep(100);

            Random random = new Random();
            if (random.nextDouble() <= 0.05) {
                throw new DomainException(transactionId,"알림 서버에 연결할 수 없습니다.");
            }

            log.info("[ Notification ] 사용자 '{}'에게 결제 완료 알림을 보냈습니다. (Transaction ID: {})", userId, transactionId);

        } catch (Exception e) {
            log.error("[ Notification ] 알림 전송 중 오류가 발생했습니다. (Transaction ID: {}). 에러: {}",
                    transactionId, e.getMessage());
        }
    }
}
