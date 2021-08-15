package com.nguyendangkhoa25.condition.onbean;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBootOnBeanConfig {
    @Bean
    @ConditionalOnBean(value = RequiredBean.class)
    public SpringBootOnBeanService springBootOnBeanService() {
        return new SpringBootOnBeanService();
    }

    @Bean
    @ConditionalOnMissingBean(name = "requiredBean")
    public SpringBootOnMissingBeanService springBootOnMissingBeanService() {
        return new SpringBootOnMissingBeanService();
    }
}
