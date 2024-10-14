使用Spring-MVC整合SSM时，需要注意有两类Spring容器：
1. Spring容器：
   + 它对应的配置文件是 `applicationContext.xml`
   + 存放的是业务相关的Bean对象，比如service，dao，实体类(domain)等等.
   + 这个容器是通过**Servlet监听器**创建的，它创建之后，就会被放入整个Web应用的 `ServletContext` 里，供所有的Servlet访问——也就是**整个Web应用进程里只有一个**
2. Spring-MVC容器
   + 它对应的配置文件是 `dispatcherServlet.xml`
   + 存放的是Web层请求处理相关的Bean对象，比如解析URL映射的`HandlerMapping`对象，处理URL请求的controller，以及视图解析的ViewResolver
   + 这个容器是在Spring-MVC提供的 `DispatcherServlet` 类里创建的——这个 Servlet 就相当于所有请求总入口的 Front-Controller

两类Spring容器的关系如下：
1. Spring容器是最先创建的，它是Spring-MVC容器的**父容器**
2. 两类Spring容器会进行汇总，**子容器Spring-MVC可以访问父容器里的bean对象，反之不行**，因此在Spring-MVC的配置文件里，
   可以使用属性注入来直接获取Spring容器里的对象
3. Spring-MVC容器里通过 `@Controller` 注解扫描得到的次级controller类里，如果要访问Spring容器对象，需要实现一个 `ApplicationContextAware` 接口，
   用于获取Spring容器的 `ApplicationContext` 对象引用
4. **Spring-MVC容器只管理（次级）Controller，其他的Service、Repository 和 Component 由Spring容器管理**，不建议两个容器上在管理Bean上发生交叉
