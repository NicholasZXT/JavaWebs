package pers.zxt.springboot.security;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;


public class SecurityApplication {

    static {
        System.setProperty("spring.config.location", "classpath:/springboot-security/");
    }

    public static void main (String[] args) {
        //SpringApplication.run(SaTokenApplication.class,args);

        ApplicationContext ctx  = SpringApplication.run(SecurityApplication.class, args);
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
