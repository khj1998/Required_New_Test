package required_new_propagation.repository.bulk_insert;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import required_new_propagation.entity.Product;
import required_new_propagation.repository.bulk_insert.parameter.ParameterSupplier;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ProductBulkRepository extends AbstractJdbcBulkRepository<Product> {
    private final JdbcTemplate jdbcTemplate;

    @Override
    protected JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
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
    protected void setParameters(PreparedStatement ps, Map<String, ParameterSupplier<?>> suppliers) throws SQLException {
        ps.setString(1, (String) suppliers.get("name").get());
        ps.setBigDecimal(2, (BigDecimal) suppliers.get("price").get());
        ps.setInt(3, (Integer) suppliers.get("stock").get());
    }
}
