package com.nguyendangkhoa25.condition.onproperty;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBootOnPropertyConfig {
    @Bean
    @ConditionalOnProperty(prefix = "on.property", name = "enabled")
    public SpringBootOnPropertyService springBootOnPropertyService() {
        return new SpringBootOnPropertyService();
    }

    @Bean
    @ConditionalOnProperty(prefix = "on.property", name = "enabled", havingValue = "string-value")
    public SpringBootOnPropertyValueService springBootOnPropertyValueService() {
        return new SpringBootOnPropertyValueService();
    }
}
