package pers.zxt.ssm.service;

import java.util.List;
import pers.zxt.ssm.domain.Student;

public interface StudentService {

    List<Student> listStudents();

    int addStudent(Student student);
}
