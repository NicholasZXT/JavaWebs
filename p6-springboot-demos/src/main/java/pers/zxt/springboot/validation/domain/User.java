package pers.zxt.springboot.validation.domain;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
// 下面几个是 hibernate-validator 提供的注解
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.Length;
import pers.zxt.springboot.validation.domain.Job;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @NotNull(message = "id不能为空")
    private Integer id;

    @NotBlank(message = "name不能为空")
    @Length(max = 10, message = "姓名过长!")
    private String name;

    private String gender;

    @Range(min = 0, max = 120, message = "age范围0-120")
    private Integer age;

    private String address;

    @Email(message = "email格式错误")
    private String email;

    @Past(message = "无效的出生日期!")
    private Date birthday;

    // 对于复杂对象，需要使用 @Valid 注解触发嵌套校验
    @Valid
    private Job job;

}
