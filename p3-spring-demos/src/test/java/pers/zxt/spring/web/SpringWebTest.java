package pers.zxt.spring.web;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.List;

import pers.zxt.spring.web.domain.Student;
import pers.zxt.spring.web.service.StudentService;

public class SpringWebTest {
    @Test
    public void test01(){
        String config="web/applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);

        StudentService service  = (StudentService) ctx.getBean("studentService");
        List<Student> list = service.queryStudent();
        for(Student stu:list){
            System.out.println("Student: " + stu);
        }
    }
}
