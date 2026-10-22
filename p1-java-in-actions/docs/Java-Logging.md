# Introduction

参考博客：

+ [稀土掘金-深入掌握Java日志体系，再也不迷路了](https://juejin.cn/post/6905026199722917902)
+ [Java日志框架（JCL,SLF4J,Log4J,Logback）](https://whatsrtos.github.io/Java/JavaEE-Log/)
+ [知乎-带你深入Java Log框架，彻底搞懂Log4J、Log4J2、LogBack，SLF4J](https://zhuanlan.zhihu.com/p/615102614)

个人感觉Java的日志框架有那么一点混乱，这里根据上述博客的介绍，简单梳理一下。

按照时间顺序，各个日志组件如下（括号内为首次发布的大致年份，部分为近似值，仅供参考）：

1. Log4j(Log for Java) 1.x，出现的最早（1.0 约 1999 年，1.2 约 2002 年），作者是 Ceki Gülcü，后面成为Apache项目；
2. JUL(Java Util Logging)，JDK 1.4（2002年）新增的日志库，Sun公司推出；
3. JCL(Jakarta Commons Logging)，**Apache推出**（2002年），后面更名为 Commons Logging；
4. Slf4j(Simple Logging Facade for Java)（2005年）和 Logback（2006年），**不属于Apache基金会**，由 Ceki 离开Apache后开发；
5. Log4j 2.x，Apache 于 **2014年7月** 发布 2.0 正式版（GA）——2012年只是 alpha/beta 阶段，它不兼容 Log4j 1.x 的 API 与配置语法（但可通过 `log4j-1.2-api` 桥接包让老代码继续运行）。

如果按照架构来分，主要分为如下两类：

+ 记录型日志框架，底层负责具体的日志输出
  + Log4j 1.x
  + JUL
  + Logback
  + Log4j 2.x
+ 门面型日志框架，上层用于封装底层的记录型日志框架
  + JCL，又叫 Commons Logging
  + Slf4j

实际中，常用的搭配组合有如下几种：

+ **JCL(Commons Logging) + Log4j 1.x**：JCL 原生支持 Log4j 1.x（无需额外依赖），这是较老的 Apache 项目中常见的组合。
+ **Slf4j + Logback**：这一套是 Log4j 1.x 的作者离开Apache之后，另立门户开发的日志框架组合，使用的广泛程度有后来居上的趋势。
+ **Slf4j + Log4j 1.x/2.x**：需要在中间加一个绑定层（binding）。
  + 对接 Log4j 1.x：`slf4j-log4j12`；
  + 对接 Log4j 2.x：`log4j-slf4j-impl`（对应 SLF4J 1.7）或 `log4j-slf4j2-impl`（对应 SLF4J 2.0）。
  + 这层转发非常轻薄，**性能开销基本可以忽略**；真正需要小心的是依赖冲突（例如同时存在多个 SLF4J binding）。
  + 它是大数据生态（Hadoop/Spark/Hive/Kafka 等）最主流的组合，因为可以用 `log4j.properties` 方便地批量调参。
+ **JCL(Commons Logging) + Log4j 2.x**：**不能直接用**。JCL 的运行时发现顺序里没有 Log4j 2.x，必须额外加入桥接包 `org.apache.logging.log4j:log4j-jcl`，否则 JCL 会静默回落到 JUL。
+ **JCL(Commons Logging) + Logback**：需要把 `commons-logging` **替换**为 `jcl-over-slf4j.jar`。
  + 注意 `jcl-over-slf4j` 不是"叠加在 JCL 之上的适配器"，而是**对 `org.apache.commons.logging.*` 这套 API 的完整替代实现**（同名同包，内部转发给 SLF4J）。
  + 因此**必须移除 `commons-logging`**，两个 jar 同时存在会发生包冲突。

> 补充：JCL/Commons Logging 的运行时发现顺序为——classpath 上存在 `org.apache.log4j.Logger` 则用 Log4j 1.x；否则存在 JUL 则用 `Jdk14Logger`；再否则用内置的 `SimpleLog`。
> 这也是"为什么只引 commons-logging 时日志会输出到 JUL 控制台"的原因。

JDK自带的JUL使用场景比较少，远不如上面两套日志框架广泛，下面分阵营介绍各个日志组件。

---

# Apache阵营

## Log4j 1.x

官网地址：

+ [Apache-&gt;Logging Service-&gt;log4j-&gt;1.2](https://logging.apache.org/log4j/1.x)
+ [Introduction](https://logging.apache.org/log4j/1.x/manual.html)

> 根据官网文档，Log4j-1.x 已经于2015年停止维护（最高版本1.2.17，提交于2012.5），建议升级到 Log4j-2.x版本。

Log4j-1.x在大数据生态的低版本组件中还有不少使用场景。

Log4j-1.x的Maven坐标如下：

```xml
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

Log4j-1.x 分为 3 个部分：

+ Logger：日志记录器，用于在代码中记录日志。
+ Appender：日志输出组件，logger记录的日志会发送到（多个）appender，决定后续输出到何种地方，控制台，文件还是网络流。
+ Layout：日志格式组件，用于控制日志格式。

简单使用教程参考 [log4j1.2.17的使用与log4j.properties配置详解](https://blog.csdn.net/xiaoxianer321/article/details/108869506).

使用示例代码如下：

```java
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class logTest {
    // getLogger 里传入一个字符串表示日志名称就行，通常的选择是当前类的类名
	private static Logger logger = Logger.getLogger(logTest.class);
	public static void main(String[] args) throws Exception {
		// 使用项目根目录（与src同级）下的 log4j.properties 配置文件；
		// 其实不写也行，默认会自动在 classpath 下寻找 log4j.properties（即 src/main/resources/log4j.properties）
		PropertyConfigurator.configure("log4j.properties");
        // 记录日志
	    logger.trace("This is Trace Message!");
        logger.debug("This is Debug Message!");
        logger.info("This is Info Message!");
        logger.warn("This is Warn Message!");
        logger.error("This is Error Message!");
        logger.fatal("This is Fatal Message!");
    }  
}
 
/**输出结果：
  2020-10-11 20:11:24,277 [main] DEBUG [com.cn.log4j.logTest] - This is Debug Message!
  2020-10-11 20:11:24,278 [main] INFO  [com.cn.log4j.logTest] - This is Info Message!
  2020-10-11 20:11:24,278 [main] WARN  [com.cn.log4j.logTest] - This is Warn Message!
  2020-10-11 20:11:24,278 [main] ERROR [com.cn.log4j.logTest] - This is Error Message!
  2020-10-11 20:11:24,278 [main] FATAL [com.cn.log4j.logTest] - This is Fatal Message!
*/
```

### 配置文件

配置文件支持 xml 和 properties 两种格式，这里以properties格式介绍。

配置文件名必须为`log4j.properties`，一般存放在`resources`目录下：

> **注意**：本仓库当前 `pom.xml` 中 `log4j:log4j`（1.x）依赖是**注释掉**的，因此本文件在当前构建下**不会生效**。
> 它对应的是"Slf4j + Log4j 1.2.17"这套组合（见文件头部注释，典型场景是本地调试 Spark/Flink），
> 想实际运行需在 `pom.xml` 中放开 1.x 依赖并引入 `slf4j-log4j12`。保留它主要是作为配置写法参考。

```properties
################################################################################
#（1）配置根Logger，语法为：
# log4j.rootLogger = <日志级别>, <Appender1>, <Appender2>, ...
# level 是日志记录的优先级，分为 OFF, TRACE, DEBUG, INFO, WARN, ERROR, FATAL, ALL
#   Log4j 建议只使用四个级别，优先级从低到高分别是 DEBUG, INFO, WARN, ERROR
#   通过在这里定义的级别，可以控制到应用程序中相应级别的日志信息的开关
#   比如在这里定义了INFO级别，则应用程序中所有DEBUG级别的日志信息将不被打印出来
# appenderName 就是指定日志信息输出到哪个地方的配置名称——名称任意取，但是要和下面对应，可同时指定多个输出目的
# 示例: 
log4j.rootLogger=INFO, console, file

################################################################################
#（2）配置日志信息输出目的地Appender，语法为：
# 设置appender提供的实现类
# log4j.appender.appenderName = fully.qualified.name.of.appender.class
# 设置appender提供的实现类的属性
# log4j.appender.appenderName.optionN = valueN

# 示例:
log4j.appender.console = org.apache.log4j.ConsoleAppender
log4j.appender.console.Target = System.err
log4j.appender.file = org.apache.log4j.FileAppender
log4j.appender.file.File = .\\log4j.log
log4j.appender.file.Append = true

#Log4j提供的 appender 常用的有以下几种：
# 1) org.apache.log4j.ConsoleAppender, 输出到控制台
# 2) org.apache.log4j.FileAppender, 输出到文件
# 3) org.apache.log4j.DailyRollingFileAppender, 每天产生一个日志文件
# 4) org.apache.log4j.RollingFileAppender, 文件大小到达指定尺寸的时候产生一个新的文件
# 5) org.apache.log4j.WriterAppender, 将日志信息以流格式发送到任意指定的地方
#
#1) ConsoleAppender 选项属性
# - Threshold = DEBUG, 指定日志消息的输出最低层次（来自父类：AppenderSkeleton.java的属性）
# - ImmediateFlush = true, 默认值是true,所有的消息都会被立即输出，设置为false则不会输出 （来自父类WriterAppender.java的属性）
# - Target = System.err, 默认值System.out,输出到控制台(err为红色,out为黑色)
#
#2) FileAppender 选项属性
# - Threshold = INFO, 指定日志消息的输出最低层次 （来自父类：AppenderSkeleton.java的属性）
# - ImmediateFlush = true, 默认值是true,所有的消息都会被立即输出 （来自父类WriterAppender.java的属性）
# - Encoding = UTF-8, 可以指定文件编码格式 （来自父类WriterAppender.java的属性）
# - File = C:\log4j.log, 指定消息输出到C:\log4j.log文件
# - Append = false, 默认值true,将消息追加到指定文件中，false指将消息覆盖指定的文件内容
# - BufferedIO = false, 是否启用缓冲区,默认为false
# - BufferSize = 8 * 1024, 缓冲区大小,默认为8KB
#
#3) DailyRollingFileAppender 选项属性
# - Threshold = WARN, 指定日志消息的输出最低层次 （来自父类：AppenderSkeleton.java的属性）
# - ImmediateFlush = true, 默认值是true,所有的消息都会被立即输出 （来自父类WriterAppender.java的属性）
# - Encoding = UTF-8, 可以指定文件编码格式 （来自父类WriterAppender.java的属性）
# - File = C:\log4j.log, 指定消息输出到C:\log4j.log文件 （来自父类FileAppender.java的属性）
# - Append = false, 默认值true,将消息追加到指定文件中，false指将消息覆盖指定的文件内容 （来自父类FileAppender.java的属性）
# - DatePattern='.'yyyy-ww, 每周滚动一次文件,即每周产生一个新的文件。
#     还可以按用以下参数:
#     '.'yyyy-MM, 每月
#     '.'yyyy-ww, 每周
#     '.'yyyy-MM-dd, 每天
#     '.'yyyy-MM-dd-a, 每天两次
#     '.'yyyy-MM-dd-HH, 每小时
#     '.'yyyy-MM-dd-HH-mm, 每分钟
#
#4) RollingFileAppender 选项属性
# - Threshold = ERROR, 指定日志消息的输出最低层次 （来自父类：AppenderSkeleton.java的属性）
# - ImmediateFlush = TRUE, 默认值是true,所有的消息都会被立即输出 （来自父类WriterAppender.java的属性）
# - Encoding = UTF-8, 可以指定文件编码格式 （来自父类WriterAppender.java的属性）
# - File = C:/log4j.log, 指定消息输出到C:/log4j.log文件 （来自父类FileAppender.java的属性）
# - Append = FALSE, 默认值true,将消息追加到指定文件中，false指将消息覆盖指定的文件内容 （来自父类FileAppender.java的属性）
# - MaxFileSize = 100KB, 后缀可以是KB,MB,GB.在日志文件到达该大小时,将会自动滚动.如:log4j.log.1
# - MaximumFileSize = 1024, 直接使用字节不带单位
# - MaxBackupIndex = 2, 指定可以产生的滚动文件的最大数

################################################################################
#（3）配置日志信息的格式(布局)，其语法为：
# 添加layout的实现类
# log4j.appender.appenderName.layout = fully.qualified.name.of.layout.class
# 添加layout的实现类的属性
# log4j.appender.appenderName.layout.optionN = valueN

# 示例:
log4j.appender.console.layout = org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern = %d{yyyy-MM-dd HH:mm:ss} [%t] %-5p %c - %m%n
log4j.appender.file.layout = org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern = %d{yyyy-MM-dd HH:mm:ss} [%t] %-5p %c - %m%n

#Log4j提供的 layout 有以下几种：
# 1) org.apache.log4j.HTMLLayout, 以HTML表格形式布局
# 2) org.apache.log4j.PatternLayout, 可以灵活地指定布局模式 —— 这个最常用
# 3) org.apache.log4j.SimpleLayout, 包含日志信息的级别和信息字符串
# 4) org.apache.log4j.TTCCLayout, 包含日志产生的时间、线程、类别等等信息
# 5) org.apache.log4j.xml.XMLLayout, 以XML形式布局
#
#1) HTMLLayout 选项属性
# - LocationInfo = TRUE, 默认值false,输出java文件名称和行号
# - Title=Struts Log Message, 默认值 Log4J Log Messages
#
#2) PatternLayout 选项属性
# - ConversionPattern = %m%n, 格式化指定的消息(参数格式见下面)
#
#3) XMLLayout 选项属性
# - LocationInfo = TRUE, 默认值false,输出java文件名称和行号
#
#Log4J采用类似C语言中的 printf 函数的打印格式格式化日志信息，打印参数如下：
# %m 输出代码中指定的消息
# %p 输出优先级，即DEBUG,INFO,WARN,ERROR,FATAL
# %r 输出自应用启动到输出该log信息耗费的毫秒数
# %c 输出所属的类目,通常就是所在类的全名
# %t 输出产生该日志事件的线程名
# %n 输出一个回车换行符，Windows平台为“\r\n”，Unix平台为“\n”
# %d 输出日志时间点的日期或时间，默认格式为ISO8601，也可以在其后指定格式
#    如：%d{yyyy年MM月dd日 HH:mm:ss,SSS}，输出类似：2012年01月05日 22:10:28,921
# %l 输出日志事件的发生位置，包括类目名、发生的线程，以及在代码中的行数
#    如：Testlog.main(TestLog.java:10)
# %F 输出日志消息产生时所在的文件名称
# %L 输出代码中的行号
# %x 输出和当前线程相关联的NDC(嵌套诊断环境),像java servlets多客户多线程的应用中
# %% 输出一个"%"字符
#
# 可以在%与模式字符之间加上修饰符来控制其最小宽度、最大宽度、和文本的对齐方式。如：
#  %5c: 输出category名称，最小宽度是5，category<5，默认的情况下右对齐
#  %-5c:输出category名称，最小宽度是5，category<5，"-"号指定左对齐,会有空格
#  %.5c:输出category名称，最大宽度是5，category>5，就会将左边多出的字符截掉，<5不会有空格
#  %20.30c:category名称<20补空格，并且右对齐，>30字符，就从左边交远销出的字符截掉

################################################################################
#（4）设置特定包的输出特定的级别
# 格式：log4j.logger.<包名>=<日志级别>
log4j.logger.org.springframework=DEBUG
```

---

## Log4j 2.x

官方文档 [Apache Log4j](https://logging.apache.org/log4j/2.x).

Log4j-2.x的Maven坐标如下，分成了 core + api 两个依赖：

```xml
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.20.0</version>
</dependency>
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-api</artifactId>
    <version>2.20.0</version>
</dependency>
```

Log4j-2.x 主要有如下组件：

+ `Logger` / `LoggerContext`: 日志记录器，以及管理所有 Logger 的上下文；
+ `Configuration`: 配置对象，负责加载并持有整个配置（Appender/Logger/Layout 等都由它持有）；
+ `Appender`: 日志输出目的地（控制台、文件、RollingFile、Socket、JDBC 等）；
+ `Layout`: 输出格式（PatternLayout、JSON Template Layout 等）；
+ `Filter`: 日志过滤，**可以挂在 3 个层级**——Logger 上、Appender 上、以及 `<AppenderRef>` 上；
+ `Lookup`: 访问系统属性、环境变量、上下文等，用于 `${...}` 属性替换；
+ `AsyncLogger` / `AsyncAppender`: 异步日志（基于 LMAX Disruptor 无锁队列，需额外引入 `com.lmax:disruptor` 依赖）。

log4j-2.x 的架构大致如下（注意 Filter 是"事件进入 Appender 之前"生效，且可挂在多个层级）：

```text
                         ┌──────────────┐
Root Logger ────┐        │ Configuration│  ← 配置文件加载后持有的顶层对象
                │        └──────────────┘
   ┌────────────▼───────────┐
   │   LoggerContext        │  ├─ Loggers: Root Logger / 若干具名 Logger
   │  （Logger 树形层次结构）  │  │  每个 Logger 可挂 Filter，并引用多个 AppenderRef
   └────────────┬───────────┘
                │  AppenderRef（可挂 Filter）
   ┌────────────▼───────────┐
   │        Appender        │  ← 内部也可以挂 Filter
   └────────────┬───────────┘
                ▼
        ┌───────────────┐
        │    Layout     │
        └───────┬───────┘
                ▼
   Console / File / RollingFile / Socket / JDBC ...
```

+ Root Logger 下，可以配置多个 Logger，每个 Logger 有如下组件：
  + Appender: 用于定义日志的输出位置，比如控制台(Console)，文件(File)还是远程计算机(Socket)；
  + Filter: 用于对日志进行过滤，比如只输出 ERROR 级别日志；
  + Layout: 定义日志的输出格式，比如日志的日期、时间、方法名称等。

### 配置文件

log4j-2.x 主要是通过[配置文件](https://logging.apache.org/log4j/2.x/manual/configuration.html)进行设定。

默认会在 classpath 目录下按照如下优先级寻找配置文件（**注意文件名中间的 2**）：

查找顺序（**省略测试配置时**）：`log4j2.properties` > `log4j2.yaml`/`log4j2.yml` > `log4j2.json`/`log4j2.jsn` > `log4j2.xml`

完整的查找顺序（官方 `ConfigurationFactory` 的 `@Order` 顺序，数字越大越先被使用）：

1. `log4j2-test<contextName>.<ext>`（测试专用，优先级最高）
2. `log4j2-test.<ext>`
3. `log4j2<contextName>.<ext>`
4. `log4j2.<ext>`
5. 以上都没找到，则使用内置的 `DefaultConfiguration`，并在 Status Logger 里打印一条警告。

其中 `<ext>` 之间也有顺序：`properties`(Order 8) > `yaml`/`yml`(7) > `json`/`jsn`(6) > `xml`(5)，即 **XML 排在最后作为兜底**。

可以通过系统属性 `-Dlog4j2.configurationFile=xxx.xml` 覆盖配置文件位置。

关于格式选择的说明：

+ 从 version **2.4** 版本开始，log4j-2 才开始支持 `.properties` 格式；
+ `.yaml`/`.json` 格式**需要额外的 Jackson 依赖**（`jackson-dataformat-yaml` / `jackson-databind`）在 classpath 上，否则会报找不到对应的 `ConfigurationFactory`；
+ 考虑到 log4j-2 的配置是层次化的，且 XML 是唯一**不需要额外依赖**的格式，所以最推荐的还是 **XML**。

注意内置默认配置（`DefaultConfiguration`）的行为：**只输出 ERROR 及以上级别到控制台**，而不是输出全部日志。

log4j-2.x 的日志级别和优先级为：`OFF > FATAL > ERROR > WARN > INFO > DEBUG > TRACE > ALL`。

打印日志时，会打印 >= 所设置级别的日志，设置的日志等级越高，打印出来的日志就越少。

示例参见 resources 目录下的 `log4j2.xml`。

---

## JCL(Commons Logging)

JCL 全称 Jakarta Commons Logging，后来更名为 Commons Logging，是最早的门面型日志框架之一，Maven 坐标如下：

```xml
<dependency>
    <groupId>commons-logging</groupId>
    <artifactId>commons-logging</artifactId>
    <version>1.2</version>
</dependency>
```

（注：Spring Framework 4.x 之前、以及大量老项目的"传递依赖"里都带着它，这也是它虽然老但依然"阴魂不散"的原因。）

### 核心 API

JCL 的 API 非常小，只有两个核心接口：

+ `org.apache.commons.logging.Log`：日志接口，定义了 `trace/debug/info/warn/error/fatal` 等方法；
+ `org.apache.commons.logging.LogFactory`：工厂，用于获取 `Log` 实例。

```java
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommonsLogDemo {
    private static final Log log = LogFactory.getLog(CommonsLogDemo.class);

    public static void main(String[] args) {
        log.trace("Trace message ...");
        log.debug("Debug message ...");
        log.info("Info message ...");
        log.warn("Warn message ...");
        log.error("Error message ...");
        log.fatal("Fatal message ...");
    }
}
```

### 运行时自动发现（最容易踩坑的地方）

`LogFactory.getLog()` 会在运行时**按固定顺序**在 classpath 上找一个"能用"的后端：

1. 存在 `org.apache.log4j.Logger`（即引入了 log4j 1.x）→ 使用 **`Log4JLogger`**；
2. 否则存在 JUL（JDK 1.4+ 必然存在）→ 使用 **`Jdk14Logger`**；
3. 再否则使用内置的 **`SimpleLog`**（只输出到 System.err）。

这里的关键结论：

+ JCL **天生支持 Log4j 1.x**，不需要任何额外依赖；
+ JCL **不支持 Log4j 2.x**，只要 classpath 上没有 Log4j 1.x，它就会默默走 JUL；
+ 想要让 JCL 的日志统一走 SLF4J/Logback，做法是**移除 `commons-logging`，换成 `jcl-over-slf4j`**（同名同包的 API 替换实现）；
+ 想要让 JCL 的日志走 Log4j 2.x，做法是引入 `org.apache.logging.log4j:log4j-jcl` 桥接包。

### 相关依赖一览

| 目的 | 依赖（Maven 坐标） | 说明 |
| --- | --- | --- |
| JCL 本身 | `commons-logging:commons-logging` | 门面 + 自带 3 种后端发现逻辑 |
| JCL API → SLF4J | `org.slf4j:jcl-over-slf4j` | **替换** `commons-logging`，不是叠加 |
| JCL API → Log4j 2.x | `org.apache.logging.log4j:log4j-jcl` | 保留 `commons-logging`，由它做桥接 |

---

# Slf4j阵营

Slf4j 和 Logback 是同一个作者开发的，这两个组件一般一起使用，似乎没有单独使用 Logback 的教程——Logback官方文档也是配合Slf4j一起用，
因此这里就不分开介绍了。

官方文档：

+ [Slf4j](https://www.slf4j.org/index.html), 里面只有一个 [SLF4J user manual](https://www.slf4j.org/manual.html) 文档比较有用
+ [Logback](https://logback.qos.ch/index.html)

## Maven依赖

Logback分为 3 个依赖：

+ `logback-core`：核心依赖，它是其他两个依赖的基础
+ `logback-classic`：它实现了Slf4j的API，所以当想配合Slf4j使用时，需要将logback-classic加入classpath
+ `logback-access`：为了集成Servlet环境而准备的，可提供HTTP-access的日志接口

```xml
<!-- Slf4j 门面依赖 -->
<dependency>
  <groupId>org.slf4j</groupId>
  <artifactId>slf4j-api</artifactId>
  <version>1.7.36</version>
</dependency>
<!-- Logback依赖：logback-classic 会自动把 logback-core 作为传递依赖带进来，所以只引 classic 也可以 -->
<dependency>
  <groupId>ch.qos.logback</groupId>
  <artifactId>logback-core</artifactId>
  <version>1.2.13</version>
</dependency>
<dependency>
  <groupId>ch.qos.logback</groupId>
  <artifactId>logback-classic</artifactId>
  <version>1.2.13</version>
</dependency>
```

> 版本说明：以上版本与当前仓库 `pom.xml` 保持一致（`slf4j-api` 1.7.36 + `logback` 1.2.13）。
> **注意** `slf4j-api` 必须是 `compile` 作用域（不能写成 `<scope>test</scope>`），否则 `main` 下的代码会编译失败。
> 选用 Logback 1.2.x 是因为它是**最后一个兼容 JDK 8** 的大版本；Logback 1.3/1.4 分别要求 JDK 8+/11+（1.4 对应 SLF4J 2.0）。

## 架构

Logback 整体是基于如下 3 个组件（和Log4j-1.x一样）：

+ `Logger`：日志记录器，属于 logback-classic 依赖
+ `Appender`：日志输出目的地，属于 logback-core 依赖
  + 比如输出到控制台、文件、远程socket服务器、MySQL等
  + 一个`Logger`可以配置多个`Appender`
+ `Layout`：日志格式，属于 logback-core 依赖
  + `Layout`对象需要附加在某个`Appender`上，用于设置输出的日志格式

Logback中所有的`Logger`都是由一个`LoggerContext`对象（默认名称为`default`）来管理的，它以一个树形的层次结构来组织所有的`Logger`，最顶层的是`Root`Logger。

Logback配置文件名称默认是`logback.xml`，配置语法参考官方文档 [Chapter3: Logback configuration -&gt;Configuration file syntax](https://logback.qos.ch/manual/configuration.html#syntax)。

有如下配置节点：

+ 根节点`<configuration>`
  + `<contextName>`节点：应用上下文名称，可选
  + `<root>`节点：最顶层的Logger（名称就是`ROOT`），单独一个节点，只能有一个
  + `<logger>`节点：具体的Logger，定义日志从哪里获取以及级别，可以有多个
  + `<appender>`节点：配置日志格式、如何过滤、文件处理，可以有多个，它内部有如下节点：
    + `<encoder>`节点：负责转换日志事件为字节数组并将字节数组写入到输出流中，class属性设置输出格式的全限定类名
    + `<layout>`节点：配置日志输出形式的类，class属性设置输出格式的全限定类名，详细的配置格式可以参考官方文档 [Chapter 6: Layouts](https://logback.qos.ch/manual/layouts.html)
    + `<filter>`节点
  + `<property>`节点：定义常用变量，可选

> Appender中的`encoder`是在 0.9.19 版本引入的，它的引入是因为 layout 只能将日志事件转换为成 string，
> 而且在日志事件写出时不能进行更细致的控制，不能将日志事件批量聚合。
> 与之相反的是，encoder 不但可以完全控制字节写出时的格式，而且还可以控制这些字节什么时候被写出。

### 配置文件查找顺序

Logback 会在 classpath 下按如下顺序查找配置文件，**找到第一个就停止**：

1. `logback-test.xml`（测试专用，优先级最高，通常放在 `src/test/resources`）
2. `logback.xml`（生产/默认使用）
3. 都没有找到 → 使用内置的 `BasicConfigurator` 默认配置：**root 级别为 `DEBUG`，只输出到控制台**。

也可以通过系统属性指定：`-Dlogback.configurationFile=/path/to/logback.xml`。

其他常用配置节点：

+ `<include resource="..."/>`：引入其他配置文件片段；
+ `<define name="x" class="..."/>`：通过类/属性动态定义变量；
+ `<timestamp>`：配合 `<property>` 生成带时间戳的文件名；
+ `<shutdownHook class="ch.qos.logback.core.hook.DelayingShutdownHook"/>`：JVM 退出时优雅关闭 appender（避免异步日志丢数据）；
+ `<statusListener class="ch.qos.logback.core.status.OnConsoleStatusListener"/>`：输出 Logback 内部诊断日志，排查"配置不生效"时非常有用。

配置文件示例参见 resources 目录下的 `logback.xml`。

> **本仓库的一个实测结论**：当前 `pom.xml` 同时存在 `log4j-core` 和 `logback-classic`，而 SLF4J 只会绑定到 **`logback-classic`**（因为它是 classpath 上唯一的 SLF4J binding）。
> 所以 `Slf4jDemo` / `LogbackDemo` 实际输出走的是 **Logback**，`logback.xml` 才会生效；
> Log4j2 只能通过它自己的 `org.apache.logging.log4j.Logger` API 使用（`Log4j2Demo`），`log4j2.xml` 对 SLF4J API 不起作用。
> 这是"多个日志实现共存时谁生效"的典型例子，比单纯看理论更直观。

---

# JUL(Java Util Logging)

JUL 是 JDK 1.4 起自带的标准库日志（`java.util.logging`），**不需要任何第三方依赖**，但使用场景较少（配置能力、性能、生态都不如 Logback/Log4j2），主要用于"临时打印"或"不便于引入依赖"的场景（如 JDK 内部、`HttpURLConnection` 的调试）。

## 核心组件

+ `Logger`：日志记录器，通过 `Logger.getLogger(name)` 获取，同样有树形层次结构（按 `.` 分隔的包名自动构成父子关系），最顶层是根 logger（名称为空字符串 `""`）；
+ `Handler`：输出目的地，`ConsoleHandler`（控制台）、`FileHandler`（文件）、`SocketHandler`、`MemoryHandler` 等；
+ `Formatter`：格式，`SimpleFormatter`（一行文本）、`XMLFormatter`（XML）；
+ `Level`：级别，**名称与 Log4j/SLF4J 不同**，容易混淆；
+ `Filter`：过滤。

## 级别映射（重点，容易混淆）

JUL 的级别从高到低是：

```text
OFF > SEVERE > WARNING > INFO > CONFIG > FINE > FINER > FINEST > ALL
```

与其他框架的对应关系（近似）：

| SLF4J / Log4j | JUL 级别 | 备注 |
| --- | --- | --- |
| ERROR | `SEVERE` | `logger.severe("...")` |
| WARN | `WARNING` | `logger.warning("...")` |
| INFO | `INFO` | 同名 |
| DEBUG | `FINE` | |
| TRACE | `FINER` / `FINEST` | |

所以 `JulLogDemo` 里那句"error 对应 severe"只是部分正确：JUL 的 `CONFIG`/`FINE`/`FINER`/`FINEST` 在 SLF4J 里没有直接对应，SLF4J 的 DEBUG 映射到 `FINE`。

## 双重级别过滤（为什么 my 的 fine 不打印）

JUL 的日志要输出，必须同时通过两道级别检查：

1. `Logger` 自身的级别（默认继承父级，根 logger 默认是 `INFO`）；
2. `Handler` 自身的级别（默认 `ConsoleHandler` 也是 `INFO`）。

因此只设置 `logger.setLevel(Level.FINE)` 是**不够**的，还得设置 handler 的级别：

```java
Logger logger = Logger.getLogger("LogDemo");
logger.setLevel(Level.FINE);
// 关键的另一步：把 Handler 的级别也放开
for (Handler h : logger.getHandlers()) {
    h.setLevel(Level.FINE);
}
logger.fine("现在这条 fine 才会输出");
```

## 配置文件

JUL 的配置文件通常叫 `logging.properties`，通过系统属性指定：

```bash
-Djava.util.logging.config.file=src/main/resources/logging.properties
```

一个最小示例：

```properties
# 全局默认级别
.level = INFO
# 控制台 Handler 的级别与格式
java.util.logging.ConsoleHandler.level = FINE
java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter
# SimpleFormatter 的格式（%1 时间 %2 来源类 %3 来源方法 %4 级别 %5 消息）
java.util.logging.SimpleFormatter.format = %1$tF %1$tT [%4$s] %3$s: %5$s%6$s%n
# 指定某个 logger 的级别
pers.zxt.java.log.level = FINE
```

> 由于 `LogManager` 是 JVM 全局单例、且配置文件在第一次调用 `LogManager` 时就被读取，**运行期修改 `logging.properties` 不会自动生效**，需要调用 `LogManager.getLogManager().readConfiguration(...)` 重新加载。

---

# SLF4J 桥接 / 绑定全景

这是整个 Java 日志体系里**最容易迷路**的部分，务必分清"绑定(binding)"和"桥接(bridge)"：

+ **绑定(binding)**：SLF4J API → 某个日志实现。同一方向**只能存在一个**，否则会出现 multiple bindings 警告（SLF4J 1.7）甚至报错（SLF4J 2.0）。
+ **桥接(bridge)**：某个老的日志 API → 转发到 SLF4J。桥接包通常**与被桥接的原始包同名同包**，因此必须把原始包移除。

## 绑定（后端实现）

| 目的（SLF4J → 某后端） | 依赖 |
| --- | --- |
| Logback | `ch.qos.logback:logback-classic` |
| Log4j 1.x | `org.slf4j:slf4j-log4j12`（配 `log4j:log4j`） |
| Log4j 2.x（SLF4J 1.7） | `org.apache.logging.log4j:log4j-slf4j-impl` |
| Log4j 2.x（SLF4J 2.0） | `org.apache.logging.log4j:log4j-slf4j2-impl` |
| JUL | `org.slf4j:slf4j-jdk14` |
| 简单输出（无配置） | `org.slf4j:slf4j-simple` |
| 丢弃全部日志 | `org.slf4j:slf4j-nop` |

## 桥接（老 API → SLF4J）

| 目的（某老 API → SLF4J） | 依赖 | 是否需要移除原依赖 |
| --- | --- | --- |
| JCL(commons-logging) → SLF4J | `org.slf4j:jcl-over-slf4j` | **是**，必须移除 `commons-logging` |
| Log4j 1.x API → SLF4J | `org.slf4j:log4j-over-slf4j` | **是**，必须移除 `log4j:log4j` |
| JUL API → SLF4J | `org.slf4j:jul-to-slf4j` | 否，但需在启动时安装 `SLF4JBridgeHandler` |

`jul-to-slf4j` 需要额外代码：

```java
// 安装桥接：把 JUL 的日志重定向到 SLF4J
SLF4JBridgeHandler.removeHandlersForRootLogger();
SLF4JBridgeHandler.install();
```

## 其他方向的桥接

| 目的 | 依赖 | 说明 |
| --- | --- | --- |
| Log4j 1.x API → Log4j 2.x | `org.apache.logging.log4j:log4j-1.2-api` | 让老代码不改 API 就跑到 Log4j2 上 |
| JCL API → Log4j 2.x | `org.apache.logging.log4j:log4j-jcl` | 保留 commons-logging 的 API |
| Log4j 2.x API → 其他实现 | `org.apache.logging.log4j:log4j-to-slf4j` | 让 Log4j2 的 API 转发到 SLF4J（Spring Boot 用它统一到 Logback） |

> **口诀**：`xxx-over-slf4j` 是"把 xxx 的调用转给 SLF4J"（要移除 xxx）；`log4j-xxx` 是"Log4j 官方提供、接入别人"；`xxx-impl` 是"SLF4J 的具体实现绑定"。
> 另外要区分 `log4j-slf4j-impl`（SLF4J 是 API、Log4j2 是后端）和 `log4j-to-slf4j`（Log4j2 是 API、SLF4J 是后端），方向正好相反。

---

# 依赖冲突排查

多个日志实现 / 多个 binding 共存在真实项目里非常常见，排查手段：

1. **看依赖树**：

   ```bash
   mvn dependency:tree -Dincludes=org.slf4j,ch.qos.logback,log4j:log4j,org.apache.logging.log4j,commons-logging
   ```
   或直接用 IDEA 的 Maven 面板 / Maven Helper 插件查看冲突。

2. **看启动日志**：
   + SLF4J 1.7.x：会出现 `SLF4J: Class path contains multiple SLF4J bindings.` 并列出冲突的 jar；
   + SLF4J 2.0.x：会直接报错并**点名**应该排除哪个 artifact。

3. **用 exclusions 排除传递依赖**：

   ```xml
   <dependency>
       <groupId>com.example</groupId>
       <artifactId>some-lib</artifactId>
       <exclusions>
           <exclusion>
               <groupId>commons-logging</groupId>
               <artifactId>commons-logging</artifactId>
           </exclusion>
           <exclusion>
               <groupId>log4j</groupId>
               <artifactId>log4j</artifactId>
           </exclusion>
       </exclusions>
   </dependency>
   ```

4. **Spring Boot 场景**：`spring-boot-starter-logging` 已经帮你排除了 `commons-logging`（用 `spring-jcl` 替代）、`log4j` 1.x，并统一到 SLF4J + Logback。所以受管的依赖不要手动再加 `commons-logging`。

---

# Spring Boot 日志

Spring Boot 3.x/2.x 的日志体系都是 **SLF4J + Logback**，由 `spring-boot-starter-logging` 自动引入（它是 `spring-boot-starter` 的传递依赖）。

## 版本对应关系（升级时很有用）

| Spring Boot | SLF4J | Logback | 最低 JDK |
| --- | --- | --- | --- |
| 2.x | 1.7.x | 1.2.x | JDK 8 |
| 3.x | 2.0.x | 1.4.x | JDK 17 |

## 常用配置（application.yml）

```yaml
logging:
  # 设置某个包/类的级别，也可以用 group 分组
  level:
    root: info
    pers.zxt: debug
  # 日志文件（二者选一，name 优先于 path）
  file:
    name: logs/app.log
  # 指定外部配置文件（Logback 用 logback-spring.xml，见下文）
  config: classpath:logback-spring.xml
  # 日志分组
  group:
    web: org.springframework.web,org.springframework.boot.web
  logback:
    rollingpolicy:
      max-file-size: 10MB
      max-history: 7
```

## 切换到 Log4j2

```xml
<dependencies>
    <!-- 排除默认的 logback -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
        <exclusions>
            <exclusion>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-logging</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    <!-- 引入 log4j2 starter，它会自动带上 log4j-slf4j2-impl（SLF4J 2.0） -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-log4j2</artifactId>
    </dependency>
</dependencies>
```

## Spring 特有的扩展

Logback 在 Spring Boot 中可以用 `logback-spring.xml`（而不是 `logback.xml`），从而获得两个 Spring 专属标签：

+ `<springProfile name="dev">`：按 profile 切换配置；
+ `<springProperty scope="context" name="appName" source="spring.application.name"/>`：把 Spring 的配置注入到 Logback 变量里。

> 命名区别的实质：`logback.xml` 由 Logback 自己在**很早**的时机加载（此时 Spring 环境还没准备好），所以用不了 Spring 标签；`logback-spring.xml` 由 Spring Boot 接管加载，因此能识别 `<springProfile>` 等扩展。

---

# MDC / ThreadContext 与链路追踪

MDC（Mapped Diagnostic Context）本质是一个与**当前线程**绑定的 `Map<String,String>`，可以在日志模板里通过 `%X{key}` 输出，常用来打 traceId。

## Logback（SLF4J 的 MDC）

```java
import org.slf4j.MDC;

MDC.put("traceId", "abc-123");
log.info("处理请求");
MDC.clear();   // 务必清理，线程池复用线程时会串号
```

`logback.xml` 中：

```xml
<pattern>%d{HH:mm:ss} [%thread] %-5level [%X{traceId}] %logger{36} - %msg%n</pattern>
```

## Log4j2（ThreadContext）

Log4j2 中对应的是 `ThreadContext`（提供了 `put` / `push` / `getImmutableStackOrNull` 等更丰富的 API）：

```java
import org.apache.logging.log4j.ThreadContext;

ThreadContext.put("traceId", "abc-123");
ThreadContext.clearAll();
```

`log4j2.xml` 中同样用 `%X{traceId}`（也可写 `%mdc{traceId}`）。

## 坑：异步 / 线程池下 MDC 会丢

MDC 基于 `ThreadLocal`，所以：

+ 使用 `@Async`、`CompletableFuture`、线程池时，子线程读不到父线程的 MDC；
+ 手动同步上下文（如自定义 `TaskDecorator`）、或用 `InheritableThreadLocal`、或借助 Micrometer Tracing / SkyWalking 等链路组件；
+ 请求处理结束一定要 `clear()`，否则线程复用时会把上一个请求的 traceId 带到下一个请求。

---

# 异步日志

高并发下，同步写日志会阻塞业务线程，此时可用异步日志。**代价是"丢失定位信息"**：异步场景下 `%L`/`%F`/`%M`/`%C` 需要抓取调用栈，开销很大（Log4j2 默认在异步 logger 上关闭 location）。

## Logback：`AsyncAppender`

```xml
<appender name="ASYNC" class="ch.qos.logback.classic.AsyncAppender">
    <!-- 队列大小，默认 256 -->
    <queueSize>1024</queueSize>
    <!-- 丢弃阈值：队列剩余容量低于该值时，丢弃 TRACE/DEBUG/INFO 事件（默认 queueSize 的 20%） -->
    <discardingThreshold>0</discardingThreshold>
    <!-- true 表示队列满时直接丢弃，不阻塞业务线程 -->
    <neverBlock>false</neverBlock>
    <!-- 被包裹的真实 appender -->
    <appender-ref ref="file"/>
</appender>
```

+ `AsyncAppender` 内部仍然会为每个事件抓取线程名/调用位置，只是写操作异步化；
+ `discardingThreshold=0` 表示**任何级别都不丢弃**（保护 WARN/ERROR 不被丢掉）；
+ 程序退出前建议配置 `DelayingShutdownHook` 或手动停止 `LoggerContext`，否则队列里未刷出的事件会丢失。

## Log4j2：`AsyncLogger` / `AsyncAppender`

+ `AsyncAppender`：逻辑与 Logback 类似，包裹具体 appender；
+ `AsyncLogger`：基于 **LMAX Disruptor 无锁环形队列**，性能最好，**需要额外依赖** `com.lmax:disruptor`；通过 `<AsyncRoot>` / `<AsyncLogger>` 配置，或用系统属性 `-Dlog4j2.contextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector` 全局异步。

```xml
<Loggers>
    <!-- 全异步：Root 换成 AsyncRoot -->
    <AsyncRoot level="info">
        <AppenderRef ref="console"/>
    </AsyncRoot>
</Loggers>
```

---

# 日志写法与安全最佳实践

## 写法

+ **优先使用参数化**（延迟字符串拼接，只有真的会输出时才构造消息）：

  ```java
  log.info("user={} orderId={}", user, orderId);   // 推荐
  // log.info("user=" + user + " orderId=" + orderId);  // 不推荐：即使不输出也会拼接
  ```

+ 仅当日志内容构造昂贵时才用 `isXxxEnabled()`：

  ```java
  if (log.isDebugEnabled()) {
      log.debug("big payload: {}", costlyToJson(obj));
  }
  ```

+ 异常必须把 `Throwable` 作为**最后一个参数**传入，而不是拼进消息里（否则会丢掉堆栈）：

  ```java
  log.error("处理订单失败, orderId={}", orderId, e);   // 推荐，会打印堆栈
  // log.error("处理订单失败: " + e.getMessage());        // 不推荐，丢失堆栈
  ```

+ `%L`/`%F`/`%C`/`%M`（行号/文件名/类名/方法名）会强制抓取调用栈，**生产环境慎用或不要用**。

+ Log4j2 若要用 `String.format` 风格，需用 `LogManager.getFormatterLogger()`，且此时 `{}` 占位符会失效。

## 安全

+ **Log4j 1.x 已停止维护，且存在已披露漏洞**：`CVE-2019-17571`（`SocketServer` 反序列化）、`CVE-2022-23307`（`Chainsaw` 反序列化）、`CVE-2021-4104`（`JMSAppender` 反序列化）等 —— 这比"停止维护"更值得作为迁移理由。
+ **Log4j 2.x 必须升级到 2.17.1 以上**：`CVE-2021-44228`（Log4Shell，JNDI lookup 远程代码执行）及后续 `CVE-2021-45046` / `CVE-2021-45105` / `CVE-2021-44832` 系列。本仓库使用的 **2.20.0 是安全版本**。
+ 不要轻易在日志里输出用户可控的字符串并使其被当作 lookup 解析（如 `%m{lookups}`）；`PatternLayout` 的 `%msg{lookups}` 在 2.15+ 已默认禁用 lookup 求值。
+ 日志中避免直接落敏感信息（密码、身份证、Token），必要时脱敏。

---

# 常用工具日志集成

+ Spark 2.x 使用的是 Slf4j，不过后端搭配的是 Log4j 1.x（最后一个版本 1.2.17），见 `org.apache.spark.internal.Logging.scala` 源码。
  + 因此在本地调试 Spark/Flink 时，需要把 classpath 上 `slf4j-log4j12` 与 `log4j.properties` 准备好；同时如果 classpath 上另有 Logback（`logback-classic`），**SLF4J 会优先绑定到先出现的那个**，可能出现 `log4j.properties` 不生效的现象。本仓库 `log4j.properties` 头部的注释正是在说明这一点。
+ Flink 同样使用 SLF4J 门面，日志后端通常也是 Log4j2 或 Log4j 1.x，通过 `log4j.properties` / `log4j2.properties` 调节第三方包的输出级别（如 `org.apache.flink.runtime` 调到 `WARN`），减少刷屏。

---

# 本仓库 Demo 与配置的对应关系

| Demo / 配置 | 使用的框架 | 生效的配置文件 |
| --- | --- | --- |
| `JulLogDemo.java` | JUL（JDK 自带） | 无（或 `logging.properties`，当前未提供） |
| `Log4j2Demo.java` | Log4j2 API（`org.apache.logging.log4j.Logger`） | `log4j2.xml` |
| `LogbackDemo.java`（SLF4J API） | SLF4J → Logback | `logback.xml` |
| `Slf4jDemo.java`（Lombok `@Slf4j`） | SLF4J → Logback | `logback.xml` |
| `CommonsLogDemo.java` | JCL（需自行引入 `commons-logging`） | 取决于 classpath，当前为 JUL |
| `log4j.properties` | Log4j 1.x（**当前未启用**） | 当前不生效，仅作参考 |

> 注意：`log4j2.xml`、`logback.xml`、`log4j.properties` 三者**同名不同**（`log4j2` / `logback` / `log4j`），各自被各自的框架查找，**放在同一个 `resources` 目录下不会互相覆盖**。
> 真正决定"谁生效"的是 classpath 上有哪些 API/实现 jar，以及 SLF4J 绑定到了谁。

---

# 参考资料汇总

+ SLF4J：[官方网站](https://www.slf4j.org/) / [SLF4J user manual](https://www.slf4j.org/manual.html) / [桥接与绑定说明](https://www.slf4j.org/legacy.html)
+ Logback：[官方网站](https://logback.qos.ch/) / [Chapter 3: Configuration](https://logback.qos.ch/manual/configuration.html)
+ Log4j2：[官方网站](https://logging.apache.org/log4j/2.x/) / [Configuration](https://logging.apache.org/log4j/2.x/manual/configuration.html) / [Appenders](https://logging.apache.org/log4j/2.x/manual/appenders.html) / [Layouts](https://logging.apache.org/log4j/2.x/manual/layouts.html)
+ Log4j 1.x：[官方网站（已退役）](https://logging.apache.org/log4j/1.x/)
+ JCL：[Apache Commons Logging](https://commons.apache.org/proper/commons-logging/)
