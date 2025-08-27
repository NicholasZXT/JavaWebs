package pers.zxt.spring.ioc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.zxt.spring.ioc.di_annotation.Student;

/**
 * 基于注解的DI使用
 */
public class IocAnnoTest {
    @Test
    public void test01(){
        String config = "ioc/di_annotation/applicationContext.xml";
        ApplicationContext ctx  = new ClassPathXmlApplicationContext(config);
        // 获取学生
        Student student = (Student) ctx.getBean("myStudent");
        System.out.println("student："+student);
    }
}
