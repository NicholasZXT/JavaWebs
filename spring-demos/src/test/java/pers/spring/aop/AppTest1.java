package pers.spring.aop;

import org.junit.Test;
import pers.spring.aop.service.SomeService;
import pers.spring.aop.service.impl.SomeServiceImplV1;
import pers.spring.aop.service.ServiceProxy;

public class AppTest1 {

    /**
     * 使用 Service 的方式
     */
    @Test
    public void test01(){
        // 通常使用service的方式，直接调用接口的实现类
        // SomeServiceImplV1.doSome() 方法中，业务逻辑和非业务逻辑的代码混合在一起
        SomeService service = new SomeServiceImplV1();
        service.doSome("item-1", 10);

        System.out.println("=============================================");

        // 使用代理的方式完成service提供的服务
        // ServiceProxy.doSome() 方法中，只有非业务逻辑的代码，业务逻辑的部分，交给接口实现类SomeServiceImplV2完成
        // AOP 就是为了完成这样 业务代码 和 非业务代码的解耦，类似于这里的 ServiceProxy 的作用
        SomeService serviceProxy = new ServiceProxy();
        serviceProxy.doSome("item-2", 20);
    }
}
