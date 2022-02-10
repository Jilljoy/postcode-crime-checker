package hill.postcodecrimechecker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:urls.properties")
@EnableCaching
public class PostcodeCrimeCheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostcodeCrimeCheckerApplication.class, args);
    }

}
