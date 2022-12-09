package pers.spring.web.controller;

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

import pers.spring.web.domain.Student;
import pers.spring.web.service.StudentService;

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
        //String config= "spring-beans.xml";
        //ApplicationContext ctx  = new ClassPathXmlApplicationContext(config);
        // -------------------------------------------------------------------

        // 直接使用监听器已经创建好的容器对象， 从ServletContext作用域获取容器
        WebApplicationContext ctx  = null;
        String key = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
        ServletContext sc  = getServletContext(); // ServletContext, servlet中的方法
        //ServletContext sc = request.getServletContext();// HttpServletRequest对象的方法

        // 获取spring容器
        Object attr  = sc.getAttribute(key);
        if( attr != null){
            ctx = (WebApplicationContext) attr;
        }
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
