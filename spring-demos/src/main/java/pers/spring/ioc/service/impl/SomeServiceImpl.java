package pers.spring.ioc.service.impl;

import pers.spring.ioc.service.SomeService;

public class SomeServiceImpl implements SomeService {
    /**
     * spring默认使用的无参数构造方法，创建对象。
     * 如果定义了有参数构造方法， 应该再定义一个无参数构造方法
     */
    public SomeServiceImpl() {
        System.out.println("SomeServiceImpl的无参数构造方法");
    }

    @Override
    public void doSome() {
        System.out.println("executing doSome method");
    }
}
