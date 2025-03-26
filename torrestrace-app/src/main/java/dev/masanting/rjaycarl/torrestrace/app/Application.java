package dev.masanting.rjaycarl.torrestrace.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
@SpringBootApplication
@ComponentScan(basePackages = "dev.masanting.rjaycarl.torrestrace")
@EntityScan("dev.masanting.rjaycarl.torrestrace.storage.entity")
@EnableJpaRepositories("dev.masanting.rjaycarl.torrestrace.storage.repository")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
