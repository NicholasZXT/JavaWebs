ch01-hello-springmvc: 第一个SpringMVC项目。

需求：用户在页面发起一个请求， 请求交给SpringMVC的控制器对象， 并显示请求的处理结果（在结果页面显示一个欢迎语句）。

实现步骤：
1. 新建web maven工程
2. 加入依赖
   + spring-webmvc依赖，间接把spring的依赖都加入到项目
   + jsp，servlet依赖
   
3. 在 `web.xml` 中注册SpringMVC框架的核心对象 `DispatcherServlet`
   1. DispatcherServlet叫做中央调度器，**本质上是一个`Servlet`，它的父类继承了HttpServlet**，有处理请求的功能 
   2. DispatcherServlet页叫做前端控制器（front controller） 
   3. DispatcherServlet负责接收用户提交的请求，调用其它的控制器对象（次级处理器），并把请求的处理结果显示给用户

4. 创建一个发起请求的页面 index.jsp

5. 创建控制器（次级处理器）类
   1. 在类的上面加入 `@Controller` 注解，创建对象，并放入到SpringMVC容器中 
   2. 在类中的方法上面加入 `@RequestMapping` 注解，配置URL路由

6. 创建一个作为结果的jsp，显示请求的处理结果。

7. 创建SpringMVC的配置文件（spring的配置文件一样）
   1. 声明组件扫描器， 指定 `@Contorller` 注解所在的包名 
   2. 声明视图解析器，帮助处理视图的解析
