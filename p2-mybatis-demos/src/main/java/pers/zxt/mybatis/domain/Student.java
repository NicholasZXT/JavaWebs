package pers.zxt.mybatis.domain;

/**
 * ORM里对应于数据库的实体类
 */
public class Student {
    //属性名和数据库中的列名一样
    private Integer id;
    private String name;
    private String email;
    private Integer age;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return " 学生实体的信息{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
