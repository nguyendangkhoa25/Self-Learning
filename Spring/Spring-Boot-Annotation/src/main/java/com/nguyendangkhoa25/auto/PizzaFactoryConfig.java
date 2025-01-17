package com.nguyendangkhoa25.auto;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class PizzaFactoryConfig {
    public Baker baker(){
        return new Baker();
    }
}
