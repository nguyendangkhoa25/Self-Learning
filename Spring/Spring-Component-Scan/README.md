# Spring Component Scanning
## 1. Khái quát
Khi làm vệc với Spring thì Spring cho phép chúng ta có nhiều cách để cài đặt beans trong Spring Container, ví dụ như là sử dụng XML configuration để mà khai báo(Cách này thường được sử dụng trong các legacy project), hoặc là sử dụng annotation _@Bean_ trong Java configuration class<br/>

Chúng ta cũng có thể sử dụng một annotaion phổ biến đó là _@ComponentScan_ để mà chỉ rõ package nào chúng ta muốn Spring scan bean <br/>
## 2. Sử dụng _@ComponentScan_ trong Spring Application
Để mà sử dụng _@ComponentScan_ trong một Spring application thì chúng ta phải sử dụng cùng với _@Configuration_ annotation để mà chỉ rõ packages nào mà chúng ta muốn Spring scan. Nếu sử dụng _@ComponentScan_ mà không khai báo arguments thì tức là Spring sẽ quét package hiện tại và tất cả các packages con của nó
Ví dụ: Chúng ta có một _@Configuration_ ở package com.nguyendangkhoa25.springapp như sau
```java
@Configuration
@ComponentScan
public class SpringComponentScanApp {
    private static ApplicationContext applicationContext;
    public static void main(String[] args) {
    applicationContext = new AnnotationConfigApplicationContext(SpringComponentScanApp.class);
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            System.out.printf("Bean: %s%n", beanName);
        }
    }
}
```
Tiếp theo, chúng ta tạo thêm 1 package là vehicles và 2 class là Bike.java và Car.java với _@Component_ annotation 
```java
package com.nguyendangkhoa25.springapp.vehicles;
//...
@Component
public class Car {
}
```
```java
package com.nguyendangkhoa25.springapp.vehicles;
//...
@Component
public class Bike {
}
```
Tạo thêm 1 package là animals và 1 @Component là Cat.java 
```java
package com.nguyendangkhoa25.springapp.animals;
//...
@Component
public class Cat {
}
```
Quay lại và run hàm main chúng ta sẽ thấy các bean được in ra như sau: 
```shell
...
Bean: springComponentScanApp
Bean: cat
Bean: bike
Bean: car
```
Thử thay thế _@ComponentScan_ bằng `@ComponentScan(basePackages = "com.nguyendangkhoa25.springapp.animals")`
```java
@Configuration
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
```
Sau đó chạy lại hàm main thì cho ra kết quả là chỉ có Cat được khai báo trong Spring Container
```shell
...
Bean: springComponentScanApp
Bean: cat
```
Như vậy, chỉ bằng cách sử dụng _@ComponentScan_ và định nghĩa khai báo các class với các @Bean annotations như là @Component thì 
Spring đã quét qua package hiện tại và các packages con để tìm các thành phần và thực hiện đăng ký vào Spring Container và chúng ta có thể sử dụng bất cứ đâu trong Application</br>
## 3. Sử dụng _@ComponentScan_ trong Spring Boot Application
Trong Spring Boot thì annotation _@SpringBootApplication_ đã bao gồm các annotations 
* _@Configuration_
* _@EnableAutoConfiguration_
* _@ComponentScan_

Ví dụ: Chúng ta có một _@SpringBootApplication_ ở package com.nguyendangkhoa25.springboot và các thành phần Car.java, Bike.java, Cat.java như sau
```java
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
```
```java
package com.nguyendangkhoa25.springboot.vehicles;
//...
@Component
public class Car {
}
```
```java
package com.nguyendangkhoa25.springboot.vehicles;
//...
@Component
public class Bike {
}
```
```java
package com.nguyendangkhoa25.springboot.animals;
//...
@Component
public class Cat {
}
```
Kết quả khi run hàm main của Spring Boot application
```shell
...
Bean: springComponentScanBootApp
Bean: cat
Bean: bike
Bean: car
```
Chú ý: Bởi vì trong Spring Boot application thì _@EnableAutoConfiguration_ đã tạo ra rất nhiều beans khác nhau 
## 4. Sử dụng _@ComponentScan_ exclusions
Đây là một cách để chúng ta bỏ qua các packages, class mà chúng ta không muốn Spring scan và cài đặt bean

Bỏ qua tất cả bean bên trong package: `com.nguyendangkhoa25.springboot.animals`
```java
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type= FilterType.REGEX,
                pattern="com\\.nguyendangkhoa25\\.Vehicle\\.animals\\..*")
)
```
Hoặc bỏ qua class Car.java 
```java
@ComponentScan(
        excludeFilters =
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Car.class)
)
```
## 5. Sử dụng _@ComponentScan_ Filters Type 
_@ComponentScan_ cho phép chúng ta filter để loại bỏ hoặc thêm vào các bean bằng cách sử dụng parameter includeFilters hoặc excludeFilters, Có 5 type được sử dụng như sau
* ANNOTATION
* ASSIGNABLE_TYPE
* ASPECTJ
* REGEX
* CUSTOM
### 5.1 FilterType.ANNOTATION
ANNOTATION filter type cho phép chúng ta loại include hoặc exclude các components được khai báo với annotation
Ví dụ: Chúng ta định nghĩa _@Vehicle_ annotation ở `com.nguyendangkhoa25.filters.annotation`
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Vehicle { }
```
Tiếp theo định nghĩa class Train.java và sử dụng _@Vehicle_
```java
package com.nguyendangkhoa25.filters.annotation.vehicles;

