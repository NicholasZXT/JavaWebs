package pers.zxt.springboot.satoken;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import cn.dev33.satoken.SaManager;

public class SaTokenApplication {

    static {
        System.setProperty("spring.config.location", "classpath:/springboot-satoken/");
    }

    public static void main (String[] args) {
        //SpringApplication.run(SaTokenApplication.class,args);

        ApplicationContext ctx  = SpringApplication.run(SaTokenApplication.class, args);
        System.out.println("ApplicationContext started.");
        String[] beans = ctx.getBeanDefinitionNames();
        int bean_num = beans.length;
        System.out.println("ApplicationContext beans[" + bean_num + "]:");
        int num = 1;
        for (String bean: beans) {
            System.out.println("[" + num + "]: " + bean);
            num++;
        }

        System.out.println("启动成功，Sa-Token 配置如下：" + SaManager.getConfig());

    }

}
