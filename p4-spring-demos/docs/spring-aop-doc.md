使用AOP的步骤：
1. 新建maven项目 

2. 修改pom.xml 加入依赖  
  + spring-context依赖
  + spring-aspects依赖（能使用aspectj框架的功能） 
  + junit

3. 创建业务接口和实现类

4. 创建一个普通类，该类作为切面类
  + 在类的上面加入@Aspect 
  + 在类中定义方法， 方法表示切面的功能 
  + 在方法的上面加入Aspect框架中的通知注解，例如@Before(value="切入点表达式")，定义该方法切入哪些业务功能

5.创建spring配置文件。
  + 声明目标对象——也就是业务接口的实现类
  + 声明切面类对象
  + 声明自动代理生成器

6. 创建测试类，测试目标方法执行时，增加切面的功能


`service.SomeService` 是接口，它有两个实现类：
+ `service.impl.SomeServiceNormalImpl` 是不使用AOP方式的常规实现，每个方法中都混有非业务代码
+ `service.impl.SomeServiceAopImpl` 是去除了业务无关代码的实现，每个方法里都只有业务代码，其他代码交给AOP来添加

`service.ServiceProxy`是通过代理来实现接口实现类功能，它展示的就是 AOP 的代理原理。  
`service.ServiceProxyMock` + `handler.MyAspect`展示的就是AOP的简化。