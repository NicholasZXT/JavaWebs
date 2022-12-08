package pers.spring.aop.service.impl;

import java.util.Date;
import pers.spring.aop.service.SomeService;

public class SomeServiceImplV1 implements SomeService {
    /**
     * 这个 Service 的接口实现类中，每个方法内部除了业务逻辑代码，还有其他一些非业务逻辑代码，耦合度比较高
     */
    @Override
    public void doSome(String item, int num) {
        //在业务方法开始时记录时间
        System.out.println("日志功能：记录方法的执行时间="+ new Date());

        // 业务逻辑代码
        System.out.println("业务方法doSome,计算商品[" + item + "], 购买金额: " + num);

        //在业务方法之后，提交事务
        System.out.println("事务功能：提交事务处理");
    }

    @Override
    public void doOther() {
        //在业务方法开始时记录时间
        System.out.println("日志功能：记录方法的执行时间="+ new Date());

        // 业务逻辑代码
        System.out.println("业务方法doOther，消减库存");

        //在业务方法之后，提交事务
        System.out.println("事务功能：提交事务处理");

    }
}