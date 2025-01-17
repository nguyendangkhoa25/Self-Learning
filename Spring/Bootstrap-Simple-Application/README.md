# How to create a simple application by using Spring Boot
## 1. Khái quát
Spring Boot là một lựa chọn bổ sung trong nền tảng Spring, nó tập trung vào việc hỗ trợ phát triển một ứng dụng một cách nhanh nhất có thể thông qua việc hỗ trợ configuration tự động<br/>
Trong bài này chúng ta cùng tạo một ứng web bằng cách sử dụng Spring Boot
## 2. Bắt đầu
Đầu tiên chúng ta tạo project bằng cách sử dụng [Spring Initializr](https://start.spring.io) để generage Spring Boot application</br>
Một số dependencies cần thiết cho ứng dụng web với H2 database như dưới đây
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
</dependency>
```
Lưu ý rằng các dependencies ở trên chúng ta không cần phải khai báo version bởi vì chúng được quản lý bởi _spring-boot-starter-parent_, chúng ta vẫn có thể override các version này trong trường hợp cần thiết
```xml
  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.5.3</version>
  </parent>
```
## 3. Application Configuration
Đây là main class của application 
```java
@SpringBootApplication
public class BootstrapSimpleApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootstrapSimpleApplication.class, args);
    }
}
```
Lưu ý rằng chúng ta sử dụng annotation _@SpringBootApplication_ để định nghĩa một Spring Boot application, Annotation này bao gồm 3 annotations là _@Configuration_, _@EnableAutoConfiguration_, _@ComponentScan_. </br>
Cuối cùng chúng ta định nghĩa một configuration file đơn giản application.properties trong resources folder. Và thay đổi server port của application thành 9090 thay vì mặc định 8080
```properties
server.port=9090
```
## 4. MVC View
Ở phía frontend thì hiện nay các dự án thường sử dụng các frontend lib nổi tiếng như ReactJs, AngularJs ... tuy nhiên trong ví dụ này chúng ta sử dụng Thymeleaf<br/>
Thêm Thymeleaf dependency vào pom file và nó sẽ được enable mà không cần configure gì thêm
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```
Định nghĩa thêm một số Thymeleaf properties vào application.properties file như sau 
```properties
spring.thymeleaf.cache=false
spring.thymeleaf.enabled=true 
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
```
Tiếp theo tạo một _HelloController_ đơn giản bên trong package controller và một method sẽ return về welcome view
```java
@Controller
public class HelloController {
    @Value("${spring.application.developer.name}")
    private String yourName;
    @GetMapping("/")
    public String welcomeMessage(Model model) {
        model.addAttribute("yourName", yourName);
        return "welcome";
    }
}
```
Cuối cùng tạo view _welcome.html_ bên trong templates folder để in ra tên của developer
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
</head>
<body>
<h1>Hello !</h1>
<p>Hello: <span th:text="${yourName}">, You're doing great!</span></p>
</body>
</html>
```
Chạy main class của application, mở browser và truy cập vào link: http://localhost:9090 để kiểm tra







## 2. Kết luận









1