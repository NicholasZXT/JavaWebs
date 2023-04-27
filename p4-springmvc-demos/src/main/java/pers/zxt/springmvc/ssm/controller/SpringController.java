package pers.zxt.springmvc.ssm.controller;

import javax.servlet.ServletContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 要想在 spring-mvc 容器里的 controller 类里，获取 spring容器 对象的应用，必须要实现 ApplicationContextAware 接口
 */
@Controller
@RequestMapping("/spring")
public class SpringController implements ApplicationContextAware {

    // 这个成员变量用于持有 spring容器 对象的应用
    private WebApplicationContext context;
    // ApplicationContextAware 接口的方法，用于给上面的 context 变量赋予 spring容器 对象的引用
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context=(WebApplicationContext) applicationContext;
    }

    @RequestMapping(value = "/hello.do", method = RequestMethod.GET)
    @ResponseBody
    public String hello(){
        return "Hello, Here is Spring containers demo in SpringMVC.";
    }

    @RequestMapping(value = "/containers.do", method = RequestMethod.GET)
    @ResponseBody
    public String getSpringContainers(){
        ServletContext sc = this.context.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(sc);
        assert webApplicationContext != null;
        String[] beans = webApplicationContext.getBeanDefinitionNames();
        StringBuilder res = new StringBuilder();
        for(String bean: beans){
            res.append(bean).append(",").append("<br>");
        }
        // 返回 spring 容器中的所有 bean 对象名称
        return res.toString();
    }
}
