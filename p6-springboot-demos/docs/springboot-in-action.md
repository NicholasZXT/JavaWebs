[TOC]

# 项目练习说明

## config

`SpringConfig` 展示了使用 JavaConfig 来作为spring配置文件的方式，它的调试见`test`目录下的`SpringConfigTest`测试用例。  
与之配套的内容如下：
+ resource下的`spring-config`里是对应的配置文件
+ `domain`包下是3个普通的 Java Bean 对象，分别通过3种方式被引入spring容器。

-------------------------------
## hello

`pers.zxt.springboot.hello` 包下是springboot的入门体验.

`MyApplication`展示 SpringBoot 的使用，与之配合的内容如下：
+ resource下的`springboot-hello`里存放对应的配置文件
+ 配合 `controller` 包里的 `BootController` 使用
+ 这个简单示例里省略了 `service`、`dao`、`domain` 等包的使用，逻辑直接放在 `controller` 包的 `BootController` 里了

这个用例展示了：
1. springboot配置文件的使用，包括多环境配置，使用自定义变量
2. 如何使用注解的方式快速创建一个 REST 接口服务


-------------------------------
## ssm

SpringBoot 整合 mybatis.

几个关键点如下：

1. 引入 mybatis-spring-boot-starter 和 MySQL 驱动
2. 告诉 mybatis 哪里去寻找 dao 接口，有两种方式：
   1. 在 启动入口类上使用 `org.mybatis.spring.annotation.MapperScan` 注解，指定要扫描的包
   2. 在 `StudentDao` 接口上使用 `org.apache.ibatis.annotations.Mapper` 注解
3. 在 application.yml 中配置数据源

-------------------------------
## ssmp

SpringBoot 整合 mybatis-plus.



-------------------------------
## swagger

SpringBoot 集成 Swagger，主要是 SpringDoc。

常用注解：

✅ 一、 主要用于实体类 (POJO/DTO) 的注解

这些注解直接作用于 Java 类或其字段/属性上，用于生成 OpenAPI 的 `components/schemas` 部分。

| 注解                     | 作用位置                 | 说明                                                   | 示例                                                                                                    |
|:-----------------------|:---------------------|:-----------------------------------------------------|:------------------------------------------------------------------------------------------------------|
| **`@Schema`**          | **类、字段、方法 (Getter)** | **最核心的注解！** 用于描述整个模型或单个字段。                           | `@Schema(description = "用户邮箱", example = "user@ex.com", requiredMode = Schema.RequiredMode.REQUIRED)` |
| **`@ArraySchema`**     | 字段 (当字段是数组/集合时)      | 专门用于描述数组或集合类型的字段。通常与 `@Schema` 配合使用。                 | `@ArraySchema(schema = @Schema(implementation = String.class), uniqueItems = true)`                   |
| **`@ParameterObject`** | 类                    | 标记一个类作为“参数对象”，其字段将被视为 API 操作的查询参数 (`@RequestParam`)。 | `@ParameterObject public class UserQuery { ... }`                                                     |

✅ 二、 主要用于 Controller 的注解

这些注解作用于 Controller 类、方法、方法参数或方法返回值上，用于生成 OpenAPI 的 `paths` 部分。

| 注解                                                          | 作用位置                                | 说明                                                                                  | 示例                                                                                                                              |
|:------------------------------------------------------------|:------------------------------------|:------------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------------|
| **`@Operation`**                                            | **方法**                              | **描述单个 API 操作**。提供 summary, description, tags, parameters, responses, deprecated 等。 | `@Operation(summary = "创建用户", description = "创建一个新用户")`                                                                         |
| **`@Parameter`**                                            | 方法参数                                | **描述单个操作参数**（路径、查询、Header、Cookie）。比 `@RequestParam` 等更丰富。                           | `@Parameter(description = "用户ID", required = true, example = "1") @PathVariable Long id`                                        |
| **`@io.swagger.v3.oas.annotations.parameters.RequestBody`** | 方法参数 (通常是 `@RequestBody`)           | **描述请求体 (Request Body)** 的内容、描述、示例、是否必需等。                                           | `@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "用户信息", required = true) @RequestBody User user`           |
| **`@ApiResponse`**                                          | 方法 (常与 `@Operation.responses` 一起使用) | **描述一个可能的响应**（成功或错误）。定义状态码、描述、内容 (Content)。                                         | `@ApiResponse(responseCode = "200", description = "用户创建成功", content = @Content(schema = @Schema(implementation = User.class)))` |
| **`@ApiResponses`**                                         | 方法                                  | 包含多个 `@ApiResponse`。                                                                | `@ApiResponses({ @ApiResponse(...), @ApiResponse(...) })`                                                                       |
| **`@Tag`**                                                  | **类 或 方法**                          | **为 API 操作分组**。在 Swagger UI 中形成标签页。                                                 | `@Tag(name = "用户管理", description = "用户相关的增删改查操作")`                                                                              |
| **`@SecurityRequirement`**                                  | 方法 或 类                              | 指定此操作需要的安全方案（需先在 `OpenAPI` Bean 或 `@SecurityScheme` 中定义）。                           | `@SecurityRequirement(name = "bearer-key")`                                                                                     |


