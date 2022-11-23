package com.zxt.javawebs;

import org.junit.Test;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;
import com.zxt.javawebs.domain.Student;
import com.zxt.javawebs.utils.MyBatisUtil;

public class MyTest {
    // 下面这三个测试用例，都没有用到 StudentDao 接口，只是为了展示 mybatis 的使用过程
    @Test
    public void testSelectStudentById() throws IOException {
        //调用mybatis某个对象的方法，执行mapper文件中的sql语句
        //mybatis核心类： SqlSessionFactory
        // 1.定义mybatis主配置文件的位置，从类路径开始的相对路径
        String config="mybatis.xml";
        // 2.读取主配置文件，使用 mybatis 框架中的 Resources 类
        InputStream inputStream = Resources.getResourceAsStream(config);
        // 3.创建 SqlSessionFactory 对象，使用 SqlSessionFactoryBuidler 类
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        // 4.获取 SqlSession 对象
        SqlSession session = factory.openSession();

        // 5.指定要执行的sql语句的 id
        //  sql的 id = namespace + "." + select|update|insert|delete标签的id属性值
        String sqlId = "com.zxt.javawebs.dao.StudentDao" + "." + "selectStudentById";

        // 6.通过SqlSession的方法，执行sql语句，传入的值会被插入SQL语句中的占位符
        Student student = session.selectOne(sqlId, 1);
        System.out.println("使用mybatis查询一个学生："+student);

        // 7.关闭SqlSession对象
        session.close();
    }

    @Test
    public void testInsertStudent() throws IOException {
        String config="mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(config);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = factory.openSession();

        String sqlId = "com.zxt.javawebs.dao.StudentDao" + "." + "insertStudent";
        Student student = new Student();
        student.setId(2);
        student.setName("李四");
        student.setEmail("1231414214");
        student.setAge(15);
        int rows = session.insert(sqlId, student);
        System.out.println("使用mybatis插入一个学生的结果：" + rows);

        //mybatis默认执行sql语句是  手工提交事务模式，在做insert，update，delete 后需要提交事务
        session.commit();
        session.close();
    }

    @Test
    public void testMybatisUtil(){
        // 使用工具类来创建session
        SqlSession session = MyBatisUtil.getSqlSession();

        String sqlId = "com.zxt.javawebs.dao.StudentDao" + "." + "selectStudentById";
        Student student = session.selectOne(sqlId, 1);
        System.out.println("使用mybatis查询一个学生："+student);
        session.close();
    }
}
