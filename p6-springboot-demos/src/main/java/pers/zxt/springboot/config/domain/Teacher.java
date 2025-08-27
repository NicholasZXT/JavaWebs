package pers.zxt.springboot.config.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Java Bean 对象
 * 使用了 Component 注解，配合 @ComponentScan(basePackages= "pers.zxt.springboot.config.domain") 使用，实现自动导入
 */
@Component("teacher")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    // @Value 配合 @PropertySource(value="classpath:spring-config/config.properties") 使用，可以从 properties 中引入属性值
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
