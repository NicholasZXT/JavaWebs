package pers.zxt.ssm.dao;

import java.util.List;
import pers.zxt.ssm.domain.Student;

public interface StudentDao {
    int insertStudent(Student student);
    List<Student> selectStudents();
}
