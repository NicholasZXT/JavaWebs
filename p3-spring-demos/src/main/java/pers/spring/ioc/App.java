package pers.spring.ioc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.spring.ioc.service.SomeService;

/**
 * Hello world!
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        // 正常使用 service 的流程
        //SomeService service = new SomeServiceImpl();
        //service.doSome();

        // 通过spring容器来获取对象
        // 1.指定spring配置文件: 从类路径（classpath）之下开始的路径
        String config= "ioc/beans.xml";
        // 2.创建容器对象， ApplicationContext 表示spring容器对象，后续通过ctx获取某个java对象
        // 这里创建容器对象的时候，就会在将 beans.xml 配置中定义的所有对象都创建出来（同一个类可以创建多个对象）
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        // 3.从容器中获取指定名称的对象，使用getBean(“id”)
        SomeService service = (SomeService) ctx.getBean("someService");
        // 4.调用对象的方法，接口中的方法
        service.doSome();

    }
}
