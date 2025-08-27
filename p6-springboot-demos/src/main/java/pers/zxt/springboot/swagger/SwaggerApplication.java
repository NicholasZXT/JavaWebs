package pers.zxt.springboot.swagger;

import java.util.Arrays;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SwaggerApplication {

    static {
        // 设置配置文件位置
        System.setProperty("spring.config.location", "classpath:/springboot-swagger/");
    }

    public static void main(String[] args) {
        System.out.println("启动参数：" + Arrays.toString(args));

        ApplicationContext ctx  = SpringApplication.run(SwaggerApplication.class, args);
        System.out.println("ApplicationContext started.");

        String[] beans = ctx.getBeanDefinitionNames();
        int bean_num = beans.length;
        System.out.println("ApplicationContext beans[" + bean_num + "]:");
        int num = 1;
        for (String bean: beans) {
            System.out.println("[" + num + "]: " + bean);
            num++;
        }
    }
}
