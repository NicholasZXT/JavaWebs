package pers.zxt.spring.web.dao;

import java.util.List;
import pers.zxt.spring.web.domain.Student;

public interface StudentDao {
    List<Student> selectStudents();
}