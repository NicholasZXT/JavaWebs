package pers.zxt.spring.aop.handler;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Date;

/**
 *  @Aspect: 切面类的注解
 *    位置：放在某个类的上面
 *    作用：表示当前类是切面类
 *    切面类：表示切面功能的类
 */
@Aspect
public class MyAspect {
    //接下来定义方法，表示切面的具体功能
    /**
     前置通知方法的定义：方法是public，返回值void，方法名称自定义，方法参数可有可无，如果有，第一个参数必须是 JoinPoint.
     @Before：前置通知
     属性：value 切入点表达式，表示切面的执行位置。
     位置：在方法的上面
     特点：
      1）执行时间：在目标方法之前先执行的。
      2）不会影响目标方法的执行。
      3）不会修改目标方法的执行结果。
     */
    @Before(value = "execution(public void pers.zxt.spring.aop.service.impl.SomeServiceAopImpl.doSome(String, int))")
    public void myBefore(){
        //切面的代码
        System.out.println("myBefore: 前置通知，在目标方法之前先执行："+ new Date());
        //System.out.println("\r\n");
    }

    /**
     * 切面类中的通知方法，可以有参数，但第一个参数必须是 JoinPoint 类型
     * JoinPoint：表示正在执行的业务方法，相当于反射中 Method。
     *   使用要求：必须是参数列表的第一个
     *   作用：获取方法执行时的信息，例如方法名称，方法的参数集合
     */
    // 这里的切入点表达式使用了匹配，会在 doSome 和 doOther 上同时起作用
    @Before(value = "execution(public void pers.zxt.spring.aop.service.impl.SomeServiceAopImpl.do*(String, int))")
    public void myBefore2(JoinPoint jp){
        //获取方法的定义
        System.out.println("myBefore2: 前置通知-目标方法定义："+jp.getSignature());
        System.out.println("myBefore2: 前置通知-方法名称："+jp.getSignature().getName());
        //获取方法执行时参数
        Object[] args = jp.getArgs();// 数组中存放的是方法的所有参数
        for(Object obj:args){
            System.out.println("myBefore2: 前置通知-方法的参数："+obj);
        }
        String methodName = jp.getSignature().getName();
        if("doSome".equals(methodName)){
            //切面的代码
            System.out.println("myBefore2: doSome前置通知");
        } else if("doOther".equals(methodName)){
            System.out.println("myBefore2: doOther前置通知");
        }
    }

    /**
     * @Pointcut: 定义和管理切入点，不是通知注解。
     *   属性：value 切入点表达式
     *   位置：在一个自定义方法的上面， 这个方法看做是切入点表达式的别名。其他的通知注解中，可以使用方法名称，就表示使用这个切入点表达式了。
     */
    @Pointcut("execution(* *..SomeServiceImpl.do*(..))")
    private void mypt(){
        //方法体内无需代码
    }


}
