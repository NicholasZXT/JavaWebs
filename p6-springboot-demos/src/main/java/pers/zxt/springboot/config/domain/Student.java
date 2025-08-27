package pers.zxt.springboot.config.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Java Bean 对象，在 spring-config/beans.xml 中被引入
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private String name;
    private Integer age;
    private String sex;

    @Override
    public String toString() {
        return "Student {" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }
}