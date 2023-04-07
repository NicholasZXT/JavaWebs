package pers.zxt.springmvc.hello;

public class Student {
    private String name;
    private Integer age;

    public Student() {
        System.out.println("===Student的无参数构造方法===");
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

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
