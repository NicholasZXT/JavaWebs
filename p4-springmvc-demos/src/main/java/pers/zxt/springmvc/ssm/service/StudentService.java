package pers.zxt.springmvc.ssm.service;

import java.util.List;
import pers.zxt.springmvc.ssm.domain.Student;

public interface StudentService {

    List<Student> listStudents();

    int addStudent(Student student);
}
