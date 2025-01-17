package com.nguyendangkhoa25.exclusions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type= FilterType.REGEX,
        pattern="com\\.nguyendangkhoa25\\.exclusions\\.animals\\..*")
)
public class SpringComponentScanExclusionsApp {
    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(SpringComponentScanExclusionsApp.class, args);
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            System.out.printf("Bean: %s%n", beanName);
        }
    }
}