//...
@Vehicle
public class Train {
}
```
Cuối cùng là sử dụng FilterType.ANNOTATION để cho Spring scan _@Vehicle_ annotation
```java
@SpringBootApplication
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Vehicle.class))
public class SpringComponentScanAnnotationFilterApp {
    private static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(SpringComponentScanAnnotationFilterApp.class, args);
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .filter(bean -> !bean.contains("org.springframework")
                        && !bean.contains("springComponentScanAnnotationFilterApp"))
                .collect(Collectors.toList())
                .forEach(beanName -> System.out.printf("Bean: %s%n", beanName));
    }
}
```
### 5.2 FilterType.ASSIGNABLE_TYPE
ASSIGNABLE_TYPE cho phép filters tất cả class mà extend class khác hoặc implement interface được chỉ định trong quá trình scan
Ví dụ: Đầu tiên tạo một interface tên Vehicle.java ở package `com.nguyendangkhoa25.filters.assignable`
```java
package com.nguyendangkhoa25.filters.assignable;
public interface Vehicle {
}
```
Tiếp theo định nghĩa class Car.java và Train.java cùng implement Vehicle interface 
```java
package com.nguyendangkhoa25.filters.assignable.vehicles;
//...
public class Train implements Vehicle {
}
```
```java
package com.nguyendangkhoa25.filters.assignable.vehicles;
//...
public class Car implements Vehicle {
}
```
Cuối cùng chúng ta sử dụng ASSIGNABLE_TYPE để Spring include bean cho tất cả class implement Vehicle interface
```java
@SpringBootApplication
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = Vehicle.class))
public class SpringComponentScanAssignableFilterApp {
    private static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(SpringComponentScanAssignableFilterApp.class, args);
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .filter(bean -> !bean.contains("org.springframework")
                        && !bean.contains("springComponentScanAssignableFilterApp"))
                .collect(Collectors.toList())
                .forEach(beanName -> System.out.printf("Bean: %s%n", beanName));
    }
}
```
### 5.3 FilterType.REGEX
REGEX filter được sử dụng để kiểm tra nếu class name match với regex pattern đã định nghĩa 
### 5.4 FilterType.ASPECTJ
ASPECTJ filter được sử dụng để thêm vào hoặc loại bỏ các tập phức tạp của các class 
Ví dụ: Khi chúng ta muốn Spring scan và inject các beans trong package `com.nguyendangkhoa25.filters.aspectj.vehicles` ngoại trừ bean có tên bắt đầu bằng "C" trong package đó
```java
@SpringBootApplication
@ComponentScan(
        includeFilters = @ComponentScan.Filter(type = FilterType.ASPECTJ,
                pattern = "com.nguyendangkhoa25.filters.aspectj.vehicles.* && !com.nguyendangkhoa25.filters.aspectj.vehicles.C*"))
public class SpringComponentScanAspectJFilterApp {
    private static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(SpringComponentScanAspectJFilterApp.class, args);
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .filter(bean -> !bean.contains("org.springframework")
                        && !bean.contains("springComponentScanAspectJFilterApp"))
                .collect(Collectors.toList())
                .forEach(beanName -> System.out.printf("Bean: %s%n", beanName));
    }
}
```
### 5.5 FilterType.CUSTOM
CUSTOM filter được sử dụng khi mà các filter có sẵn ở trên không phù hợp với yêu cầu thì chúng ta sử dụng CUSTOM để tạo ra custom filter theo yêu cầu</br>
Ví dụ: Yêu cầu là chỉ inject những bean mà có name lớn hơn 2 ký tự và bắt đầu bằng chữ cái C thì:
Đầu tiên tạo một class _SpringComponentScanCustomFilter_ và implement interface `org.springframework.core.type.filter.TypeFilter`
```java
package com.nguyendangkhoa25.filters.custom;
//...
public class SpringComponentScanCustomFilter implements TypeFilter {
    @Override
    public boolean match(MetadataReader metadataReader,
                         MetadataReaderFactory metadataReaderFactory) {
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        String fullyQualifiedName = classMetadata.getClassName();
        String className = fullyQualifiedName.substring(fullyQualifiedName.lastIndexOf(".") + 1);
        return className.length() >= 3 && className.startsWith("C");
    }
}
```
Sau đó là sử dụng class filter đã tạo ở trên trong _@ComponentScan_
```java
@SpringBootApplication
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.CUSTOM,
        classes = SpringComponentScanCustomFilter.class))
public class SpringComponentScanCustomFilterApp {
    private static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(SpringComponentScanCustomFilterApp.class, args);
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .filter(bean -> !bean.contains("org.springframework")
                        && !bean.contains("springComponentScanCustomFilterApp"))
                .collect(Collectors.toList())
                .forEach(beanName -> System.out.printf("Bean: %s%n", beanName));
    }
}
```
Kết quả là chỉ có class "Car.java" được injected vào trong Spring Container
```shell
Bean: car
#...
```
## 6. Chú ý khi sử dụng _@Configuration_ annotation
Chúng ta không nên khai báo _@Configuration_ ở default package, vì khi khai báo như vậy 
Spring sẽ quét qua tất cả các gói jar trong classpath và có thể dẫn tới lỗi trùng tên bean trong các gói jar khác nhau và Application có thể không start được
## 7. Kết luận
Như vậy, có rất nhiều cách để làm việc với Spring Container trông qua _@ComponentScan_ 









