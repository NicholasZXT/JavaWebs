package pers.zxt.mybatis.domain;

/**
 * ORM里对应于数据库的实体类
 */
public class Student {
    //属性名和数据库中的列名一样
    private Integer id;
    private String name;
    private Integer age;
    private String grade;

    // 各个属性的get和set方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
    @Override
    public String toString() {
        return " Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
