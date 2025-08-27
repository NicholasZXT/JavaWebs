package pers.zxt.springboot.ssm;

import java.util.Arrays;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.mybatis.spring.annotation.MapperScan;

/**
 *
 */
@SpringBootApplication
// SpringBoot 接入 MyBatis 的第1种方式：在启动类上使用 @MapperScan 注解，指定待扫描的 dao 接口所在包
// 第2种方式是在 DAO 接口上使用 @Mapper 注解进行标识
@MapperScan(basePackages = {"pers.zxt.springboot.ssm.dao"})
public class MySsmApplication {

    static {
        // 设置配置文件位置
        System.setProperty("spring.config.location", "classpath:/springboot-ssm/");
    }

    public static void main(String[] args) {
        System.out.println("启动参数：" + Arrays.toString(args));

        ApplicationContext ctx  = SpringApplication.run(MySsmApplication.class, args);
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
