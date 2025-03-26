package dev.masanting.rjaycarl.torrestrace.rest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI torresTraceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TorresTrace Replay API")
                        .version("1.0.0")
                        .description("API for replaying logged HTTP requests.")
                        .contact(new Contact()
                                .name("R-Jay Carl Torres")
                                .email("rjaycarltorres@gmail.com")
                                .url("https://github.com/rjaycarl/")
                        )
                        .license(new License().name("MIT License").url("https://opensource.org/licenses/MIT"))
                );
    }

}
