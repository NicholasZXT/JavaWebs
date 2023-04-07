package pers.zxt.ssm.service.impl;

import java.util.List;
import javax.annotation.Resource;
import pers.zxt.ssm.domain.Student;
import pers.zxt.ssm.service.StudentService;
import pers.zxt.ssm.dao.StudentDao;

public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentDao studentDao;

    @Override
    public int addStudent(Student student) {
        return studentDao.insertStudent(student);
    }

    @Override
    public List<Student> listStudents() {
        return null;
    }
}
