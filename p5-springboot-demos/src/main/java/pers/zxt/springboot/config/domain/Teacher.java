package pers.zxt.springboot.config.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("teacher")
public class Teacher {
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
