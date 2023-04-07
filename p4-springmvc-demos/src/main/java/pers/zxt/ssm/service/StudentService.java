package pers.zxt.ssm.service;

import java.util.List;
import pers.zxt.ssm.domain.Student;

public interface StudentService {
    int addStudent(Student student);
    List<Student> listStudents();
}
