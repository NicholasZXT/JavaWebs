package pers.zxt.mybatis;

import java.util.List;
import org.junit.Test;
import org.apache.ibatis.session.SqlSession;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.Page;
import pers.zxt.mybatis.utils.MyBatisUtil;
import pers.zxt.mybatis.domain.Student;
import pers.zxt.mybatis.dao.StudentDao;
import pers.zxt.mybatis.dao.impl.StudentDaoImpl;

public class MyTest2 {
    // 使用 传统DAO 的方式，DAO 接口的实现类 StudentDaoImpl 的每个方法里其实做的只是配置工作，并没有太多有意义的事情
    @Test
    public void testSelectOne(){
        // 接口类型  变量  =  new 接口的实现类();
        StudentDao  dao  = new StudentDaoImpl();
        Student student  = dao.selectStudentById(1001);
        System.out.println("通过dao执行方法获取的对象：" + student);
    }

    /**
     * 上面使用的 StudentDao接口 的 实现类StudentDaoImpl 其实作用不大，因为其中的每个方法只是获取SqlSession对象，
     * 然后执行 mapper映射文件中的SQL语句而已，都是重复的操作，mybatis 可以通过通过反射+配置信息来实现这些操作——也就是动态代理.
     * 详细来说，MyBatis 的 DAO动态代理 可以代替我们来动态创建一个 DAO接口 的实现类，不需要我们手动重复实现.
     * 不过使用DAO动态代理有一些要求：
     *  1. mapper文件中的 namespace 必须是dao接口的全限定名称
     *  2. mapper文件中标签的 id == dao接口中的方法名称
     *  使用 SqlSession 对象的 getMapper() 方法即可获取 DAO接口的代理实现
     */

    // 使用 mybatis 动态代理
    @Test
    public void testSelectOneByProxy(){
        //1.获取SqlSession
        SqlSession session = MyBatisUtil.getSqlSession();
        //2.获取dao的代理
        // 需要传入 StudentDao 这个接口的 class
        StudentDao dao = session.getMapper(StudentDao.class);
        // dao代理的实现类
        System.out.println("dao.class: " + dao.getClass().getName());
        // 通过代理对象执行接口，从而执行对应的SQL语句
        Student student = dao.selectStudentById(1001);
        System.out.println("selectStudentById by mapper proxy :" + student);
        //3.关闭SqlSession对象
        session.close();
    }

    // 分页插件的使用
    @Test
    public void testPage(){
        SqlSession session = MyBatisUtil.getSqlSession();
        StudentDao dao = session.getMapper(StudentDao.class);
        // 在查询语句之前，调用 PageHelper 的分页方法即可
        Page<Student> page = PageHelper.startPage(1, 3);
        List<Student> students = dao.selectAllStudents();
        students.forEach(System.out::println);
        // 还需要关闭一下
        page.close();
        System.out.println("---------------------------------------------");
        // 再翻一页
        page = PageHelper.startPage(2, 3);
        students = dao.selectAllStudents();
        students.forEach(System.out::println);
        page.close();
    }
}