-------------------------------
## validation

SpringBoot 数据校验.

- JSR 380 / JSR 303 标准 validation-api 提供的注解，包路径：`jakarta.validation.constraints`/`javax.validation.constraints`

| 注解                           | 说明                                                                         | 适用类型                                                                                                          |
|:-----------------------------|:---------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------|
| `@NotNull`                   | **不为 null**。被注解的元素不能为 `null`。                                              | 任意类型 (Object, String, Collection, 数值包装类等)                                                                     |
| `@Null`                      | **必须为 null**。被注解的元素必须为 `null`。                                             | 任意类型                                                                                                          |
| `@NotEmpty`                  | **非空**。被注解的字符串、集合、Map 或数组不能为 `null`，且其长度/大小必须大于 0。                         | `String`, `Collection`, `Map`, 数组                                                                             |
| `@NotBlank`                  | **非空白**。被注解的字符串不能为 `null`，且去掉首尾空格后长度必须大于 0。**注意**：此注解**仅适用于 `String` 类型**。 | `String`                                                                                                      |
| `@Min(value)`                | **最小值**。被注解的数值（或字符串解析成的数值）必须大于或等于指定值。                                      | `byte`, `short`, `int`, `long`, `BigInteger`, `BigDecimal` 及其包装类                                              |
| `@Max(value)`                | **最大值**。被注解的数值（或字符串解析成的数值）必须小于或等于指定值。                                      | `byte`, `short`, `int`, `long`, `BigInteger`, `BigDecimal` 及其包装类                                              |
| `@Size(min, max)`            | **大小**。被注解的元素（字符串、集合、数组、Map）的大小（长度、元素个数）必须在指定范围内。`min` 和 `max` 可选。         | `String`, `Collection`, `Map`, 数组                                                                             |
| `@Pattern(regexp)`           | **正则表达式**。被注解的字符串必须匹配指定的正则表达式。                                             | `CharSequence` (String)                                                                                       |
| `@DecimalMin(value)`         | **最小值（字符串比较）**。被注解的数值必须大于或等于指定值。使用 `BigDecimal` 的字符串比较，支持负数和科学计数法。         | `CharSequence` (String), `byte`, `short`, `int`, `long`, `BigInteger`, `BigDecimal` 及其包装类                     |
| `@DecimalMax(value)`         | **最大值（字符串比较）**。被注解的数值必须小于或等于指定值。使用 `BigDecimal` 的字符串比较。                    | `CharSequence` (String), `byte`, `short`, `int`, `long`, `BigInteger`, `BigDecimal` 及其包装类                     |
| `@AssertTrue`                | **必须为 true**。被注解的布尔值必须为 `true`。                                            | `boolean`, `Boolean`                                                                                          |
| `@AssertFalse`               | **必须为 false**。被注解的布尔值必须为 `false`。                                          | `boolean`, `Boolean`                                                                                          |
| `@Digits(integer, fraction)` | **数字格式**。被注解的数值必须是一个数字，且整数部分最多 `integer` 位，小数部分最多 `fraction` 位。            | `CharSequence` (String), `byte`, `short`, `int`, `long`, `BigInteger`, `BigDecimal` 及其包装类                     |
| `@Past`                      | **过去的时间**。被注解的日期/时间必须在当前时间之前。                                              | `java.util.Date`, `java.util.Calendar`, `java.time` 中的日期时间类 (如 `LocalDateTime`, `ZonedDateTime`, `Instant` 等) |
| `@PastOrPresent`             | **过去或现在的时间**。被注解的日期/时间必须在当前时间之前或与当前时间相等。                                   | `java.util.Date`, `java.util.Calendar`, `java.time` 中的日期时间类                                                   |
| `@Future`                    | **未来的时间**。被注解的日期/时间必须在当前时间之后。                                              | `java.util.Date`, `java.util.Calendar`, `java.time` 中的日期时间类                                                   |
| `@FutureOrPresent`           | **未来或现在的时间**。被注解的日期/时间必须在当前时间之后或与当前时间相等。                                   | `java.util.Date`, `java.util.Calendar`, `java.time` 中的日期时间类                                                   |
| `@Email`                     | **邮箱格式**。被注解的字符串必须是合法的邮箱地址格式。可以指定 `regexp` 和 `flags` 进行自定义校验。              | `CharSequence` (String)                                                                                       |


