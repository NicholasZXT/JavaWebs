package pers.zxt.springboot;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.zxt.springboot.config.SpringConfig;
import pers.zxt.springboot.config.domain.*;

/**
 * 测试作为配置的SpringConfig使用
 */
public class SpringConfigTest {
    static {
        // 通过这种方式设置logback和log4j的配置文件，因为这两个文件不在标准路径（src/main/resources）下
        // 等价于启动时使用下面两种方式：
        //   -Dlogback.configurationFile=src/main/resources/spring-config/logback.xml
        //   -Dlog4j.configurationFile=src/main/resources/spring-config/log4j2.xml
        System.setProperty("logback.configurationFile", "src/main/resources/spring-config/logback.xml");
        System.setProperty("log4j.configurationFile", "src/main/resources/spring-config/log4j2.xml");
    }

    /**
     * 使用xml作为容器配置文件
     */
    @Test
    public void test01(){
        String config= "spring-config/beans.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        Student student = (Student) ctx.getBean("myStudent");
        System.out.println("容器（xml配置）中的对象：" + student);
    }

    /**
     * 使用 JavaConfig 作为spring容器配置文件
     */
    @Test
    public void test02(){
        ApplicationContext ctx  = new AnnotationConfigApplicationContext(SpringConfig.class);
        Student student = (Student) ctx.getBean("createStudent");
        System.out.println("使用JavaConfig创建的bean对象：" + student);
    }

    @Test
    public void test03(){
        ApplicationContext ctx  = new AnnotationConfigApplicationContext(SpringConfig.class);
        Student student = (Student) ctx.getBean("myStudent3");
        System.out.println("使用JavaConfig创建的bean对象：" + student);
    }

    @Test
    public void test05(){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        Teacher teacher = (Teacher) ctx.getBean("teacher");
        System.out.println("teacher==" + teacher);
    }
}
