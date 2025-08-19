package required_new_propagation.data_init;

import jdbc_bulk_insert_library.jdbc.AbstractJdbcBulkRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import required_new_propagation.entity.Coupon;
import required_new_propagation.vo.DiscountType;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

@Component
public class CouponBulkStatementMapper extends AbstractJdbcBulkRepository<Coupon> {
    public CouponBulkStatementMapper(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
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
    protected void setParameters(PreparedStatement ps, Map<String, Object> params) throws SQLException {
        ps.setString(1, (String) params.get("code"));
        ps.setObject(2, params.get("validFrom"));
        ps.setObject(3, params.get("validUntil"));
        ps.setBoolean(4, (Boolean) params.get("isActive"));
        ps.setString(5, ((DiscountType) params.get("discountType")).name());
        ps.setBigDecimal(6, (BigDecimal) params.get("discountRate"));
        ps.setBigDecimal(7, (BigDecimal) params.get("discountAmount"));
    }
}
