package pers.spring.web.dao;

import java.util.List;
import pers.spring.web.domain.Student;

public interface StudentDao {
    List<Student> selectStudents();
}