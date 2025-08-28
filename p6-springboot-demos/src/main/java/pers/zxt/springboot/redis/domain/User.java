package pers.zxt.springboot.redis.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class User implements Serializable {
    private Long id;
    private String name;
    private Integer age;
}
