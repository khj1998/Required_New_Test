package required_new_propagation.data_init;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jqwik.api.Arbitraries;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import required_new_propagation.entity.Coupon;
import required_new_propagation.entity.Product;
import required_new_propagation.entity.User;
import required_new_propagation.repository.bulk_insert.BulkRepositoryExecutor;
import required_new_propagation.repository.bulk_insert.parameter.ParameterSupplier;
import required_new_propagation.vo.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final BulkRepositoryExecutor bulkRepositoryExecutor;

    @Override
    @Transactional
    public void run(String... args){
        log.info("[ Fixture Monkey를 사용해 테스트용 User 데이터 생성을 시작 ]");

        Map<String, Object> userParams = new HashMap<>();
        userParams.put("point", Arbitraries.bigDecimals().between(new BigDecimal("1000"), new BigDecimal("100000")).sample());
        bulkRepositoryExecutor.execute(User.class, 100000,500,userParams);


        log.info("[ Fixture Monkey를 사용해 테스트용 Product 데이터 생성을 시작 ]");

        Map<String, Object> productParams = new HashMap<>();
        productParams.put("name", Arbitraries.strings().alpha().ofMaxLength(32).sample());
        productParams.put("price",Arbitraries.bigDecimals().between(new BigDecimal("100"), new BigDecimal("1000000")).sample());
        productParams.put("stock",Arbitraries.integers().between(50, 1000).sample());
        bulkRepositoryExecutor.execute(Product.class,100000,500,productParams);


        log.info("[ Fixture Monkey를 사용해 테스트용 Coupon 데이터 생성을 시작 ]");

        Map<String, Object> couponParams = new HashMap<>();
        couponParams.put("code",UUID.randomUUID().toString());
        couponParams.put("validFrom",LocalDateTime.now().minusDays(10));
        couponParams.put("validUntil",LocalDateTime.now().plusDays(20));
        couponParams.put("isActive",true);
        couponParams.put("discountType",Arbitraries.of(DiscountType.PERCENTAGE, DiscountType.FIXED_AMOUNT).sample());
        couponParams.put("discountRate",Arbitraries.bigDecimals().between(new BigDecimal("10"), new BigDecimal("50")).sample());
        couponParams.put("discountAmount",Arbitraries.bigDecimals().between(new BigDecimal("1000"), new BigDecimal("5000")).sample());

        bulkRepositoryExecutor.execute(Coupon.class, 1000, 500, couponParams);

        log.info("[ 모든 테스트 데이터 생성 완료 ]");
    }
}
