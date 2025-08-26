package pers.zxt.mybatis;

import org.junit.Test;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;
import pers.zxt.mybatis.domain.Student;
import pers.zxt.mybatis.utils.MyBatisUtil;

/**
 * 下面三个测试用例，都没有用到 StudentDao 接口，只是为了展示 mybatis 的基本使用过程
 */
public class MyBatisBasicTest {

    @Test
    public void testSelectStudentById() throws IOException {
        //调用mybatis某个对象的方法，执行mapper文件中的sql语句
        //mybatis核心类： SqlSessionFactory

        // 1.定义mybatis主配置文件的位置，从类路径开始的相对路径
        String config = "mybatis.xml";

        // 2.读取主配置文件，使用 mybatis 框架中的 Resources 类
        InputStream inputStream = Resources.getResourceAsStream(config);

        // 3.创建 SqlSessionFactory 对象，使用 SqlSessionFactoryBuidler 类
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);

        // 4.获取 SqlSession 对象
        SqlSession session = factory.openSession();

        // 5.指定要执行的sql语句的 id = namespace + "." + select|update|insert|delete标签的id属性值
        String namespace = "pers.zxt.mybatis.dao.StudentDao";
        String sqlId = namespace + "." + "selectStudentById";

        // 6.通过SqlSession的方法，执行sql语句，传入的值会被插入SQL语句中的占位符
        Student student = session.selectOne(sqlId, 1001);
        System.out.println("test for selectStudentById: " + student);

        // 7.关闭SqlSession对象
        session.close();
    }

    @Test
    public void testInsertStudent() throws IOException {
        String config = "mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(config);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = factory.openSession();

        String namespace = "pers.zxt.mybatis.dao.StudentDao";
        String sqlId = namespace + "." + "insertStudent";
        Student student = new Student();
        student.setId(1009);
        student.setName("李四");
        student.setGrade("初三");
        student.setAge(15);
        int rows = session.insert(sqlId, student);
        System.out.println("test for insertStudent: " + rows);

        //mybatis默认执行sql语句是  手工提交事务模式，在做insert，update，delete 后需要提交事务
        session.commit();
        session.close();
    }

    /**
     * 使用工具类来创建session
     */
    @Test
    public void testMybatisUtil(){
        // 使用工具类来创建session
        SqlSession session = MyBatisUtil.getSqlSession();

        String namespace = "pers.zxt.mybatis.dao.StudentDao";
        String sqlId = namespace + "." + "selectStudentById";
        Student student = session.selectOne(sqlId, 1001);
        System.out.println("test for selectStudentById: " + student);
        session.close();
    }
}
