package pers.zxt.ssm.dao;

import java.util.List;
import pers.zxt.ssm.domain.Student;

public interface StudentDao {

    List<Student> selectStudents();

    int insertStudent(Student student);
}
