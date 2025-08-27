package pers.zxt.java.reflection;

import java.io.Serializable;

/**
 提供Java bean, 用于之后的 ReflectionDemo 类中练习反射操作。
    Javabean规范：
    1. 提供私有字段
    2. 必须提供getter和setter方法
    3. 提供无参构造方法
    4. 必须实现序列化接口
 */
public class Bean implements Serializable {
    //这些字段必须是私有的
    private String id;
    private String className;
    public String description;

    //无参构造方法
    public Bean() {
        System.out.println("无参构造方法");
    }

    //一个参数的构造方法
    public Bean(String id) {
        this.id = id;
    }

    //两个参数的构造方法，公有构造方法
    public Bean(String id, String className) {
        this.id = id;
        this.className = className;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "id='" + id + '\'' +
                ", className='" + className + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public static void main(String[] args) {
        System.out.println("main 方法");
    }
}
