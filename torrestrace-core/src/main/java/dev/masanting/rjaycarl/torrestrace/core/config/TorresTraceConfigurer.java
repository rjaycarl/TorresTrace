package dev.masanting.rjaycarl.torrestrace.core.config;

import dev.masanting.rjaycarl.torrestrace.core.interceptor.TorresTraceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
@Configuration
@DependsOn("torresTraceInterceptor")
public class TorresTraceConfigurer implements WebMvcConfigurer {

    private final TorresTraceInterceptor interceptor;

    @Autowired
    public TorresTraceConfigurer(TorresTraceInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/**");
        ;
    }
}
