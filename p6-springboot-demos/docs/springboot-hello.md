
`pers.zxt.springboot.hello` 包下是springboot的入门体验.
`MyApplication`展示 SpringBoot 的使用，与之配合的内容如下：
+ resource下的`springboot-hello`里存放对应的配置文件
+ 配合 `controller` 包里的 `BootController` 使用
+ 这个简单示例里省略了 `service`、`dao`、`domain` 等包的使用，逻辑直接放在 `controller` 包的 `BootController` 里了

这个用例展示了：
1. springboot配置文件的使用，包括多环境配置，使用自定义变量
2. 如何使用注解的方式快速创建一个 REST 接口服务

-----------------------------------------------------------------
传统 Spring MVC (基于 `web.xml`) 的配置流程中，需要：

- 明确声明 `ContextLoaderListener` 和 `DispatcherServlet`。
- 指定配置文件路径。
- 打包成 WAR 文件，部署到外部的 Servlet 容器（如 Tomcat）。

**Spring Boot 通过“约定优于配置”和“自动配置”机制，将 `web.xml` 中手动配置 `DispatcherServlet` 和 `ContextLoaderListener` 的工作，在项目启动时自动完成了。**

具体来说，Spring Boot 通过如下机制消除了对 `web.xml` 的依赖：

✅ **机制一：内嵌 Servlet 容器 (Embedded Servlet Container)**

- **核心思想**：不再依赖外部的 Tomcat/Jetty。Spring Boot 会**自动将一个 Servlet 容器（如 Tomcat, Jetty, Undertow）打包进你的 JAR 文件中**。
- **启动方式**：你的应用是一个独立的 Java 程序，通过 `main` 方法启动 (`java -jar myapp.jar`)，这个 `main` 方法会启动内嵌的 Tomcat。
- **结果**：`web.xml` 是 Servlet 规范的一部分，用于配置**外部**的 Web 容器。既然容器是内嵌的、由代码控制的，`web.xml` 就变得多余了。

✅ **机制二：自动配置 (Auto-configuration)**

这是 Spring Boot 的“魔法”所在。它基于你项目的**类路径 (Classpath)** 和已有的**Bean**，自动配置你需要的组件。

- **自动配置 `DispatcherServlet`**：
  - 当你引入了 `spring-boot-starter-web` 依赖时，`spring-webmvc` 库就在类路径上了。
  - Spring Boot 会检测到这一点。
  - 它会自动应用 `@EnableWebMvc`（或等效配置）并注册一个名为 `dispatcherServlet` 的 `ServletRegistrationBean`。
  - 这个 `ServletRegistrationBean` 会创建 `DispatcherServlet` 实例，并将其映射到 `/` 路径（就像 `web.xml` 里的 `<url-pattern>/</url-pattern>`）。
  - **关键注解**：`@SpringBootApplication` 注解包含了 `@EnableAutoConfiguration`，它会触发所有自动配置类的加载。
- **自动配置 Spring 核心容器**：
  - `@SpringBootApplication` 注解本身也包含了 `@SpringBootConfiguration`（等同于 `@Configuration`）和 `@ComponentScan`。
  - `SpringApplication.run(MyApplication.class, args)`这行代码会：
    - 创建一个 `ApplicationContext`（通常是 `AnnotationConfigServletWebServerApplicationContext`）。
    - 扫描 `@SpringBootApplication` 所在包及其子包下的组件（`@Component`, `@Service`, `@Controller`, `@Repository` 等）。
    - 将这些组件注册为 Spring Bean。
  - 这个 `ApplicationContext` 就是 Spring 的核心容器，**完全替代了 `ContextLoaderListener` 的作用**。它不再需要 `web.xml` 来触发创建。

✅ **机制三：基于 Java 的配置 (Java Config)**

- Spring Boot 鼓励使用 `@Configuration` 类和 `@Bean` 方法来代替 XML 配置。
- 例如，你不再需要 `applicationContext.xml`，而是用 `@Service`, `@Repository` 等注解标记类，让 Spring 通过 `@ComponentScan` 自动发现。
- 任何需要的 Bean 都可以通过在 `@Configuration` 类中定义 `@Bean` 方法来创建。

使用 Spring MVC 和 Spring Boot 开发Web项目时的对比总结如下：

| 传统 Spring MVC (`web.xml`)        | Spring Boot (自动配置)                                                |
|:---------------------------------|:------------------------------------------------------------------|
| **依赖外部 Servlet 容器** (Tomcat)     | **内嵌 Servlet 容器** (Tomcat/Jetty/Undertow)                         |
| **`web.xml` 是配置入口**              | **`main` 方法是启动入口**                                                |
| **手动配置 `ContextLoaderListener`** | **`@SpringBootApplication` + `SpringApplication.run()` 自动创建核心容器** |
| **手动配置 `DispatcherServlet`**     | **`WebMvcAutoConfiguration` 自动注册 `DispatcherServlet`**            |
| **XML 配置为主**                     | **Java 注解配置为主**                                                   |
| **打包成 WAR 部署**                   | **打包成可执行 JAR 直接运行**                                               |