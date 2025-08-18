package required_new_propagation.repository.bulk_insert;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import required_new_propagation.entity.Product;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

@Component
public class ProductBulkStatementMapper extends AbstractJdbcBulkRepository<Product> {
    public ProductBulkStatementMapper(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Class<Product> getSupportedType() {
        return Product.class;
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO products (name, price, stock) VALUES (?, ?, ?)";
    }

    @Override
    protected void setParameters(PreparedStatement ps, Map<String, Object> params) throws SQLException {
        ps.setString(1, (String) params.get("name"));
        ps.setBigDecimal(2, (BigDecimal) params.get("price"));
        ps.setInt(3, (Integer) params.get("stock"));
    }
}
