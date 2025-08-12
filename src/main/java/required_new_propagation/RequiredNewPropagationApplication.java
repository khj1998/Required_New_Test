package required_new_propagation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class RequiredNewPropagationApplication {

    public static void main(String[] args) {
        SpringApplication.run(RequiredNewPropagationApplication.class, args);
    }

}
