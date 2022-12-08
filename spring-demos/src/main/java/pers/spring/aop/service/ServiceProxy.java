package pers.spring.aop.service;

import java.util.Date;
import pers.spring.aop.service.impl.SomeServiceImplV2;

/**
 *  调用ServiceProxy类方法时候， 调用真正的目标方法，并且增加了业务逻辑之外的功能。
 *
 *  ServiceProxy叫做代理， 代理对目标的操作。
 *  创建代理，可以完成对目标方法的调用， 增减功能。
 *  保持目标方法内容不变。
 *
 *  AOP 实现的就是类似于这个代理类的功能——解耦非业务代码
 */
public class ServiceProxy implements SomeService {

    //真正的目标，也就是实现了 Service 接口的类，其中的方法只实现了业务逻辑
    SomeService target = new SomeServiceImplV2();
    @Override
    public void doSome(String item, int num) {
        // 再封装一层 非业务逻辑代码
        System.out.println("日志功能，记录方法的执行时间="+new Date());
        target.doSome(item, num);
        System.out.println("事务功能，业务方法之后，提交事务");
    }

    @Override
    public void doOther() {
        System.out.println("日志功能，记录方法的执行时间="+new Date());
        target.doOther();
        System.out.println("事务功能，业务方法之后，提交事务");
    }
}