- Hibernate Validator 提供的注解，均位于 `org.hibernate.validator.constraints` 包内

| 注解                 | 说明                  |
|:-------------------|:--------------------|
| `@Email`           | 被注释的元素必须是电子邮箱地址     |
| `@Length`          | 被注释的字符串的大小必须在指定的范围内 |
| `@NotEmpty`        | 被注释的字符串的必须非空        |
| `@Range`           | 被注释的元素必须在合适的范围内     |
| `CreditCardNumber` | 被注释的元素必须符合信用卡格式     |


有两个注解的使用需要注意：`org.springframework.validation.annotation.Validated` 和 `javax.validation.Valid`。

两者联系：`@Valid` 是 **Bean Validation 规范的核心注解**，而 `@Validated` 是 **Spring 框架提供的、对 `@Valid` 功能的扩展和增强**。

📌 **核心区别概览**

| 特性           | `@Valid` (`javax.validation.Valid` / `jakarta.validation.Valid`) | `@Validated` (`org.springframework.validation.annotation.Validated`) |
|:-------------|:-----------------------------------------------------------------|:---------------------------------------------------------------------|
| **来源**       | Bean Validation 规范 (JSR 303/JSR 380)                             | Spring Framework 特有                                                  |
| **主要用途**     | **嵌套对象校验** 和 **级联校验**                                            | **方法级别的参数校验** (尤其是分组校验)                                              |
| **作用目标**     | 字段、方法、构造函数、方法参数、类型参数                                             | **类** (类型)、**方法**、**构造函数**                                           |
| **核心能力**     | 触发对注解目标对象的校验，支持级联校验 (`@Valid`)。                                  | 支持**校验分组 (Validation Groups)** 和 **方法参数/返回值校验**。                     |
| **是否需要额外配置** | 在 Spring 中使用通常需要 `@Validated` 或 AOP 代理                           | 需要在类上标注以启用方法校验                                                       |

`@Valid`注解的作用有：

- 告诉 Bean Validation 框架（如 Hibernate Validator）“**请校验这个对象**”。
- 标识需要校验的**嵌套对象**
- 触发**级联校验**

`@Validated`注解作用：

- 主要标注在**类**上，告诉Spring框架，以启用该类所有公共方法的校验。
- 启用方法级别的校验，Spring 使用 AOP 代理来拦截带有 `@Validated` 注解的 **Bean 的方法调用**
- 支持校验分组 (Validation Groups)

两者使用场景对比：

| 场景                                     | 使用 `@Valid`                             | 使用 `@Validated`                    |
|:---------------------------------------|:----------------------------------------|:-----------------------------------|
| **REST Controller 中校验 `@RequestBody`** | ✅ `@Valid @RequestBody User user`       | ❌ 通常不需要                            |
| **校验嵌套对象/级联校验**                        | ✅ `@Valid private Address address;`     | ❌ 不适用                              |
| **Service 层方法参数校验 (基本类型)**             | ❌ 仅用 `@Valid` 无法生效                      | ✅ `@Validated` + `@Min(1) Long id` |
| **Service 层方法参数校验 (复杂对象)**             | ✅ `@Valid User user` (但类需 `@Validated`) | ✅ 类上必须有 `@Validated`               |
| **需要校验分组 (如 创建/更新)**                   | ❌ 不支持                                   | ✅ `@Validated(CreateGroup.class)`  |
| **校验方法返回值**                            | ✅ `@Valid @ResponseBody User getUser()` | ✅ 需要类上 `@Validated`                |



