package pers.zxt.springboot.validation.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {

    private String company;

    private String jobName;

    @Min(value = 0, message = "salary不能小于0")
    private float salary;

}
