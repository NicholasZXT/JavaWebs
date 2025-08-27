package pers.zxt.springmvc.ssm.dao;

import java.util.List;
import pers.zxt.springmvc.ssm.domain.Student;

public interface StudentDao {

    List<Student> selectStudents();

    int insertStudent(Student student);
}
