package pers.zxt.springboot.ssm.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.zxt.springboot.ssm.domain.Student;
import pers.zxt.springboot.ssm.service.StudentService;


@Controller
@RequestMapping("/students")
public class StudentController {

    @Resource
    private StudentService service;

    @RequestMapping("/hello.do")
    @ResponseBody
    public String hello(){
        return "Hello, Here is SpringBoot student-demo.";
    }

    // 返回已有学生
    @RequestMapping("/listStudent.do")
    @ResponseBody
    public List<Student> listStudent(){
        //参数检查， 简单的数据处理
        List<Student> students = service.listStudents();
        return students;
    }

}
