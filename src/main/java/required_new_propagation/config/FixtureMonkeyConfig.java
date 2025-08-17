package required_new_propagation.config;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FixtureMonkeyConfig {
    @Bean
    public FixtureMonkey fixtureMonkey() {
        return FixtureMonkey.builder()
                .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
                .build();
    }
}
