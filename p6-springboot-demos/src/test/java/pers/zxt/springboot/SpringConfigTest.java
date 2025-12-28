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
     * 显示Spring容器中所有 Bean
     * @param ctx
     */
    public void showSpringContextBeans(ApplicationContext ctx){
        // 1. 获取所有 Bean 的名称数组
        String[] allBeanNames = ctx.getBeanDefinitionNames();
        // 2. 遍历打印所有 Bean
        System.out.println("===== Spring 容器中所有 Bean 列表（共 " + allBeanNames.length + " 个） =====");
        for (String beanName : allBeanNames) {
            // 获取 Bean 实例（可选，按需获取）
            Object beanInstance = ctx.getBean(beanName);
            // 打印 Bean 名称 + Bean 类型
            System.out.printf("Bean 名称：%-15s | Bean 类型：%s%n", beanName, beanInstance.getClass().getName());
        }
    }

    /**
     * 显示Spring容器中指定包下的所有 Bean
     * @param applicationContext
     * @param packageName
     */
    public void showSpringContextBeansOfUserDefined(ApplicationContext applicationContext, String packageName){
        // 1. 获取所有 Bean 的名称数组
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        System.out.println("===== 用户自定义 Bean 列表（按包名前缀过滤） =====");
        int userBeanCount = 0;
        for (String beanName : allBeanNames) {
            Object beanInstance = applicationContext.getBean(beanName);
            String beanClassName = beanInstance.getClass().getName();
            // 2. 判断该 Bean 的类名是否以用户包名前缀开头
            if (beanClassName.startsWith(packageName)) {
                userBeanCount++;
                System.out.printf("Bean 名称：%-15s | Bean 类型：%s%n", beanName, beanClassName);
            }
        }
        System.out.println("===== 用户自定义 Bean 总数：" + userBeanCount + " 个 =====");
    }

    /**
     * 使用xml作为容器配置文件
     */
    @Test
    public void testXmlBean(){
        String config= "spring-config/beans.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        Student student = (Student) ctx.getBean("myStudent");
        System.out.println("容器（xml配置）中的对象：" + student);
        showSpringContextBeansOfUserDefined(ctx, "pers.zxt.springboot.config.domain");
    }

    /**
     * 使用 JavaConfig 作为spring容器配置文件
     */
    @Test
    public void testJavaConfigBean(){
        ApplicationContext ctx  = new AnnotationConfigApplicationContext(SpringConfig.class);
        Teacher teacher = (Teacher) ctx.getBean("teacher");
        System.out.println("JavaConfig -> ComponentScan Bean: " + teacher);
        Student student1 = (Student) ctx.getBean("createStudent");
        System.out.println("JavaConfig -> @Bean: " + student1);
        Student student2 = (Student) ctx.getBean("myStudent3");
        System.out.println("JavaConfig -> @Bean（自定义bean名称）：" + student2);
        showSpringContextBeansOfUserDefined(ctx, "pers.zxt.springboot.config.domain");
    }

}
