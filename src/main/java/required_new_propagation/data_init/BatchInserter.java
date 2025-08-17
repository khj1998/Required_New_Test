package required_new_propagation.data_init;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BatchInserter {
    @Transactional
    public <T> void saveInBatch(JpaRepository<T,Long> repository, List<T> entities,int batchSize) {
        for (int i=0; i<entities.size(); i+=batchSize) {
            List<T> subList = entities.subList(i,Math.min(i+batchSize,entities.size()));
            repository.saveAll(subList);
        }
    }
}
