package pers.spring.aop.service.impl;

import java.util.Date;
import pers.spring.aop.service.SomeService;

public class SomeServiceImplV2 implements SomeService {
    /**
     * 这个 Service 的接口实现类中，每个方法内部只有业务逻辑代码，非业务逻辑的代码，交给了代理类 ServiceProxy 实现
     */
    @Override
    public void doSome(String item, int num) {
        System.out.println("业务方法doSome,计算商品[" + item + "], 购买金额: " + num);
    }

    @Override
    public void doOther(String item, int num) {
        System.out.println("业务方法doOther,消减库存[" + item + "], 数量: " + num);
    }
}
