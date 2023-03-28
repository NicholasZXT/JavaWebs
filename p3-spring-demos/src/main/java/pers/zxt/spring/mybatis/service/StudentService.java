package pers.zxt.spring.mybatis.service;

import java.util.List;
import pers.zxt.spring.mybatis.domain.Student;

public interface StudentService {
    List<Student> queryStudent();
}
