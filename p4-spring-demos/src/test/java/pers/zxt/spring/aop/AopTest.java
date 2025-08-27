package pers.zxt.spring.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.zxt.spring.aop.service.SomeService;
import pers.zxt.spring.aop.service.impl.SomeServiceNormalImpl;
import pers.zxt.spring.aop.service.ServiceProxy;
import pers.zxt.spring.aop.handler.MyAspect;

public class AopTest {

    /**
     * 使用 Service 的方式
     */
    @Test
    public void test1(){
        System.out.println("------ SomeService Normal Implementation ------");
        // 通常使用service的方式，直接实例化接口的实现类，调用其中的接口方法
        // SomeServiceNormalImpl.doSome() 方法中，业务逻辑和非业务逻辑的代码混合在一起
        SomeService service = new SomeServiceNormalImpl();
        service.doSome("item-1", 10);

        System.out.println("------ SomeService AOP-like Proxy Implementation ------");
        // 使用代理的方式完成service提供的服务
        // ServiceProxy.doSome() 方法中，只有非业务逻辑的代码，业务逻辑的部分，交给接口实现类SomeServiceImplV2完成
        // AOP 就是为了完成这样 业务代码 和 非业务代码的解耦，类似于这里的 ServiceProxy 的作用
        SomeService serviceProxy = new ServiceProxy();
        serviceProxy.doSome("item-2", 20);
    }

    /**
     * 使用 AOP 前置通知
     */
    @Test
    public void test2(){
        String config = "aop/applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);

        // 获取目标对象或者目标对象的代理
        SomeService service = (SomeService) ctx.getBean("someService");
        service.doSome("item-3", 30);

        System.out.println("=============================================");
        // 没有加入代理——注释掉 aop/applicationContext.xml 配置文件里 <aop:aspectj-autoproxy /> 这一行
        // 此时 service 类型为 pers.spring.aop.service.impl.SomeServiceAopImpl，没有变化
        // 加入代理时，service 对象的类型变为了 com.sun.proxy.$Proxy8
        System.out.println("service.class: "+service.getClass().getName());

        System.out.println("=============================================");
        service.doOther("item-4", 40);

        System.out.println("=============================================");
        // 获取一下切面类，就是一个普通的java对象，其中的方法也能正常调用
        MyAspect myAspect = (MyAspect) ctx.getBean("myAspect");
        System.out.println("myAspect.class: "+myAspect.getClass().getName());
        myAspect.myBefore();
        // myBefore2方法因为有一个 JointPoint 参数，不好直接调用
        //myAspect.myBefore2();
    }
}
