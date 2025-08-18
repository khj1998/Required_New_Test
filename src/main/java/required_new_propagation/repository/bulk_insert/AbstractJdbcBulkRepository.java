package required_new_propagation.repository.bulk_insert;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import required_new_propagation.repository.bulk_insert.parameter.ParameterSupplier;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public abstract class AbstractJdbcBulkRepository<T> implements BulkRepository<T> {
    @Override
    public final void saveAllInBatch(int batchSize,Map<String, ParameterSupplier<?>> parameterSuppliers) {
        getJdbcTemplate().batchUpdate(getInsertSql(), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                setParameters(ps, parameterSuppliers);
            }

            @Override
            public int getBatchSize() {
                return batchSize;
            }
        });
    }

    protected abstract String getInsertSql();

    protected abstract void setParameters(PreparedStatement ps, Map<String, ParameterSupplier<?>> suppliers) throws SQLException;

    protected abstract JdbcTemplate getJdbcTemplate();
}
