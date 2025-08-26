package pers.zxt.mybatisplus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * ORM里对应于数据库的实体类
 */
@TableName("teachers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer age;

    private String grade;
}
