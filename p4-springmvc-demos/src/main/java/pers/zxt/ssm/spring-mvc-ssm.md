使用spring-mvc整合SSM时，需要注意有两类spring容器：
1. spring容器：
   + 它对应的配置文件是 applicationContext.xml
   + 存放的是业务相关的bean对象，比如service，dao，实体类(domain)等等.
   + 这个容器是通过Servlet监听器创建的，它创建之后，就会被放入整个应用的ServletContext里，供所有的Servlet访问
2. spring-mvc容器
   + 它对应的配置文件是dispatcherServlet.xml
   + 存放的是web层相关的bean对象，比如解析URL映射的HandlerMapping对象，处理URL请求的controller，以及视图解析的ViewResolver
   + 这个容器是在spring-mvc提供的 DispatcherServlet 类里创建的——这个 Servlet 就相当于总入口的 front-controller

两类spring容器的关系如下：
1. spring容器是最先创建的，它是spring-mvc容器的**父容器**
2. 两类spring容器会进行汇总，**子容器spring-mvc可以访问父容器李的bean对象，反之不行**，因此在spring-mvc的配置文件里，
   可以使用属性注入来直接获取spring容器里的对象
3. spring-mvc容器里通过@Controller注解扫描得到的次级controller类里，如果要访问spring容器对象，需要实现一个 `ApplicationContextAware` 接口，
   用于获取spring容器的 `ApplicationContext` 对象引用