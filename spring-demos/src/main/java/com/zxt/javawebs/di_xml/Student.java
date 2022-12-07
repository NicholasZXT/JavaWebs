package com.zxt.javawebs.di_xml;

public class Student {

    private String name;
    private int age;
    //引用类型
    private School school;


    public Student() {
        //System.out.println("Student无参数构造方法");
    }

    //有参数构造方法，可以用于spring构造注入
    public Student(String myname,int myage, School mySchool){
        //System.out.println("Student有参数构造方法");
        //给属性依次赋值
        this.name = myname;
        this.age = myage;
        this.school = mySchool;
    }


    // 以下 set 方法会被spring框架使用
    public void setName(String name) {
        //System.out.println("setName: "+name);
        this.name = name;
    }

    public void setAge(int age) {
        //System.out.println("setAge: "+age);
        this.age = age;
    }

    public void setSchool(School school) {
        //System.out.println("setSchool: "+school);
        this.school = school;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", school=" + school +
                '}';
    }
}