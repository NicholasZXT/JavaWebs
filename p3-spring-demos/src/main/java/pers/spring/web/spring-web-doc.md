问题明确：在web开发中集成spring，主要解决的问题就是如何在Servlet中获取到Spring容器。   
+ `pers.spring.web`包中`dao`、`domain`、`service` 包的内容 和 `pers.spring.mybatis` 里的是一样的   
+ `resource/web` 中的 `mybatis.xml`、`applicationContext.xml` 配置文件和 `resource/mybatis` 里的也一样，没有变化
+ 唯一的变化是 `pers.spring.web`中新加了一个 `controller` 包，存放视图函数——也就是Servlet，**重点就是如何在这个Servlet对象里拿到spring容器对象**

思考如下：
+ 基本的一个做法是：   
  + 在 `controller.QueryStudentServlet` 的 `doGet` 方法里，使用配置文件实例化一个spring容器对象，然后获取其中的对象；
  + 但是这个方法的问题是，`doGet` 方法每次请求都会执行一次，意味着每次请求都会创建一个spring容器对象——这样的开销太大了。   

+ 进一步的想法：  
  + 为了保证每个Servlet容器只实例化一次 spring容器对象，可以考虑将 spring容器 对象的初始化放到Servlet的`init`方法里执行，这样每次请求就不会都创建一个spring容器对象了。
  + 但另一个问题是，一个web应用中，会有多个Servlet对象，每个Servlet对象持有一个spring容器对象也是不必要的——spring容器对象在整个应用里有一个就可以了

+ 最终的解决方法：
  + 使用 Servlet监听器，在初始化时创建一个spring容器，然后放到ServletContext里，供每个Servlet访问使用
  + 这个监听器spring官方在 spring-web 包中已经提供了，所以只需要在 maven 中导入
  + 由于要注册Servlet监听器，所以主要的配置在 `webapp/WEB-INF/web.xml` 里

需要注意的是，**这里的监听器创建的spring容器对象，管理的是service层的对象，也就是各个接口实现类的代理，和SpringMVC里管理controller对象的容器对象不一样**。