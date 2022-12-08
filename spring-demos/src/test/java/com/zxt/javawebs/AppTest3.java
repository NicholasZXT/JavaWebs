package com.zxt.javawebs;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.zxt.javawebs.di_annotation.Student;

/**
 * 基于注解的DI使用
 */
public class AppTest3 {
    @Test
    public void test01(){
        String config= "di_annotation/applicationContext.xml";
        ApplicationContext ctx  = new ClassPathXmlApplicationContext(config);
        // 获取学生
        Student student = (Student) ctx.getBean("myStudent");
        System.out.println("student："+student);
    }
}
