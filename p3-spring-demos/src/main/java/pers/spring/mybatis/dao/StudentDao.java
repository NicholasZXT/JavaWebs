package pers.spring.mybatis.dao;

import java.util.List;
import pers.spring.mybatis.domain.Student;

public interface StudentDao {
    List<Student> selectStudents();
}