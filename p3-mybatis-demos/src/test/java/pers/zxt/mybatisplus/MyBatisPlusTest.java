package pers.zxt.mybatisplus;

import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import pers.zxt.mybatisplus.domain.Teacher;
import pers.zxt.mybatisplus.dao.TeacherDao;

public class MyBatisPlusTest {

    @Test
    public void testSelectTeacherById() throws IOException {
        String config = "mybatisplus.xml";
        InputStream inputStream = Resources.getResourceAsStream(config);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = factory.openSession();

        TeacherDao dao = session.getMapper(TeacherDao.class);

        // 使用 mybatis 的 Data Mapper 模式
        Teacher teacher1 = dao.selectTeacherById(1001);
        System.out.println("test for testSelectTeacherById: " + teacher1);

        session.close();
    }

    @Test
    public void testSelectTeacherByIdFromMapperProxy() throws IOException {
        String config = "mybatisplus.xml";
        InputStream inputStream = Resources.getResourceAsStream(config);

        // 注意，这里需要改为使用 MyBatis-Plus 提供的 MybatisSqlSessionFactoryBuilder ----- KEY
        //SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSessionFactory factory = new MybatisSqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = factory.openSession();

        TeacherDao dao = session.getMapper(TeacherDao.class);

        // 使用 mybatis-plus 扩展的 Active Record 模式
        Teacher teacher2 = dao.selectById(1001);
        System.out.println("testSelectTeacherById by mapper proxy:" + teacher2);

        session.close();
    }

}
