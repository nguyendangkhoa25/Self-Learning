package com.nguyendangkhoa25.condition.expression;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringBootConditionalOnExpressionApp {
    private static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(SpringBootConditionalOnExpressionApp.class, args);
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .filter(bean -> !bean.contains("org.springframework")
                        && !bean.contains("springBootConditionalOnExpression"))
                .collect(Collectors.toList())
                .forEach(beanName -> System.out.printf("Bean: %s%n", beanName));
    }
}
