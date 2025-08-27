package pers.zxt.spring.mybatis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.List;

import pers.zxt.spring.mybatis.domain.Student;
import pers.zxt.spring.mybatis.service.StudentService;

public class SpringMybatisTest {
    @Test
    public void test01(){
        String config="mybatis/applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);

        StudentService service  = (StudentService) ctx.getBean("studentService");
        List<Student> list = service.queryStudent();
        for(Student stu:list){
            System.out.println("Student: " + stu);
        }
    }
}
