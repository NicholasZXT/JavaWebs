package pers.zxt.springboot.ssm.service;


import java.util.List;
import pers.zxt.springboot.ssm.domain.Student;

public interface StudentService {
    List<Student> listStudents();

    int addStudent(Student student);
}
