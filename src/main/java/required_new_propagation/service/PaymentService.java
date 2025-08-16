package required_new_propagation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import required_new_propagation.entity.Payment;
import required_new_propagation.entity.User;
import required_new_propagation.exception.DomainException;
import required_new_propagation.repository.PaymentRepository;
import required_new_propagation.vo.PaymentStatus;

import java.math.BigDecimal;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Transactional
    public void processPayment(User user, BigDecimal totalAmount, String transactionId) throws InterruptedException {
        if (totalAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainException(transactionId, "결재 금액은 0보다 작을 수 없습니다.");
        }

        // 결제 게이트웨이 호출 시, 지연을 가정.
        Thread.sleep(100);

        Random random = new Random();
        if (random.nextDouble() <= 0.05) {
                throw new DomainException(transactionId,"상품 주문 결재에 실패하였습니다.");
        }

        Payment payment = Payment.builder()
                    .user(user)
                    .transactionId(transactionId)
                    .status(PaymentStatus.PAID_SUCCESS)
                    .build();
        paymentRepository.save(payment);
    }
}
