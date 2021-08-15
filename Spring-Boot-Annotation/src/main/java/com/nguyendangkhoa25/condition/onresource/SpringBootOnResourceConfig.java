package com.nguyendangkhoa25.condition.onresource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBootOnResourceConfig {
    @Bean
    @ConditionalOnResource(resources = { "log4j.properties" })
    public Log4jService log4jService() {
        return new Log4jService();
    }
}
