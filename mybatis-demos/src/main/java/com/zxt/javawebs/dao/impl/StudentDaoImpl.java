package com.zxt.javawebs.dao.impl;

import com.zxt.javawebs.dao.StudentDao;
import com.zxt.javawebs.domain.Student;
import com.zxt.javawebs.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 * 对DAO接口的实现类，这个类虽然实现了DAO接口的方法，但是其中并没有做什么有意义的内容，实际的查询逻辑还是mapper文件中的SQL
 * 这个类的实现可以交给 mybatis 的 动态代理来实现
 */
public class StudentDaoImpl implements StudentDao {
    @Override
    public Student selectStudentById(int id) {
        SqlSession session = MyBatisUtil.getSqlSession();
        String sqlId = "com.zxt.javawebs.dao.StudentDao" + "." + "selectStudentById";
        Student student = session.selectOne(sqlId, id);
        System.out.println("使用mybatis查询一个学生："+student);
        session.close();
        return student;
    }

    @Override
    public int insertStudent(Student student) {
        SqlSession session = MyBatisUtil.getSqlSession();
        String sqlId = "com.zxt.javawebs.dao.StudentDao" + "." + "insertStudent";
        int rows = session.insert(sqlId, student);
        System.out.println("使用mybatis插入一个学生的结果：" + rows);
        session.commit();
        session.close();
        return rows;
    }
}