-----------------------------------------------------------------
# SpringBoot 对比 SpringMVC

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


------------------------------------------------
# Spring 框架系列常用注解

## 🧩 一、 核心配置与组件管理

这些注解是构建 Spring 应用的基础。

| 注解                | 包路径                                       | 说明                                                                              |
|:------------------|:------------------------------------------|:--------------------------------------------------------------------------------|
| `@Configuration`  | `org.springframework.context.annotation`  | 标记一个类为配置类，可包含 `@Bean` 方法。                                                       |
| `@Component`      | `org.springframework.stereotype`          | 通用的组件注解，标记一个类为 Spring 管理的 Bean。                                                 |
| `@Service`        | `org.springframework.stereotype`          | 标记一个类为业务逻辑层 (Service Layer) 的 Bean。语义化 `@Component`。                            |
| `@Repository`     | `org.springframework.stereotype`          | 标记一个类为数据访问层 (DAO/Repository) 的 Bean。语义化 `@Component`，并能自动处理持久层异常。               |
| `@Controller`     | `org.springframework.stereotype`          | 标记一个类为 Spring MVC 的控制器，处理 Web 请求。语义化 `@Component`。                              |
| `@RestController` | `org.springframework.web.bind.annotation` | 组合注解 (`@Controller` + `@ResponseBody`)，用于构建 RESTful Web 服务，返回数据而非视图名。           |
| `@ComponentScan`  | `org.springframework.context.annotation`  | 启用组件扫描，自动发现并注册标注了 `@Component`, `@Service`, `@Controller`, `@Repository` 等注解的类。 |
| `@Scope`          | `org.springframework.context.annotation`  | 定义 Bean 的作用域（如 `singleton`, `prototype`）。                                       |
| `@Lazy`           | `org.springframework.context.annotation`  | 延迟初始化 Bean，直到第一次被请求时才创建。                                                        |
| `@Primary`        | `org.springframework.context.annotation`  | 当存在多个相同类型的 Bean 时，优先注入被 `@Primary` 标记的 Bean。                                    |


与 `@Configuration` 配置类相关的注解：

| 注解                | 包路径                                            | 说明                                                                                                                                    |
|:------------------|:-----------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------------------|
| `@Bean`           | `org.springframework.context.annotation`       | **定义 Bean**。标记在 `@Configuration` 类的方法上，该方法的返回值将被注册为 Spring 容器中的一个 Bean。可以指定 Bean 名称、作用域、初始化/销毁方法等。                                    |
| `@Scope`          | `org.springframework.context.annotation`       | **定义 Bean 作用域**。可以用于 `@Component` 类或 `@Bean` 方法上。常用值：`singleton` (默认), `prototype`, `request`, `session`, `application`, `websocket`。 |
| `@DependsOn`      | `org.springframework.context.annotation`       | **强制初始化顺序**。标记在 `@Component` 类或 `@Bean` 方法上，指定当前 Bean 依赖于其他 Bean，确保这些依赖的 Bean 会先被创建。                                                  |
| `@Lookup`         | `org.springframework.beans.factory.annotation` | **方法注入**。标记在抽象方法上，Spring 会动态生成子类，重写该方法以返回指定名称的 Bean 实例。常用于单例 Bean 注入原型 (prototype) Bean 的场景。                                          |
| `@Import`         | `org.springframework.context.annotation`       | **导入配置**。可以导入其他 `@Configuration` 类、`@Component` 类或 `ImportSelector`/`ImportBeanDefinitionRegistrar` 实现类，将它们的 Bean 定义引入当前配置。           |
| `@ImportResource` | `org.springframework.context.annotation`       | **导入 XML 配置**。用于加载和解析一个或多个 Spring XML 配置文件，将其中定义的 Bean 引入基于 Java 的配置中。                                                                |
| `@Profile`        | `org.springframework.context.annotation`       | **环境配置**。标记在 `@Component` 类或 `@Configuration` 类上，指定该 Bean 只在特定的 Spring Profile（如 `dev`, `test`, `prod`）激活时才会被创建。                      |



------

## 🔌 二、 依赖注入

用于将 Bean 注入到其他 Bean 中。

