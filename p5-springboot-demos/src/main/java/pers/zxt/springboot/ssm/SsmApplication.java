package pers.zxt.springboot.ssm;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 接入 mybatis 的第二种方式：在启动类上使用 @MapperScan 注解，指定 dao 接口的包
 */
@SpringBootApplication
@MapperScan(basePackages = {"pers.zxt.springboot.ssm.dao"})
public class SsmApplication {

    // 启动的时候，注意加上命令行参数 --spring.config.location=classpath:/springboot-ssm/
    public static void main(String[] args) {
        SpringApplication.run(SsmApplication.class, args);
    }
}
