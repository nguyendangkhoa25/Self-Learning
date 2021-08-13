# Spring Boot Annotation
## 1. Khái quát
Khi sử dụng Spring Boot để phát triển một ứng dụng thì chúng ta đã tiết kiệm được khá nhiều thời gian cho việc "configuring" thông qua việc sử dụng tính năng "auto-configure" được cung cấp sẵn trong Spring Boot</br>
Trong bài viết này chúng ta sẽ tìm hiểu về các annotations trong Spring packages: `org.springframework.boot.autoconfigure và org.springframework.boot.autoconfigure.condition`
## 2. Sử dụng _@SpringBootApplication_ annotation
Chúng ta sử dụng annotation _@SpringBootApplication_ để khai báo main class là một Spring Boot Application
```java
//...
@SpringBootApplication
public class SpringBootAnnotationApp {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootAnnotationApp.class, args);
	}
}
```
Trong Spring Boot thì annotation _@SpringBootApplication_ đã bao gồm các annotations với các thành phần default
* _@Configuration_
* _@EnableAutoConfiguration_
* _@ComponentScan_
## 3. Annotation _@EnableAutoConfiguration_
Annotation _@EnableAutoConfiguration_ này cho phép Spring tìm kiếm "auto-configuration" beans ở classpath và tự động apply vào Spring Container
Lưu ý: _@EnableAutoConfiguration_ annotation phải được sử dụng cùng với _@Configuration_
```java
//...
@Configuration
@EnableAutoConfiguration
public class PizzaFactoryConfig {
    public Baker baker(){
        return new Baker();
    }
}
```
## 4. Auto-Configuration conditions
### 1. @ConditionalOnClass và @ConditionalOnMissingClass
### 2. @ConditionalOnBean và @ConditionalOnMissingBean
### 3. @ConditionalOnProperty
### 4. @ConditionalOnResource
### 5. @ConditionalOnWebApplication và @ConditionalOnNotWebApplication
### 6. @ConditionalExpression
### 7. @Conditional
## 5. @AutoConfigureBefore và @AutoConfigureAfter
## 6. @AutoConfigureOrder
## 7. Kết luận








