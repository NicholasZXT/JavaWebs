package pers.zxt.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import javax.annotation.Resource;
import pers.zxt.ssm.domain.Student;
import pers.zxt.ssm.service.StudentService;



@Controller
@RequestMapping("/student")
public class StudentController {

    @Resource
    private StudentService service;

    @RequestMapping("/queryStudent.do")
    @ResponseBody
    public List<Student> queryStudent(){
        //参数检查， 简单的数据处理
        List<Student> students = service.listStudents();
        return students;
    }
}
