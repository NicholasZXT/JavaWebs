package pers.spring.ioc.di_annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 *   @Component: 表示创建对象，对象放到容器中。 作用是<bean>
 *       属性：value ，表示对象名称，也就是bean的id属性值
 *       位置：在类的上面，表示创建此类的对象。
 *   @Component(value = "myStudent") 等同于 <bean id="myStudent" class="com.zxt.javawebs.di_annotation.Student" />
 *
 *   和 @Component功能相同的创建对象的注解：
 *   1) @Repository : 放在dao接口的实现类上面，表示创建dao对象，持久层对象，能访问数据库
 *   2) @Service :    放在业务层接口的实现类上面， 表示创建业务层对象， 业务层对象有事务的功能
 *   3) @Controller：放在控制器类的上面，表示创建控制器对象。 属于表示层对象。
 *                   控制器对象能接受请求，把请求的处理结果显示给用户。
 *   以上四个注解都能创建对象，但是 @Repository @Service @Controller有角色说明， 表示对象是分层的。
 *   对象是属于不能层的，具有额外的功能。
 */

//使用value 指定对象的名称，不过通常可以省略 value=
// 没有提供自定义对象名称时则使用框架的默认对象名称：类名首字母小写
@Component(value = "myStudent")
public class Student {
    /**
     * 简单类型属性赋值：@Value
     * @Value: 简单类型属性赋值
     *    属性：value 简单类型属性值
     *    位置：1）在属性定义的上面 ，无需set方法，推荐使用
     *         2）在set方法的上面
     */
    @Value("李四")
    private String name;

    //使用外部属性文件中的数据，语法 @Value(${"key"})
    @Value("${age}")
    private int age;

    // gender 属性使用 set 方法赋值
    private String gender;

    @Value("male")
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 引用类型
     * @Autowired: spring框架提供的，给引用类型赋值的，使用自动注入原理，支持byName，byType。默认是byType.
     *       位置：1)在属性定义的上面，无需set方法，推荐使用
     *            2）在set方法的上面
     */
    //默认使用byType
    @Autowired
    private School school;

    public Student() {
        //System.out.println("Student无参数构造方法");
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", school=" + school +
                '}';
    }
}
