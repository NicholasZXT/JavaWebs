package pers.zxt.mybatis.dao;

import java.util.List;
import pers.zxt.mybatis.domain.Student;

/**
 * DAO接口，定义对应表操作数据库的方法，接口中每一个方法对应于mapper文件里的一个查询语句
 * 这个接口文件的同目录下，需要定义该表的mapper文件（也就是mybatis的xml配置文件，每个表一个）
 * 除此之外，resource里存放了mybatis的主配置文件，里面存放了数据库的配置信息
 */
public interface StudentDao {

    Student selectStudentById(int id);

    int insertStudent(Student student);

    List<Student> selectAllStudents();

    ////where
    //List<Student> selectWhere(Student student);
    //
    ////foreach-1
    //List<Student> selectForeachOne(List<Integer> idlist);
    //
    ////foreach-2
    //List<Student> selectForeachTwo(List<Student> studentList);
    //

}
