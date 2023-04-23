package pers.zxt.springboot;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.zxt.springboot.hello.SpringConfig;
import pers.zxt.springboot.hello.domain.*;

/**
 * 测试作为配置的SpringConfig使用
 */
public class SpringConfigTest
{
    /**
     * 使用xml作为容器配置文件
     */
    @Test
    public void test01(){
        String config="springconf/beans.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        Student student = (Student) ctx.getBean("myStudent");
        System.out.println("容器（xml配置）中的对象："+student);
    }

    /**
     * 使用JavaConfig
     */
    @Test
    public void test02(){
        ApplicationContext ctx  = new AnnotationConfigApplicationContext(SpringConfig.class);
        Student student = (Student) ctx.getBean("createStudent");
        System.out.println("使用JavaConfig创建的bean对象："+student);
    }

    @Test
    public void test03(){
        ApplicationContext ctx  = new AnnotationConfigApplicationContext(SpringConfig.class);
        Student student = (Student) ctx.getBean("myStudent3");
        System.out.println("使用JavaConfig创建的bean对象："+student);
    }

    @Test
    public void test05(){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        Teacher teacher = (Teacher) ctx.getBean("teacher");
        System.out.println("teacher==" + teacher);
    }
}
