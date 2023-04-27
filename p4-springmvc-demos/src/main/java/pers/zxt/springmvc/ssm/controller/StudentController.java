package pers.zxt.springmvc.ssm.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.zxt.springmvc.ssm.domain.Student;
import pers.zxt.springmvc.ssm.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {

    // 这里的 Service 对象是存放在spring容器而不是spring-mvc容器中的，但是因为 spring容器 是 spring-mvc容器的父容器
    // 所以这里通过 autowire 的方式，自动注入了
    @Resource
    private StudentService service;

    @RequestMapping("/hello.do")
    @ResponseBody
    public String hello(){
        return "Hello, Here is SpringMVC student-demo.";
    }

    // 返回已有学生
    @RequestMapping("/queryStudent.do")
    @ResponseBody
    public List<Student> queryStudent(){
        //参数检查， 简单的数据处理
        List<Student> students = service.listStudents();
        return students;
    }

    //注册学生
    //@RequestMapping("/addStudent.do")
    //public ModelAndView addStudent(Student student){
    //    ModelAndView mv = new ModelAndView();
    //    String tips = "注册失败";
    //    //调用service处理student
    //    int nums = service.addStudent(student);
    //    if( nums > 0 ){
    //        //注册成功
    //        tips = "学生【" + student.getName() + "】注册成功";
    //    }
    //    //添加数据
    //    mv.addObject("tips",tips);
    //    //指定结果页面
    //    mv.setViewName("result");
    //    return mv;
    //}
}
