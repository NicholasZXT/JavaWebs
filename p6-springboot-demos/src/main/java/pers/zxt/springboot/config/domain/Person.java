package pers.zxt.springboot.config.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Java Bean对象，在 spring-config/applicationContext.xml 中被引入
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private String cardId;
    private String name;
    private Integer age;

    @Override
    public String toString() {
        return "Person {" +
                "cardId='" + cardId + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
