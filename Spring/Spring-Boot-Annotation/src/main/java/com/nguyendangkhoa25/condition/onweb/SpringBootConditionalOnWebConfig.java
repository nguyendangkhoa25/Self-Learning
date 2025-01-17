package com.nguyendangkhoa25.condition.onweb;

import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBootConditionalOnWebConfig {
    @Bean
    @ConditionalOnWebApplication
    public WebModule webModule() {
        return new WebModule();
    }

    @Bean
    @ConditionalOnNotWebApplication
    public NotWebModule notWebModule() {
        return new NotWebModule();
    }
}
