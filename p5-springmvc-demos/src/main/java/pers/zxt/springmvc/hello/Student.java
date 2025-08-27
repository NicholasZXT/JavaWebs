package pers.zxt.springmvc.hello;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {
    private String name;
    private Integer age;

    public Student() {
        System.out.println("===Student的无参数构造方法===");
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
