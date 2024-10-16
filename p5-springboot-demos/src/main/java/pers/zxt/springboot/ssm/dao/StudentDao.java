package pers.zxt.springboot.ssm.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.zxt.springboot.ssm.domain.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 第一种方式，使用 mybatis-starter 提供的 @Mapper 注解标识 dao 接口
 */
//@Mapper
public interface StudentDao {

    List<Student> selectStudents();

    int insertStudent(Student student);
}
