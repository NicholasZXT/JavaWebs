package com.zxt.javawebs;

import com.zxt.javawebs.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import com.zxt.javawebs.dao.StudentDao;
import com.zxt.javawebs.dao.impl.StudentDaoImpl;
import com.zxt.javawebs.domain.Student;

public class MyTest2 {
    // 使用 传统DAO 的方式，DAO 接口的实现类 StudentDaoImpl 的每个方法里其实做的只是配置工作，并没有太多有意义的事情
    @Test
    public void testSelectOne(){
        // 接口类型  变量  =  new 接口的实现类();
        StudentDao  dao  = new StudentDaoImpl();
        Student student  = dao.selectStudentById(1);
        System.out.println("通过dao执行方法获取的对象：" + student);
    }

    /**
     * 上面使用的 StudentDao接口 的 实现类StudentDaoImpl 其实没啥大作业，mybatis可以通过反射+配置信息，完成该实现类中进行的操作.
     * MyBatis可以代替我们来动态创建一个 DAO接口 的实现类，不需要我们手动重复实现，这就是 MyBatis 的 DAO动态代理.
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
        StudentDao dao = session.getMapper(StudentDao.class);
        // dao代理的实现类
        System.out.println("dao.class: " + dao.getClass().getName());
        Student student = dao.selectStudentById(1);
        System.out.println("student = " + student);
        //3.关闭SqlSession对象
        session.close();
    }
}
