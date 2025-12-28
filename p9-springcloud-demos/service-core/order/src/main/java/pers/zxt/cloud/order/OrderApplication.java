package pers.zxt.cloud.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients     // 开启 OpenFeign
@EnableDiscoveryClient  // 开启 Nacos 服务注册与发现
@SpringBootApplication
public class OrderApplication {

    public static void main(String[] args) {
        ApplicationContext ctx  = SpringApplication.run(OrderApplication.class, args);
        Environment env = ctx.getEnvironment();
        String profile = env.getActiveProfiles()[0];
        System.out.println(">>> ApplicationContext started with profile: " + profile);
        System.out.println("Raw config content: " + env.getProperty("conf", "conf not found"));
        System.out.println("conf.env = " + env.getProperty("conf.env", "conf.env not found"));
        System.out.println("conf.service = " + env.getProperty("conf.service", "conf.service not found"));
        System.out.println("conf.group = " + env.getProperty("conf.group", "conf.group not found"));
        System.out.println("conf.some = " + env.getProperty("conf.some", "conf.some not found"));
    }

}
