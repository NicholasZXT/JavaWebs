package pers.spring.mybatis.service;

import java.util.List;
import pers.spring.mybatis.domain.Student;

public interface StudentService {
    List<Student> queryStudent();
}
