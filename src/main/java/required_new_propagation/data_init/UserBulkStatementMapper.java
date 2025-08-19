package required_new_propagation.data_init;

import jdbc_bulk_insert_library.jdbc.AbstractJdbcBulkRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import required_new_propagation.entity.User;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

@Component
public class UserBulkStatementMapper extends AbstractJdbcBulkRepository<User> {
    public UserBulkStatementMapper(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
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
    protected void setParameters(PreparedStatement ps, Map<String, Object> params) throws SQLException {
        ps.setBigDecimal(1, (BigDecimal) params.get("point"));
    }
}
