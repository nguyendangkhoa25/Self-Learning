package com.nguyendangkhoa25.condition.onweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringBootConditionalOnWebApp {
    private static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(SpringBootConditionalOnWebApp.class, args);
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .filter(bean -> !bean.contains("org.springframework")
                        && !bean.contains("springBootConditionalOnWebApp"))
                .collect(Collectors.toList())
                .forEach(beanName -> System.out.printf("Bean: %s%n", beanName));
    }
}
