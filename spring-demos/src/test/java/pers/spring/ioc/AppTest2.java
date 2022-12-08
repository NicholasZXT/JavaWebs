package pers.spring.ioc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.Date;

import pers.spring.ioc.di_xml.Student;

/**
 * 基于XML配置文件的注入（DI）
 */
public class AppTest2 {

    /**
     * set注入方式
     */
    @Test
    public void test01(){
        String config= "di_xml/applicationContext.xml";
        ApplicationContext ctx  = new ClassPathXmlApplicationContext(config);

        // 获取学生
        Student student = (Student) ctx.getBean("myStudent");
        System.out.println("student："+student);

        // 对于非自定义类，只要有 set 方法就可以
        Date date = (Date) ctx.getBean("mydate");
        System.out.println("date: "+date);
    }

    /**
     * 构造注入方式
     */
    @Test
    public void test02(){
        String config= "di_xml/applicationContext.xml";
        ApplicationContext ctx  = new ClassPathXmlApplicationContext(config);

        // 获取构造注入创建的学生
        Student student = (Student) ctx.getBean("myStudent1");
        System.out.println("student1："+student);
        student = (Student) ctx.getBean("myStudent2");
        System.out.println("student2："+student);
        student = (Student) ctx.getBean("myStudent3");
        System.out.println("student3："+student);
    }

    /**
     * 引用类型自动注入
     */
    @Test
    public void test03(){
        String config= "di_xml/applicationContext.xml";
        ApplicationContext ctx  = new ClassPathXmlApplicationContext(config);

        // 获取引用类型按照 ByName 自动注入的学生
        Student student = (Student) ctx.getBean("myStudent4");
        System.out.println("student4："+student);
    }

    /**
     * 测试多个配置文件的使用
     */
    @Test
    public void test04(){
        // 只需要指定总的配置文件
        String config= "di_xml/applicationContext.xml";
        ApplicationContext ctx  = new ClassPathXmlApplicationContext(config);

        // di_xml/spring-student.xml 配置文件中导入的Student对象
        Student student = (Student) ctx.getBean("myStudent5");
        System.out.println("student4："+student);
    }

}
