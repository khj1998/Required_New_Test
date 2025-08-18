package required_new_propagation.repository.bulk_insert;

import required_new_propagation.repository.bulk_insert.parameter.ParameterSupplier;

import java.util.Map;

public interface BulkRepository<T> {
    Class<T> getSupportedType();
    void saveAllInBatch(int batchSize,Map<String, ParameterSupplier<?>> parameterSupplier);
}
