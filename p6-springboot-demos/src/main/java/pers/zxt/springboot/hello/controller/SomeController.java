package pers.zxt.springboot.hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pers.zxt.springboot.hello.MyDataSource;

// 使用 @Controller 注解标识当前类作为 Spring-MVC 的 次级Controller 使用
@Controller
public class SomeController {

    // 访问 springboot-hello/application.yml 配置文件中的值
    // 这个操作一般在service或者dao里执行，这里为了方便演示，就直接在controller里访问了
    // -------------------------------
    // 访问配置文件中的单个值
    @Value("${server.port}")
    private Integer port;

    @Value("${dirs.docDir}")
    private String docDir;

    @Value("${dirs.tempDir}")
    private String tempDir;

    @Value("${profile}")
    private String profile;

    // 使用自动装配将yml等配置文件中的数据封装到一个Environment对象中
    @Autowired
    private Environment env;

    @Autowired
    private MyDataSource myDataSource;
    // ---------------------------------------

    @RequestMapping("/hello")
    @ResponseBody
    public String doSome(){
        return "Hello SpringBoot !";
    }

    @RequestMapping(value = "/values", method = RequestMethod.GET)
    @ResponseBody
    public String getValues(){
        return "application values: " +
                "server.port=" + this.port +
                ", dirs.docDir=" + this.docDir +
                ", dirs.tempDir=" + this.tempDir +
                ", profile=" + this.profile + " .";
    }

    @RequestMapping(value = "/envs", method = RequestMethod.GET)
    @ResponseBody
    public String getEnvs(){
        this.env.getProperty("homeDir");
        return "application env values: " +
                "server.port=" + this.env.getProperty("server.port") +
                ", homeDir=" + this.env.getProperty("homeDir") +
                ", dirs.docDir=" + this.env.getProperty("dirs.docDir") +
                ", dirs.tempDir=" + this.env.getProperty("dirs.tempDir") +
                ", profile=" + this.env.getProperty("profile") + " .";
    }

    @RequestMapping("/dataSource")
    @ResponseBody
    public String getDataSource(){
        return myDataSource.toString();
    }
}
