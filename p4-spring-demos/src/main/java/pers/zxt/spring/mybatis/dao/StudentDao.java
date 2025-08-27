package pers.zxt.spring.mybatis.dao;

import java.util.List;
import pers.zxt.spring.mybatis.domain.Student;

public interface StudentDao {
    List<Student> selectStudents();
}