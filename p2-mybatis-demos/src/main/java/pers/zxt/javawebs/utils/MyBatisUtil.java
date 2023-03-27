package pers.zxt.javawebs.utils;

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

    private static SqlSessionFactory factory  = null;

    static {
        String config="mybatis.xml";
        try {
            InputStream inputStream  =Resources.getResourceAsStream(config);
            factory  = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //创建方法，获取SqlSession对象
    public static SqlSession getSqlSession(){
        SqlSession session = null;
        if( factory != null ){
            session =factory.openSession();
            // 开启自动事务
            //session =factory.openSession(true);
        }
        return session;
    }

}
