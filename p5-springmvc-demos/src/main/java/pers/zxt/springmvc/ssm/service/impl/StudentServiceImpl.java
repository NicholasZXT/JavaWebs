package pers.zxt.springmvc.ssm.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import pers.zxt.springmvc.ssm.domain.Student;
import pers.zxt.springmvc.ssm.service.StudentService;
import pers.zxt.springmvc.ssm.dao.StudentDao;

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