| 注解           | 包路径                                            | 说明                                                                 |
|:-------------|:-----------------------------------------------|:-------------------------------------------------------------------|
| `@Autowired` | `org.springframework.beans.factory.annotation` | 自动装配依赖。可以用于字段、构造函数、setter 方法或任意方法。                                 |
| `@Qualifier` | `org.springframework.beans.factory.annotation` | 与 `@Autowired` 配合使用，通过名称指定要注入的 Bean，解决 `@Autowired` 的歧义。           |
| `@Value`     | `org.springframework.beans.factory.annotation` | 注入属性值（如配置文件中的值、字面量）。支持 SpEL (Spring Expression Language)。          |
| `@Resource`  | `javax.annotation` (JSR-250)                   | JSR-250 标准注解，功能类似于 `@Autowired` + `@Qualifier`，但默认按名称 (`name`) 注入。 |
| `@Inject`    | `javax.inject` (JSR-330)                       | JSR-330 标准注解，功能基本等同于 `@Autowired`。                                 |

以下为Bean生命周期回调相关注解：

| 注解               | 包路径                                         | 说明                                                                                                   |
|:-----------------|:--------------------------------------------|:-----------------------------------------------------------------------------------------------------|
| `@PostConstruct` | `javax.annotation` (JSR-250)                | **初始化后回调**。标记的方法在 Bean 的依赖注入完成后、Bean 被任何其他 Bean 使用之前执行。常用于执行初始化逻辑（如校验配置、打开资源）。                       |
| `@PreDestroy`    | `javax.annotation` (JSR-250)                | **销毁前回调**。标记的方法在 Bean 被销毁（如容器关闭）之前执行。常用于执行清理逻辑（如关闭数据库连接、释放文件句柄）。                                     |
| `@EventListener` | `org.springframework.context.event`         | **事件监听**。标记一个方法为事件监听器。当 Spring 容器发布指定类型的 `ApplicationEvent` 时，该方法会被调用。非常灵活，可用于解耦业务逻辑。                |
| `@Async`         | `org.springframework.scheduling.annotation` | **异步执行**。标记一个方法为异步方法。调用该方法时，它会在一个单独的线程（由 `TaskExecutor` 管理）中执行，调用者会立即返回。需要在配置类上使用 `@EnableAsync` 启用。 |

------

## 🌐 三、 Web MVC

用于构建 Web 应用和 RESTful API。

| 注解                  | 包路径                                       | 说明                                                                |
|:--------------------|:------------------------------------------|:------------------------------------------------------------------|
| `@RequestMapping`   | `org.springframework.web.bind.annotation` | 映射 Web 请求到处理器方法。可指定 URL 路径、HTTP 方法等。                              |
| `@GetMapping`       | `org.springframework.web.bind.annotation` | 组合注解 (`@RequestMapping(method = RequestMethod.GET)`)，映射 GET 请求。   |
| `@PostMapping`      | `org.springframework.web.bind.annotation` | 组合注解 (`@RequestMapping(method = RequestMethod.POST)`)，映射 POST 请求。 |
| `@PutMapping`       | `org.springframework.web.bind.annotation` | 组合注解，映射 PUT 请求。                                                   |
| `@DeleteMapping`    | `org.springframework.web.bind.annotation` | 组合注解，映射 DELETE 请求。                                                |
| `@PatchMapping`     | `org.springframework.web.bind.annotation` | 组合注解，映射 PATCH 请求。                                                 |
| `@RequestParam`     | `org.springframework.web.bind.annotation` | 将请求参数绑定到方法参数。                                                     |
| `@PathVariable`     | `org.springframework.web.bind.annotation` | 将 URL 模板变量绑定到方法参数。                                                |
| `@RequestBody`      | `org.springframework.web.bind.annotation` | 将 HTTP 请求体 (Body) 绑定到方法参数（通常用于接收 JSON/XML 数据）。                    |
| `@ResponseBody`     | `org.springframework.web.bind.annotation` | 将方法返回值直接写入 HTTP 响应体（通常用于返回 JSON/XML 数据）。`@RestController` 已包含此功能。 |
| `@RequestHeader`    | `org.springframework.web.bind.annotation` | 将 HTTP 请求头绑定到方法参数。                                                |
| `@CookieValue`      | `org.springframework.web.bind.annotation` | 将 Cookie 值绑定到方法参数。                                                |
| `@ModelAttribute`   | `org.springframework.web.bind.annotation` | 将方法参数或返回值绑定到 Model。也可用于在请求处理前填充 Model。                            |
| `@SessionAttribute` | `org.springframework.web.bind.annotation` | 访问 Session 中的属性。                                                  |
| `@RequestAttribute` | `org.springframework.web.bind.annotation` | 访问请求 (Request) 中的属性。                                              |

