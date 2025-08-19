package required_new_propagation.data_init;

import jakarta.transaction.Transactional;
import jdbc_bulk_insert_library.jdbc.BulkRepositoryExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jqwik.api.Arbitraries;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import required_new_propagation.entity.Coupon;
import required_new_propagation.entity.Product;
import required_new_propagation.entity.User;
import required_new_propagation.vo.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final BulkRepositoryExecutor bulkRepositoryExecutor;

    @Override
    @Transactional
    public void run(String... args){
        log.info("[ Fixture Monkey를 사용해 테스트용 User 데이터 생성을 시작 ]");

        List<Map<String, Object>> userParamsList = new ArrayList<>();

        int userCount = 100_000;
        for (int i = 0; i < userCount; i++) {
            Map<String, Object> userParams = new HashMap<>();
            userParams.put("point", new BigDecimal(Arbitraries.integers().between(1000,100000).sample()));
            userParamsList.add(userParams);
        }
        bulkRepositoryExecutor.execute(User.class, 1000, userParamsList);

        log.info("[ Fixture Monkey를 사용해 테스트용 Product 데이터 생성을 시작 ]");

        List<Map<String, Object>> productParamsList = new ArrayList<>();

        int productCount = 100_000;
        for (int i = 0; i < productCount; i++) {
            Map<String, Object> productParams = new HashMap<>();
            productParams.put("name", Arbitraries.strings().alpha().ofMaxLength(32).sample());
            productParams.put("price", new BigDecimal(Arbitraries.integers().between(100, 1000000).sample()));
            productParams.put("stock", Arbitraries.integers().between(50, 1000).sample());
            productParamsList.add(productParams);
        }
        bulkRepositoryExecutor.execute(Product.class, 1000, productParamsList);

        log.info("[ Fixture Monkey를 사용해 테스트용 Coupon 데이터 생성을 시작 ]");

        List<Map<String, Object>> couponParamsList = new ArrayList<>();

        int couponCount = 10_000;
        for (int i = 0; i < couponCount; i++) {
            Map<String, Object> couponParams = new HashMap<>();
            couponParams.put("code", UUID.randomUUID().toString());
            couponParams.put("validFrom", LocalDateTime.now().minusDays(10));
            couponParams.put("validUntil", LocalDateTime.now().plusDays(20));
            couponParams.put("isActive", true);
            couponParams.put("discountType", Arbitraries.of(DiscountType.PERCENTAGE, DiscountType.FIXED_AMOUNT).sample());
            couponParams.put("discountRate", new BigDecimal(Arbitraries.integers().between(10, 50).sample()));
            couponParams.put("discountAmount", new BigDecimal(Arbitraries.integers().between(1000, 5000).sample()));
            couponParamsList.add(couponParams);
        }
        bulkRepositoryExecutor.execute(Coupon.class, 1000, couponParamsList);

        log.info("[ 모든 테스트 데이터 생성 완료 ]");
    }
}
