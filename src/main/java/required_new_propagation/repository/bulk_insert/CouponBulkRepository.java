package required_new_propagation.repository.bulk_insert;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import required_new_propagation.entity.Coupon;
import required_new_propagation.repository.bulk_insert.parameter.ParameterSupplier;
import required_new_propagation.vo.DiscountType;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CouponBulkRepository extends AbstractJdbcBulkRepository<Coupon> {
    private final JdbcTemplate jdbcTemplate;

    @Override
    protected JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Override
    public Class<Coupon> getSupportedType() {
        return Coupon.class;
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO coupons (code, valid_from, valid_until, is_active, discount_type, discount_rate, discount_amount) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected void setParameters(PreparedStatement ps, Map<String, ParameterSupplier<?>> suppliers) throws SQLException {
        ps.setString(1, (String) suppliers.get("code").get());
        ps.setObject(2, suppliers.get("validFrom").get());
        ps.setObject(3, suppliers.get("validUntil").get());
        ps.setBoolean(4, (Boolean) suppliers.get("isActive").get());
        ps.setString(5, ((DiscountType) suppliers.get("discountType").get()).name());
        ps.setBigDecimal(6, (BigDecimal) suppliers.get("discountRate").get());
        ps.setBigDecimal(7, (BigDecimal) suppliers.get("discountAmount").get());
    }
}
