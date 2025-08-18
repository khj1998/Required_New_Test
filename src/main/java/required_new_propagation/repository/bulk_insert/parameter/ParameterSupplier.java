package required_new_propagation.repository.bulk_insert.parameter;

@FunctionalInterface
public interface ParameterSupplier<T> {
    T get();
}
