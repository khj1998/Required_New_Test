package required_new_propagation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import required_new_propagation.dto.req.OrderRequest;
import required_new_propagation.entity.Order;
import required_new_propagation.entity.Product;
import required_new_propagation.entity.User;
import required_new_propagation.event.OrderFailureEvent;
import required_new_propagation.exception.DomainException;
import required_new_propagation.repository.OrderRepository;
import required_new_propagation.repository.ProductRepository;
import required_new_propagation.repository.UserRepository;
import required_new_propagation.vo.OrderStatus;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PaymentService paymentService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void createOrder(OrderRequest req) {
        log.info("TID : {}, 상품 주문 처리 시작",req.getTransactionId());

        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new DomainException(req.getTransactionId(), "사용자가 존재하지 않습니다. userId = " + req.getUserId()));

        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new DomainException(req.getTransactionId(), "상품이 존재하지 않습니다. productId = " + req.getProductId()));

        try {
            if (!product.isStockEnough(req.getQuantity())) {
                throw new DomainException(req.getTransactionId(), "상품의 재고가 부족합니다.");
            }

            if (!user.canUsePoint(req.getRequestedPoint())) {
                throw new DomainException(req.getTransactionId(),"사용자의 포인트가 부족합니다.");
            }

            BigDecimal totalAmount = product.getTotalAmount(req.getQuantity(),req.getRequestedPoint());

            user.usePoint(req.getRequestedPoint());
            product.decreaseStock(req.getQuantity());

            Order order = Order.builder()
                    .user(user)
                    .product(product)
                    .transactionId(req.getTransactionId())
                    .quantity(req.getQuantity())
                    .totalAmount(totalAmount)
                    .status(OrderStatus.PENDING)
                    .build();

            paymentService.processPayment(user,totalAmount,req.getTransactionId());

            orderRepository.save(order);

            log.info("TID : {}, 상품 주문 처리 완료",req.getTransactionId());
        } catch (Exception e) {
            OrderFailureEvent event = OrderFailureEvent.from(user,product, req.getTransactionId(),e.getMessage());
            eventPublisher.publishEvent(event);

            throw new DomainException(req.getTransactionId(),e.getMessage());
        }
    }
}
