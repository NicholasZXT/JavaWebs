package pers.zxt.mybatis.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * ORM里对应于数据库的实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    //属性名和数据库中的列名一样
    private Integer id;

    private String name;

    private Integer age;

    private String grade;
}
