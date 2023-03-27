package pers.spring.web.service.impl;

import java.util.List;
import pers.spring.web.service.StudentService;
import pers.spring.web.dao.StudentDao;
import pers.spring.web.domain.Student;

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
