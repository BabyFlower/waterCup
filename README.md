# waterCup
A system which has a web and a server

## 框架结构 
+ sping boot 1.4.0
+ Maven 4.0.0
+ ORM(Hibernate)
+ mySQL

### 数据库相关操作
1. maven中添加Spring-data-jpa , mysql-connector-java依赖
>         <dependency
>            <groupId>org.springframework.boot</groupId>
>            <artifactId>spring-boot-starter-data-jpa</artifactId>
>       </dependency>
>        <dependency>
>            <groupId>mysql</groupId>
>            <artifactId>mysql-connector-java</artifactId>
>            <version>5.1.40</version>
>        </dependency>

2. 在.properties中添加相关配置
>     spring.datasource.url=jdbc:mysql://localhost:3306/test
>     spring.datasource.username=root
>     spring.datasource.password=root
>     spring.datasource.driver-class-name=com.mysql.jdbc.Driver
>     spring.jpa.properties.hibernate.hbm2ddl.auto=create-drop
>     spring.jpa.database=MYSQL
>     # 显示后台处理的SQL语句
>     spring.jpa.show-sql=true

spring.jpa.properties.hibernate.hbm2ddl.auto是hibernate的配置属性，其主要作用是：自动创建、更新、验证数据库表结构。
+ **create：** 每次加载hibernate时都会删除上一次的生成的表，然后根据你的model类再重新来生成新表，
哪怕两次没有任何改变也要这样执行，这就是导致数据库表数据丢失的一个重要原因。
+ **create-drop：** 每次加载hibernate时根据model类生成表，但是sessionFactory一关闭,表就自动删除。
+ **update：** 最常用的属性，第一次加载hibernate时根据model类会自动建立起表的结构（前提是先建立好数据库），
以后加载hibernate时根据model类自动更新表结构，即使表结构改变了但表中的行仍然存在不会删除以前的行。
要注意的是当部署到服务器后，表结构是不会被马上建立起来的，是要等应用第一次运行起来后才会。
+ **validate：** 每次加载hibernate时，验证创建数据库表结构，只会和数据库中的表进行比较，不会创建新表，但是会插入新值。
3. 创建实体类
```java
@Entity
@Table(name = "t_card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String deviceid;
    private String uid;
    private String cid;
    private Float charge;
    private Float rest;
    private String paystate;
    @OneToMany(mappedBy = "card")
    private List<Account> accounts;
}
```
4. 创建数据访问接口
```java
public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);

    User findByNameAndAge(String name, Integer age);

    @Query("from User u where u.name=:name")
    User findUser(@Param("name") String name);

}
```
通过Query语句，提供findUser函数，来操作数据库。Param传递参数。

### 网页模板引擎thymeleaf
1. maven添加thymeleaf依赖
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-java8time</artifactId>
            <version>3.0.0.RELEASE</version>
        </dependency>
```
2. 添加配置
```properties
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.cache=false
spring.thymeleaf.content-type=text/html
spring.thymeleaf.mode =LEGACYHTML5
```
3. HTML中引用thymeleaf
```html
<html
        lang="zh-CN"
        xmlns="http://www.w3.org/1999/xhtml"
        xmlns:th="http://www.thymeleaf.org"
        xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout">
```
4. 使用案例
+ **服务端：** 
```java
public String addusers(Model model,HttpServletRequest request) {
    model.addAttribute("errMg", errMg);
    return "/pages/adduser";
}
```
+ **web端：**
```html
th:name="${session.login_user.getUsername()}" th:text="${session.login_user.getUsername()}
```
