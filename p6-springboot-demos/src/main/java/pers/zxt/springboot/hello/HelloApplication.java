package pers.zxt.springboot.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 1. 使用 @SpringBootApplication 注解标识 SpringBoot 启动入口类，
 *    其中的 main方法 中执行 SpringApplication.run(HelloApplication.class, args) 来启动SpringBoot应用；
 * 2. @SpringBootApplication注解 的启动入口类必须要在整个应用包结构的最顶层，因为默认只扫描 启动类所在的包及其子包 下的 bean 对象；
 * 3. SpringBoot默认的配置文件名称为 application.properties 或者 application.yml：
 *     (1) yml配置的优先级比较高。
 *     (2) 配置文件的默认路径是 classpath，也就是 resources 下。
 *     (3) 如果像这里把配置文件都放在了 resources/springboot-hello 目录下，需要采用下面的方式进行设置
 *     (4) application.properties 配置文件里，不要有中文，否则会导致乱码。
 */
@SpringBootApplication
public class HelloApplication {
    /**
     * 这里需要指定为使用 springboot-hello/application.yml 配置文件，有如下几种方式实现：
     * 1. 启动的时候，指定命令行参数 --spring.config.location=classpath:/springboot-hello/
     * 2. 启动类的静态初始化代码块里，使用 System.setProperty("spring.config.location", "classpath:/springboot-hello/");
     * 3. 在 main 方法里，使用 System.setProperty("spring.config.location", "classpath:/springboot-hello/");
     * 4. 在 main 方法里，使用如下方式：
     *   ```java
     *      // 创建SpringApplication实例
     *      SpringApplication app = new SpringApplication(MyApplication.class);
     *      // 设置配置文件位置
     *      app.setAdditionalProfiles("classpath:/springboot-hello/");
     *      // 运行应用
     *      ApplicationContext ctx = app.run(args);
     *   ```
     * 推荐使用静态代码块的方式。
     * 需要注意的点如下：
     *   - 时机很重要：设置配置位置的代码必须在 SpringApplication.run() 执行之前完成。
     *   - 路径格式：使用 classpath:/springboot-hello/ 格式，注意末尾的斜杠也必须要有，表示是文件夹而不是文件。
     *   - 优先级：命令行参数 > 系统属性 > 环境变量 > application.properties中的配置。
     *   - 覆盖问题：命令行参数的优先级最高，如果同时在代码中设置了系统属性又在命令行传参，命令行参数会覆盖代码中的设置。
     */
    static {
        // 设置配置文件位置
        System.setProperty("spring.config.location", "classpath:/springboot-hello/");

        // 通过这种方式设置logback和log4j的配置文件，因为这两个文件不在标准路径（src/main/resources）下
        // 等价于启动时使用下面两种方式：
        //   -Dlogback.configurationFile=src/main/resources/springboot-hello/logback.xml
        //   -Dlog4j.configurationFile=src/main/resources/springboot-hello/log4j2.xml
        //System.setProperty("logback.configurationFile", "src/main/resources/springboot-hello/logback.xml");
        //System.setProperty("log4j.configurationFile", "src/main/resources/springboot-hello/log4j2.xml");
    }

    public static void main (String[] args) {
        // main方法里启动springboot应用的通用代码
        //SpringApplication.run(HelloApplication.class,args);

        // run 方法返回的实际上是 SpringBoot的 Context对象，可以获取其中装配的 bean 对象
        ApplicationContext ctx  = SpringApplication.run(HelloApplication.class, args);
        Environment env = ctx.getEnvironment();
        String profile = env.getActiveProfiles()[0];
        System.out.println(">>> ApplicationContext started with profile: " + profile);
        //showSpringContextBeans(ctx);
        showSpringContextBeansOfUserDefined(ctx, "pers.zxt.springboot.hello");

        // 展示 Spring Config API 获取的数据
        System.out.println("===== Spring Config API 获取的数据 =====");

        System.out.println("some.name = " + env.getProperty("some.name", "some.name not found"));
        System.out.println("some.value = " + env.getProperty("some.value", "some.value not found"));

    }

    /**
     * 显示Spring容器中所有 Bean
     * @param ctx
     */
    public static void showSpringContextBeans(ApplicationContext ctx){
        // 1. 获取所有 Bean 的名称数组
        String[] allBeanNames = ctx.getBeanDefinitionNames();
        // 2. 遍历打印所有 Bean
        System.out.println("===== Spring 容器中所有 Bean 列表（共 " + allBeanNames.length + " 个） =====");
        int num = 1;
        for (String beanName : allBeanNames) {
            // 获取 Bean 实例（可选，按需获取）
            Object beanInstance = ctx.getBean(beanName);
            // 打印 Bean 名称 + Bean 类型
            System.out.printf("[" + num + "]: Bean 名称：%-15s | Bean 类型：%s%n", beanName, beanInstance.getClass().getName());
        }
    }

    /**
     * 显示Spring容器中指定包下的所有 Bean
     * @param applicationContext
     * @param packageName
     */
    public static void showSpringContextBeansOfUserDefined(ApplicationContext applicationContext, String packageName){
        // 1. 获取所有 Bean 的名称数组
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        System.out.println("===== 用户自定义 Bean 列表（按包名前缀过滤） =====");
        int userBeanCount = 0;
        for (String beanName : allBeanNames) {
            Object beanInstance = applicationContext.getBean(beanName);
            String beanClassName = beanInstance.getClass().getName();
            // 2. 判断该 Bean 的类名是否以用户包名前缀开头
            if (beanClassName.startsWith(packageName)) {
                userBeanCount++;
                System.out.printf("[" + userBeanCount + "]: Bean 名称：%-15s | Bean 类型：%s%n", beanName, beanClassName);
            }
        }
        System.out.println("===== 用户自定义 Bean 总数：" + userBeanCount + " 个 =====");
    }
}

