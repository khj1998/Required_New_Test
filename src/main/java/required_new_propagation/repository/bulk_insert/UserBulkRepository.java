package required_new_propagation.repository.bulk_insert;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import required_new_propagation.entity.User;
import required_new_propagation.repository.bulk_insert.parameter.ParameterSupplier;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserBulkRepository extends AbstractJdbcBulkRepository<User> {
    private final JdbcTemplate jdbcTemplate;

    @Override
    protected JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Override
    public Class<User> getSupportedType() {
        return User.class;
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO users (point) VALUES (?)";
    }

    @Override
    protected void setParameters(PreparedStatement ps, Map<String, ParameterSupplier<?>> suppliers) throws SQLException {
        ps.setBigDecimal(1, (BigDecimal) suppliers.get("point").get());
    }
}
