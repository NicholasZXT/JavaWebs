package pers.spring.mybatis.service.impl;

import java.util.List;
import pers.spring.mybatis.service.StudentService;
import pers.spring.mybatis.dao.StudentDao;
import pers.spring.mybatis.domain.Student;

public class StudentServiceImpl implements StudentService {

    private StudentDao studentDao;

    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public List<Student> queryStudent() {
        List<Student> students  = studentDao.selectStudents();
        return students;
    }
}
