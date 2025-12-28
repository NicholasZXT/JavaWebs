package pers.zxt.springboot.config;

import org.springframework.context.annotation.*;
import pers.zxt.springboot.config.domain.Student;

/**
 * @Configuration: 表示当前类是作为spring容器的配置文件使用的，相当于 bean.xml
 * @ImportResource: 表示引入 xml 定义的spring配置文件，导入其中定义的bean对象
 * @PropertySource: 表示引入 properties 配置
 * @ComponentScan: 表示spring容器的包扫描路径，默认下是 声明该注解的配置类所在的包（package）及其所有子包。
 * 下面的配置中 引入 了如下的 bean 对象：
 *   1. spring-config/applicationContext.xml 中定义的 person
 *   2. spring-config/beans.xml 中定义的 myStudent
 *   3. 通过 @ComponentScan 扫描到的 pers.zxt.springboot.config.domain.Teacher 对象，使用注解的方式定义，
 *      并且从 spring-config/config.properties 中获取值
 */
@Configuration
@ImportResource(value={"classpath:spring-config/applicationContext.xml", "classpath:spring-config/beans.xml"})
@PropertySource(value= "classpath:spring-config/config.properties")
@ComponentScan(basePackages= "pers.zxt.springboot.config.domain")   // 指定了待扫描的package路径
public class SpringConfig
{
    public static void main(String[] args) {
        System.out.println("Hello SpringConfig!");
    }

    /**
     * 创建方法，方法的返回值是对象。在方法的上面加入 @Bean 注解，方法的返回值对象就注入到容器中。
     * @Bean: 把对象注入到spring容器中，作用相当于 <bean>
     *   位置：方法的上面
     *   说明：@Bean 注解不指定对象的名称时，则默认 bean id 为方法名，可以使用 name 属性来指定 bean对象的名称
     */
    @Bean
    public Student createStudent() {
        Student s1 = new Student();
        s1.setName("李四");
        s1.setAge(26);
        s1.setSex("男");
        return s1;
    }

    @Bean(name="myStudent3")
    public Student anotherStudent() {
        Student s2 = new Student();
        s2.setName("小红");
        s2.setAge(26);
        s2.setSex("女");
        return s2;
    }
}
