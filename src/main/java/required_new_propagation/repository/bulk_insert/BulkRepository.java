package required_new_propagation.repository.bulk_insert;

import java.util.Map;

public interface BulkRepository<T> {
    Class<T> getSupportedType();
    void saveAllInBatch(int batchSize,Map<String,Object> params);
}
