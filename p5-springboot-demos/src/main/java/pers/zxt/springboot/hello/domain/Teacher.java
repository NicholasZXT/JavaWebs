package pers.zxt.springboot.hello.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// 普通的 Java Bean 对象，
// 但是使用了 Component 注解，配合 @ComponentScan(basePackages= "pers.zxt.springboot.hello.domain") 使用，实现自动导入
@Component("teacher")
public class Teacher {
    // @Value 配合 @PropertySource(value="classpath:springconf/config.properties") 使用，可以从 properties 中引入属性值
    @Value("${teacher.name}")
    private String name;
    @Value("${teacher.age}")
    private Integer age;

    @Override
    public String toString() {
        return "Teacher {" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
