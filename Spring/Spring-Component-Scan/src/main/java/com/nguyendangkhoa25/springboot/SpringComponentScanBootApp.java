package com.nguyendangkhoa25.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringComponentScanBootApp {
	private static ApplicationContext applicationContext;

	public static void main(String[] args) {
		applicationContext = SpringApplication.run(SpringComponentScanBootApp.class, args);
		for (String beanName : applicationContext.getBeanDefinitionNames()) {
			System.out.printf("Bean: %s%n", beanName);
		}
	}
}
