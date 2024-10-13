package pers.zxt.spring.web.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import pers.zxt.spring.web.domain.Student;
import pers.zxt.spring.web.service.StudentService;


/**
 * 这个 Servlet 部署在Tomcat中之后，会根据 web.xml 配置中定义好的URL映射，处理对应的请求，这样就有两种模式可以选择：
 *   (1) web.xml 中，每个URL配置一个Servlet，也就是在 web.xml 手动维护具体的URL mapping
 *   (2) web.xml 中，所有的（或者一大类）的URL配置一个Servlet，在该Servlet中，再进行URL mapping的分配
 * 现在的 MVC 开发模式中，广泛采用的是第二种方式，此时 web.xml 中配置的那个”总“的 Servlet，起到的作用就是 front-controller.
 * 在接下来的 spring-mvc 中，这个 front-controller 就是交由 spring-mvc 框架管理,而不需要手动编写。
 */
public class QueryStudentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    // get请求
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取 request 的参数，这里的get不需要参数，
        //String stuId = request.getParameter("stuid");

        // 下面这种创建spring容器的方式，每次请求都会创建，开销太大
        // -------------------------------------------------------------------
        //创建容器，获取service
        //String config= "spring-start-beans.xml";
        //ApplicationContext ctx  = new ClassPathXmlApplicationContext(config);
        // -------------------------------------------------------------------

        // 使用监听器已经创建好的容器对象，从ServletContext作用域获取容器，需要先获取 ServletContext 对象
        ServletContext sc  = getServletContext(); // ServletContext, servlet中的方法
        //ServletContext sc = request.getServletContext(); // HttpServletRequest对象的方法

        // 从 ServletContext 对象中获取 spring 容器对象 有两种方式：
        // 第一种：使用 key 获取spring容器
        //WebApplicationContext ctx  = null;
        //String webAppContextKey = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
        //Object attr  = sc.getAttribute(webAppContextKey);
        //if( attr != null){
        //    ctx = (WebApplicationContext) attr;
        //}
        // 第二种，使用spring框架提供的工具类
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
        // 很明显，第二种方式方便。实际上，点进去第二种方式的源码，也是通过第一种方式里面使用key获取的，只不过做了封装而已

        System.out.println("在servlet中创建的对象容器: " + ctx);
        // 从容器中获取 StudentService 对象
        StudentService service = (StudentService) ctx.getBean("studentService");

        List<Student> students = service.queryStudent();
        for (Student stu: students){
            System.out.println("student对象: " + stu);
        }

        Student student = students.get(0);
        request.setAttribute("stu", student);
        request.getRequestDispatcher("/show.jsp").forward(request, response);

    }
}
