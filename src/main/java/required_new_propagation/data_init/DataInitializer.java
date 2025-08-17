package required_new_propagation.data_init;

import com.navercorp.fixturemonkey.FixtureMonkey;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jqwik.api.Arbitraries;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import required_new_propagation.entity.Coupon;
import required_new_propagation.entity.Product;
import required_new_propagation.entity.User;
import required_new_propagation.repository.CouponRepository;
import required_new_propagation.repository.ProductRepository;
import required_new_propagation.repository.UserRepository;
import required_new_propagation.vo.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;
    private final FixtureMonkey fixtureMonkey;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("[ Fixture Monkey를 사용해 테스트용 User 데이터 생성을 시작 ]");

        List<User> testUsers = fixtureMonkey.giveMeBuilder(User.class)
                .setNull("id")
                .set("point",Arbitraries.bigDecimals().between(new BigDecimal("1000"),new BigDecimal("100000")))
                .sampleList(100000);
        userRepository.saveAll(testUsers);

        log.info("[ Fixture Monkey를 사용해 테스트용 Product 데이터 생성을 시작 ]");

        List<Product> products = fixtureMonkey.giveMeBuilder(Product.class)
                .setNull("id")
                .set("price", Arbitraries.bigDecimals().between(new BigDecimal("100"), new BigDecimal("1000000")))
                .set("stock", Arbitraries.integers().between(50, 1000))
                .sampleList(100000);
        productRepository.saveAll(products);

        log.info("[ Fixture Monkey를 사용해 테스트용 Coupon 데이터 생성을 시작 ]");

        List<Coupon> coupons = fixtureMonkey.giveMeBuilder(Coupon.class)
                .setNull("id")
                .set("code", UUID.randomUUID().toString())
                .set("validFrom", LocalDateTime.now().minusDays(10))
                .set("validUntil", LocalDateTime.now().plusDays(20))
                .set("isActive", true)
                .set("discountType",Arbitraries.of(DiscountType.PERCENTAGE,DiscountType.FIXED_AMOUNT))
                .set("discountRate",Arbitraries.bigDecimals().between(new BigDecimal("10"),new BigDecimal("50")))
                .set("discountAmount",Arbitraries.bigDecimals().between(new BigDecimal("1000"),new BigDecimal("5000")))
                .sampleList(10000);

        couponRepository.saveAll(coupons);
    }
}
