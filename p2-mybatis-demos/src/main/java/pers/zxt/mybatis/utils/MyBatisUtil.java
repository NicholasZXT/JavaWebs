package pers.zxt.mybatis.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;

/**
 *  工具类：创建SqlSession对象
 */
public class MyBatisUtil {

    private static SqlSessionFactory factory = null;

    // 在类的静态初始化块里读取mybatis配置文件，初始化一个 SqlSessionFactory 对象
    static {
        String config = "mybatis.xml";
        try {
            InputStream inputStream = Resources.getResourceAsStream(config);
            factory  = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //静态方法，获取SqlSession对象
    public static SqlSession getSqlSession(){
        SqlSession session = null;
        if( factory != null ){
            session = factory.openSession();
            // 开启自动事务
            //session = factory.openSession(true);
        }
        return session;
    }

}
