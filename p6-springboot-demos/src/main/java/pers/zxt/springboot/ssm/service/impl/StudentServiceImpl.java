package pers.zxt.springboot.ssm.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import pers.zxt.springboot.ssm.domain.Student;
import pers.zxt.springboot.ssm.dao.StudentDao;
import pers.zxt.springboot.ssm.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentDao studentDao;

    @Override
    public List<Student> listStudents() {
        return studentDao.selectStudents();
    }

    @Override
    public int addStudent(Student student) {
        return studentDao.insertStudent(student);
    }
}
