package pers.zxt.mybatisplus.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import pers.zxt.mybatisplus.domain.Teacher;

//@Mapper
public interface TeacherDao extends BaseMapper<Teacher> {

    Teacher selectTeacherById(int id);

    int insertTeacher(Teacher teacher);

    List<Teacher> selectAllTeachers();

}
