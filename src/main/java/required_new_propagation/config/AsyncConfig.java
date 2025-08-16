package required_new_propagation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {
    @Bean(name = "myAsyncExecutor")
    public Executor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // 리틀의 법칙을 참고하였음.
        // 100TPS, 비동기 로직 소요시간 평균 100ms를 가정해 0.1 * 100 = 10개로 설정
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(200);

        executor.setThreadNamePrefix("my-async-thread-");

        executor.initialize();

        return executor;
    }
}
