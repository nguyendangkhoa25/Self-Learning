package com.nguyendangkhoa25.springapp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ComponentScan
@ComponentScan(basePackages = "com.nguyendangkhoa25.springapp.animals")
public class SpringComponentScanApp {
    private static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext =
                new AnnotationConfigApplicationContext(SpringComponentScanApp.class);
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            System.out.printf("Bean: %s%n", beanName);
        }
    }
}
