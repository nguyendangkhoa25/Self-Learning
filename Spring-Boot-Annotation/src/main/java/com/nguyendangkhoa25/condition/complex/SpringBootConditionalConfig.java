package com.nguyendangkhoa25.condition.complex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBootConditionalConfig {
    @Bean
    @Conditional(CustomConditional.class)
    public CustomConditionalModule customConditionalModule() {
        return new CustomConditionalModule();
    }
}
