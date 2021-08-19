# Spring Boot Annotations
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
Khi chúng ta khởi tạo Spring beans, thì chúng ta có thể thêm vào các điều kiện bằng cách sử dụng các Conditional Annotations, Các bean chỉ được load vào Application Context khi mà các điều kiện chúng ta đặt ra được thoả mãn
### 1. @ConditionalOnClass và @ConditionalOnMissingClass
_@ConditionalOnClass_ cho phép chúng ta chỉ định để Spring tự động cấu hình các beans nếu class tồn tại trong classpath,
Ví dụ: Class _ConditionalOnClassConfiguration_ và bean _ConditionalOnClassService_ sẽ chỉ được load nếu class _OnRequiredClass_ tồn tại trong classpath, và config _ConditionalOnClass_ by value như sau
```java
package com.nguyendangkhoa25.condition.onclass;
//...
@Configuration
@ConditionalOnClass(value = {OnRequiredClass.class})
public class ConditionalOnClassConfiguration {
    @Bean
    public ConditionalOnClassService conditionalOnClassService() {
        return new ConditionalOnClassService();
    }
}
```
Hoặc config by name như sau
```java
package com.nguyendangkhoa25.condition.onclass;
//...
@Configuration
@ConditionalOnClass(name = "OnRequiredClass")
public class ConditionalOnClassConfiguration {
    @Bean
    public ConditionalOnClassService conditionalOnClassService() {
        return new ConditionalOnClassService();
    }
}
```
Tiếp theo tạo class _OnRequiredClass_  để condition trên được thoả mãn
```java
package com.nguyendangkhoa25.condition.onclass;
public class OnRequiredClass {
}
```
Và main class cho Spring Boot Application và chạy để kiểm tra kết quả
```java
package com.nguyendangkhoa25.condition.onclass;
//...
@SpringBootApplication
public class SpringBootOnClassConditionalApp {
    private static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(SpringBootOnClassConditionalApp.class, args);
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .filter(bean -> !bean.contains("org.springframework")
                        && !bean.contains("springBootOnClassConditionalApp"))
                .collect(Collectors.toList())
                .forEach(beanName -> System.out.printf("Bean: %s%n", beanName));
    }
}
```
Trường hợp ngược lại, sử dụng _@ConditionalOnMissingClass_ để cho phép Spring inject beans khi mà class không tồn tại trong classpath
Ví dụ: Chúng ta sẽ làm tương tự như ví dụ _@ConditionalOnClass_ ở trên nhưng sẽ không tạo class _OnRequiredClass_ như dưới đây
```java
package com.nguyendangkhoa25.condition.onmissingclass;
//...
@Configuration
@ConditionalOnMissingClass(value = "com.nguyendangkhoa25.condition.onmissingclass.OnRequiredClass")
public class ConditionalOnMissingClassConfiguration {
}
```
Main class với _@SpringBootApplication_ annotation để kiểm tra bean được tạo
```java
package com.nguyendangkhoa25.condition.onmissingclass;
//...
@SpringBootApplication
public class SpringBootOnMissingClassConditionalApp {
    private static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(SpringBootOnMissingClassConditionalApp.class, args);
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .filter(bean -> !bean.contains("org.springframework")
                        && !bean.contains("springBootOnMissingClassConditionalApp"))
                .collect(Collectors.toList())
                .forEach(beanName -> System.out.printf("Bean: %s%n", beanName));
    }
}
```
Tiếp theo chúng ta sẽ đến với 2 conditional annotations: _@ConditionalOnBean_ và _@ConditionalOnMissingBean_
### 2. @ConditionalOnBean và @ConditionalOnMissingBean
Annotations _@ConditionalOnBean_ và _@ConditionalOnMissingBean_ được sử dụng để cài đặt Spring nếu một hoặc nhiều Beans khác tồn tại(_@ConditionalOnBean_) hoặc không tồn tại(_@ConditionalOnMissingBean_) trong classpath</br>
Chúng ta có thể filter bằng name, type, value hoặc search để cài đặt như ví dụ sau đây.
Đầu tiên tạo 1 bean _RequiredBean_ và sử dụng _@Component_ để autowire 
```java
package com.nguyendangkhoa25.condition.onbean;
//...
@Component
public class RequiredBean {
}
```
Tiếp theo ở class _SpringBootOnBeanConfig_ chúng ta sử dụng _@ConditionalOnBean_ để cài đặt bean "springBootOnBeanService" nếu bean "requiredBean" tồn tại như sau
```java
package com.nguyendangkhoa25.condition.onbean;
//..
@Configuration
public class SpringBootOnBeanConfig {
    @Bean
    @ConditionalOnBean(value = RequiredBean.class)
    public SpringBootOnBeanService springBootOnBeanService() {
        return new SpringBootOnBeanService();
    }
}
```
Hoặc là sử dụng _@ConditionalOnMissingBean_ để cài đặt bean "springBootOnMissingBeanService" trong trường hợp "requiredBean" không tồn tại
```java
package com.nguyendangkhoa25.condition.onbean;
//...
@Configuration
public class SpringBootOnBeanConfig {
    @Bean
    @ConditionalOnMissingBean(name = "requiredBean")
    public SpringBootOnMissingBeanService springBootOnMissingBeanService() {
        return new SpringBootOnMissingBeanService();
    }
}
```
Main class để kiểm tra kết quả 
```java
package com.nguyendangkhoa25.condition.onbean;
//...
@SpringBootApplication
public class SpringBootOnBeanApp {
    private static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(SpringBootOnBeanApp.class, args);
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .filter(bean -> !bean.contains("org.springframework")
                        && !bean.contains("springBootOnBeanApp"))
                .collect(Collectors.toList())
                .forEach(beanName -> System.out.printf("Bean: %s%n", beanName));
    }
}
```
### 3. @ConditionalOnProperty
Sử dụng annotation này để cài đặt Spring Bean dựa vào một property nào đó</br>
Ví dụ: Bean "springBootOnPropertyService" sẽ được Spring cài đặt dựa vào property "on.property.enabled" trong application.properties file
```java
package com.nguyendangkhoa25.condition.onproperty;
//...
@Configuration
public class SpringBootOnPropertyConfig {
    @Bean
    @ConditionalOnProperty(prefix = "on.property", name = "enabled")
    public SpringBootOnPropertyService springBootOnPropertyService() {
        return new SpringBootOnPropertyService();
    }
}
```
Hoặc Bean "springBootOnPropertyValueService" sẽ chỉ được cài đặt nếu property "on.property.enabled" có value là "string-value" như dưới đây:
```java
package com.nguyendangkhoa25.condition.onproperty;
//...
@Configuration
public class SpringBootOnPropertyConfig {
    @Bean
    @ConditionalOnProperty(prefix = "on.property", name = "enabled", havingValue = "string-value")
    public SpringBootOnPropertyValueService springBootOnPropertyValueService() {
        return new SpringBootOnPropertyValueService();
    }
}
```
Main class để kiểm tra kết quả
```java
package com.nguyendangkhoa25.condition.onproperty;
//...
@SpringBootApplication
public class SpringBootOnPropertyApp {
    private static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(SpringBootOnPropertyApp.class, args);
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .filter(bean -> !bean.contains("org.springframework")
                        && !bean.contains("springBootOnPropertyApp"))
                .collect(Collectors.toList())
                .forEach(beanName -> System.out.printf("Bean: %s%n", beanName));
    }
}
```
Chú ý: Trong trường hợp property có value dạng true/false thì nếu set value là "false" thì Spring sẽ không load bean nếu không chỉ rõ value cụ thể
### 4. @ConditionalOnResource
Annotation này được sử dụng để cài đặt bean dựa vào sự tồn tại của 1 resource trong classpath
Ví dụ: Chúng ta sẽ chỉ cài đặt bean Log4jService trong trường hợp classpath có tồn tại file log4j.properties như sau
```java
package com.nguyendangkhoa25.condition.onresource;
//...
@Configuration
public class SpringBootOnResourceConfig {
    @Bean
    @ConditionalOnResource(resources = { "log4j.properties" })
    public Log4jService log4jService() {
        return new Log4jService();
    }
}
```
Main class để kiểm tra kết quả 
```java
package com.nguyendangkhoa25.condition.onresource;
//...
@SpringBootApplication
public class SpringBootOnResourceApp {
    private static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(SpringBootOnResourceApp.class, args);
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .filter(bean -> bean.contains("log4jService")).collect(Collectors.toList())
                .forEach(beanName -> System.out.printf("Bean: %s loaded", beanName));
    }
}
```
### 5. @ConditionalOnWebApplication và @ConditionalOnNotWebApplication
2 Annotations _@ConditionalOnWebApplication_ và _@ConditionalOnNotWebApplication_ cho phép Spring cài đặt bean trong trường hợp application là một web-application(_@ConditionalOnWebApplication_) hoặc không phải là một web-application(_@ConditionalOnNotWebApplication_)</br>
Một web application là một application bất kỳ có sử dụng một Spring _WebApplicationContext_, định nghĩa một session scope hoặc có một _StandardServletEnvironment_
Xem xét ví dụ dưới đây để thấy _@ConditionalOnWebApplication_ và _@ConditionalOnNotWebApplication_ hoạt động như thế nào thông qua việc cài đặt bean WebModule </br>
```java
package com.nguyendangkhoa25.condition.onweb;
public class WebModule {
}
```
Tạo một configuration class như sau:
```java
package com.nguyendangkhoa25.condition.onweb;
//...
@Configuration
public class SpringBootConditionalOnWebConfig {
    @Bean
    @ConditionalOnWebApplication
    public WebModule webModule() {
        return new WebModule();
    }
}
```
Thêm vào dependency spring-boot-starter-web
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
Cuối cùng là main class để kiểm tra kết quả: 
```java
package com.nguyendangkhoa25.condition.onweb;
//...
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
```
Tương tự cho annotation _@ConditionalOnNotWebApplication_  
```java
package com.nguyendangkhoa25.condition.onweb;
//...
@Configuration
public class SpringBootConditionalOnWebConfig {
    @Bean
    @ConditionalOnNotWebApplication
    public NotWebModule notWebModule() {
        return new NotWebModule();
    }
}
```
Thì bean "notWebModule" sẽ chỉ được cài đặt vào application context nếu chúng ta delete dependency spring-boot-starter-web
### 6. @ConditionalExpression
Annotation _@ConditionalExpression_ được sử dụng khi chúng ta muốn cài đặt bean/configuration dựa trên kết quả của một SpEL(Spring Expression Language) expresstion</br>
Cùng tìm hiểu về _@ConditionalExpression_ qua ví dụ sau:
Đầu tiên tạo class ExpressionModule, class này được sử dụng để khởi tạo bean khi run application
```java
package com.nguyendangkhoa25.condition.expression;
public class ExpressionModule {
}
```
Chúng ta sẽ chỉ tạo bean "expressionModule" khi property "on.expression.enabled" có giá trị "true"
```java
package com.nguyendangkhoa25.condition.expression;
//...
@Configuration
public class SpringBootConditionalOnExpressionConfig {
    @Bean
    @ConditionalOnExpression(value = "${on.expression.enabled:true}")
    public ExpressionModule expressionModule() {
        return new ExpressionModule();
    }
}
```
File application.properties khai báo properties ở trên 
```text
on.expression.enabled=true
```
Và main class để kiểm tra
```java
package com.nguyendangkhoa25.condition.expression;
//...
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
```
Khi chạy main class chúng ta sẽ thấy "expressionModule" được in ra trong console. Bây giờ nếu ta thay đổi giá trị của property thành false thì bean sẽ không được cài đặt vào application context
```text
on.expression.enabled=false
```
### 7. @Conditional
## 5. @AutoConfigureBefore và @AutoConfigureAfter
## 6. @AutoConfigureOrder
## 7. Kết luận








