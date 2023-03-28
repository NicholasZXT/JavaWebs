package pers.zxt.spring.web.service.impl;

import java.util.List;
import pers.zxt.spring.web.service.StudentService;
import pers.zxt.spring.web.dao.StudentDao;
import pers.zxt.spring.web.domain.Student;

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