------
  
## 🗄️ 四、 数据访问

用于数据库操作和事务管理。

| 注解                | 包路径                                          | 说明                                                                       |
|:------------------|:---------------------------------------------|:-------------------------------------------------------------------------|
| `@Transactional`  | `org.springframework.transaction.annotation` | 声明式事务管理。标记在方法或类上，定义事务的传播行为、隔离级别、超时等。                                     |
| `@Entity`         | `javax.persistence` (JPA)                    | JPA 注解，标记一个类为持久化实体 (Entity)。                                             |
| `@Table`          | `javax.persistence` (JPA)                    | JPA 注解，指定实体映射到的数据库表名。                                                    |
| `@Id`             | `javax.persistence` (JPA)                    | JPA 注解，标记实体的主键字段。                                                        |
| `@GeneratedValue` | `javax.persistence` (JPA)                    | JPA 注解，指定主键的生成策略 (如 `AUTO`, `IDENTITY`)。                                 |
| `@Column`         | `javax.persistence` (JPA)                    | JPA 注解，指定字段映射到的数据库列名及其他属性。                                               |
| `@JoinColumn`     | `javax.persistence` (JPA)                    | JPA 注解，定义外键关联。                                                           |
| `@OneToOne`       | `javax.persistence` (JPA)                    | JPA 注解，定义一对一关联。                                                          |
| `@OneToMany`      | `javax.persistence` (JPA)                    | JPA 注解，定义一对多关联。                                                          |
| `@ManyToOne`      | `javax.persistence` (JPA)                    | JPA 注解，定义多对一关联。                                                          |
| `@ManyToMany`     | `javax.persistence` (JPA)                    | JPA 注解，定义多对多关联。                                                          |
| `@Query`          | `org.springframework.data.jpa.repository`    | Spring Data JPA 注解，用于在 Repository 接口中定义 JPQL 或原生 SQL 查询。                 |
| `@Modifying`      | `org.springframework.data.jpa.repository`    | 与 `@Query` 配合使用，标记一个查询是修改操作（INSERT, UPDATE, DELETE），需要 `@Transactional`。 |


------

## 🔁 五、 AOP

用于实现横切关注点（如日志、安全、事务）。

| 注解                | 包路径                           | 说明                                                     |
|:------------------|:------------------------------|:-------------------------------------------------------|
| `@Aspect`         | `org.aspectj.lang.annotation` | 标记一个类为切面 (Aspect)。                                     |
| `@Before`         | `org.aspectj.lang.annotation` | 定义前置通知 (Before Advice)，在目标方法执行前运行。                     |
| `@After`          | `org.aspectj.lang.annotation` | 定义后置通知 (After Advice)，在目标方法执行后（无论是否异常）运行。              |
| `@AfterReturning` | `org.aspectj.lang.annotation` | 定义返回通知 (After Returning Advice)，在目标方法成功执行后运行。          |
| `@AfterThrowing`  | `org.aspectj.lang.annotation` | 定义异常通知 (After Throwing Advice)，在目标方法抛出异常后运行。           |
| `@Around`         | `org.aspectj.lang.annotation` | 定义环绕通知 (Around Advice)，可以控制目标方法的执行（在其前后执行代码，甚至决定是否执行）。 |
| `@Pointcut`       | `org.aspectj.lang.annotation` | 定义切点 (Pointcut)，指定通知应应用到哪些连接点（方法）。                     |

------

## 🧪 六、 测试

用于编写单元测试和集成测试。

