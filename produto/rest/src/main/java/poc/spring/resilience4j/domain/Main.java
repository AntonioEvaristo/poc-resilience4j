package poc.spring.resilience4j.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "poc.spring.resilience4j")
@EntityScan(basePackages = "poc.spring.resilience4j.integration")
@EnableJpaRepositories(basePackages = "poc.spring.resilience4j.integration")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}