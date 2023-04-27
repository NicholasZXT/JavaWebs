package pers.zxt.springboot.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * 1. 使用 @SpringBootApplication 注解标识springboot启动入口类，其中的main方法中执行 SpringApplication.run(MyApplication.class,args)
 *    来启动springboot应用；
 * 2. @SpringBootApplication注解的启动入口类必须要在整个应用包结构的最顶层，因为默认只扫描 启动类所在的包及其子包 下的 bean 对象；
 * 3. springboot默认的配置文件名称为 application.properties 或者 application.yml：
 *   (1) yml配置的优先级比较高。
 *   (2) 配置文件的默认路径是 classpath，也就是 resources 下。
 *   (3) 如果像这里把配置文件都放在了 resources/springboot-conf 目录下，就必须指定命令行参数
 *       --spring.config.location=classpath:/springboot-conf/，这样才能找到配置文件，注意，末尾的反斜杠也必须要有，表示是文件夹而不是文件。
 *   (4) 可以使用 --spring.config.name 来指定配置文件名称，而不是使用默认的 application.xxx 。
 *   (5) application.propertites 配置文件里，尽量不要有中文，否则会导致乱码。
 */
@SpringBootApplication
public class MyApplication {

    // 启动的时候，注意加上命令行参数 --spring.config.location=classpath:/springboot-conf/
    public static void main(String[] args) {
        // main方法里启动springboot应用的通用代码
        //SpringApplication.run(MyApplication.class,args);

        // run 方法返回的实际上是springboot的Context对象，可以获取其中装配的 bean 对象
        ApplicationContext ctx  = SpringApplication.run(MyApplication.class, args);
        String[] beans = ctx.getBeanDefinitionNames();
        int bean_num = beans.length;
        System.out.println("ApplicationContext beans[" + bean_num + "]: ");
        for(String bean: beans){
            System.out.println(bean);
        }
    }
}

