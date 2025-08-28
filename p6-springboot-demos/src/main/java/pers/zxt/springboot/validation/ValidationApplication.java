package pers.zxt.springboot.validation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class ValidationApplication {

    static {
        System.setProperty("spring.config.location", "classpath:/springboot-validation/");
        //System.setProperty("spring.profiles.active", "dev");
    }

    public static void main(String[] args) {
        ApplicationContext ctx  = SpringApplication.run(ValidationApplication.class, args);
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
