package com.nguyendangkhoa25.condition.onclass;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//Conditional by Name
//@ConditionalOnClass(name = "com.nguyendangkhoa25.condition.onclass.OnRequiredClass")
//Conditional by value
@ConditionalOnClass(value = {OnRequiredClass.class})
public class ConditionalOnClassConfiguration {
    @Bean
    public ConditionalOnClassService conditionalOnClassService() {
        return new ConditionalOnClassService();
    }
}
