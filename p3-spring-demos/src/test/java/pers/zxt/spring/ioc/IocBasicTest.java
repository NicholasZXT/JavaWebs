package pers.zxt.spring.ioc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.Date;

import pers.zxt.spring.ioc.service.SomeService;
import pers.zxt.spring.ioc.service.impl.SomeServiceImpl;

public class IocBasicTest
{
    @Test
    public void normalUse(){
        // 正常使用 service 的流程
        SomeService service = new SomeServiceImpl();
        service.doSome();
    }

    @Test
    public void useSpring(){
        // 通过spring容器来获取对象
        // 1.指定spring配置文件: 从类路径（classpath）之下开始的路径
        // 这里的配置文件名称为 start-beans.xml，但是实际中常常命名为 applicationContext.xml
        String config= "ioc/start-beans.xml";

        // 2.创建容器对象， ApplicationContext 表示spring容器对象，后续通过ctx获取某个java对象
        // 这里创建容器对象的时候，就会在将 start-beans.xml 配置中定义的所有对象都创建出来（同一个类可以创建多个对象）
        // 这样的优点是获取对象快，但是缺点是占用内存
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);

        // 3.从容器中获取指定名称的对象，使用getBean(“id”)
        SomeService service = (SomeService) ctx.getBean("someService");
        // 使用下面这种方式可以不用做类型转换
        //SomeService service = ctx.getBean(SomeService.class);

        // 4.调用对象的方法，接口中的方法
        service.doSome();
    }


    /**
     * spring创建对象， 调用是类的那个方法？
     * 默认是调用类的无参数构造方法。
     */
    @Test
    public void test1(){
        String config= "ioc/start-beans.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        SomeService service  = (SomeService) ctx.getBean("someService");
        service.doSome();
    }

    /**
     * 获取容器中对象的信息
     */
    @Test
    public void test2(){
        String config= "ioc/start-beans.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);

        //获取容器中定义对象的数量
        int nums = ctx.getBeanDefinitionCount();
        System.out.println("容器中定义对象的数量：" + nums);

        //获取容器中定义的对象名称
        String names[] = ctx.getBeanDefinitionNames();
        for(String name:names){
            System.out.println("容器中对象的名称：" + name);
        }

    }

    /**
     * 创建非自定义的类对象，
     */
    @Test
    public void test3(){
        String config= "ioc/start-beans.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        // 获取非定义的对象
        Date date = (Date) ctx.getBean("mydate");
        System.out.println("date: "+date);
    }

}
