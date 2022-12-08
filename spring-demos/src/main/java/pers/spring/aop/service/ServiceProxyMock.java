package pers.spring.aop.service;

import pers.spring.aop.service.impl.SomeServiceImplV2;
import pers.spring.aop.handler.MyAspect;

/**
 * 这个类就是简化版的 AspectJ 框架生成的代理，它和 ServiceProxy 类似
 */
public class ServiceProxyMock implements SomeService {

    // 实现了 Service 接口的类，其中的方法只实现了业务逻辑
    SomeService target = new SomeServiceImplV2();

    // 切面类 ----- KEY
    MyAspect aspect  = new MyAspect();

    @Override
    public void doSome(String item, int num) {
        // 调用切面对象的前置方法
        aspect.myBefore();
        target.doSome(item, num);
    }

    @Override
    public void doOther(String item, int num) {
        aspect.myBefore();
        target.doOther(item, num);
    }
}