| 注解                | 包路径                                                       | 说明                                                |
|:------------------|:----------------------------------------------------------|:--------------------------------------------------|
| `@SpringBootTest` | `org.springframework.boot.test.context`                   | 用于 Spring Boot 集成测试，加载完整的应用上下文。                   |
| `@WebMvcTest`     | `org.springframework.boot.test.autoconfigure.web.servlet` | 用于测试 Spring MVC 控制器，只加载 Web 层相关的 Bean。            |
| `@DataJpaTest`    | `org.springframework.boot.test.autoconfigure.orm.jpa`     | 用于测试 JPA Repository，配置内存数据库和 `TestEntityManager`。 |
| `@DataMongoTest`  | `org.springframework.boot.test.autoconfigure.data.mongo`  | 用于测试 MongoDB Repository。                          |
| `@MockBean`       | `org.springframework.boot.test.mock.mockito`              | 在测试中创建一个 Mock Bean 并将其注入到 Spring 容器中，替换真实的 Bean。  |
| `@SpyBean`        | `org.springframework.boot.test.mock.mockito`              | 在测试中创建一个 Spy Bean (部分模拟)，保留真实对象的行为，但可以对特定方法进行模拟。  |
| `@Test`           | `org.junit.jupiter.api` (JUnit 5)                         | JUnit 注解，标记一个方法为测试方法。                             |
| `@BeforeEach`     | `org.junit.jupiter.api` (JUnit 5)                         | JUnit 注解，标记的方法在每个测试方法执行前运行。                       |
| `@AfterEach`      | `org.junit.jupiter.api` (JUnit 5)                         | JUnit 注解，标记的方法在每个测试方法执行后运行。                       |
| `@BeforeAll`      | `org.junit.jupiter.api` (JUnit 5)                         | JUnit 注解，标记的方法在所有测试方法执行前运行一次。                     |
| `@AfterAll`       | `org.junit.jupiter.api` (JUnit 5)                         | JUnit 注解，标记的方法在所有测试方法执行后运行一次。                     |

------

## 🚀 七、 Spring Boot 特有注解

这些是 Spring Boot 提供的简化开发的注解。

| 注解                               | 包路径                                                | 说明                                                                                              |
|:---------------------------------|:---------------------------------------------------|:------------------------------------------------------------------------------------------------|
| `@SpringBootApplication`         | `org.springframework.boot.autoconfigure`           | **核心注解**！组合了 `@SpringBootConfiguration`, `@EnableAutoConfiguration`, `@ComponentScan`。通常用于主应用类。 |
| `@SpringBootConfiguration`       | `org.springframework.boot`                         | 标记一个类为 Spring Boot 应用的配置类。它本身是一个 `@Configuration`。通常由 `@SpringBootApplication` 包含。              |
| `@EnableAutoConfiguration`       | `org.springframework.boot.autoconfigure`           | 开启 Spring Boot 的自动配置机制。通常由 `@SpringBootApplication` 包含。                                         |
| `@ConditionalOnClass`            | `org.springframework.boot.autoconfigure.condition` | 条件化配置：只有当指定的类在 classpath 上时，才加载该自动配置。                                                           |
| `@ConditionalOnMissingBean`      | `org.springframework.boot.autoconfigure.condition` | 条件化配置：只有当容器中不存在指定类型的 Bean 时，才创建该 Bean。                                                          |
| `@ConditionalOnProperty`         | `org.springframework.boot.autoconfigure.condition` | 条件化配置：只有当指定的配置属性存在且值为预期时，才加载该配置。                                                                |
| `@ConfigurationProperties`       | `org.springframework.boot.context.properties`      | 将配置文件（如 `application.yml`）中的属性绑定到一个 Bean 上。                                                     |
| `@Validated`                     | `org.springframework.validation.annotation`        | 启用对 `@ConfigurationProperties` Bean 的 JSR-303 Bean Validation。                                  |
| `@EnableConfigurationProperties` | `org.springframework.boot.context.properties`      | 启用 `@ConfigurationProperties` 功能，并将指定的配置属性类注册为 Bean。                                            |
| `@RestControllerAdvice`          | `org.springframework.web.bind.annotation`          | 组合注解 (`@ControllerAdvice` + `@ResponseBody`)，用于全局异常处理、数据预处理等。                                   |
| `@ExceptionHandler`              | `org.springframework.web.bind.annotation`          | 在 `@ControllerAdvice` 或 `@RestControllerAdvice` 类中，定义处理特定异常的方法。                                 |

------

## ✅ 使用建议

1. **理解层级**：`@Component` 是基础，`@Service`, `@Repository`, `@Controller` 是语义化的特化。
2. **首选组合注解**：如 `@GetMapping`, `@PostMapping`, `@RestController`, `@SpringBootApplication`，它们让代码更简洁。
3. **合理使用条件注解**：`@ConditionalOn...` 系列是 Spring Boot 自动配置的核心，理解它们有助于理解自动配置原理。
4. **区分配置类**：普通配置类用 `@Configuration`，主应用类用 `@SpringBootApplication`。