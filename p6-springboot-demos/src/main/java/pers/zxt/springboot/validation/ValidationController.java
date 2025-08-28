package pers.zxt.springboot.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import pers.zxt.springboot.validation.domain.User;


@Slf4j
@RestController
@RequestMapping("/validation")
@Validated   // 这个注解用于告诉 Spring 框架对当前类的对象开启参数验证
public class ValidationController {

    //@Autowired
    //private Validator globalValidator;

    /**
     * 直接参数校验。
     * 有时候接口的参数比较少，这时候就没必要定义一个DTO来接收参数，可以直接接收参数并进行校验。
     * 不过需要注意的是，如果想在参数中使用 @NotNull 这种注解校验，就必须在类上添加 @Validated 注解
     * @param name
     * @return
     */
    @GetMapping("/getUser")
    public String getUser (@NotNull @RequestParam String name) {
        log.info("name: {}", name);
        return "hello " + name;
    }

    /**
     * DTO参数校验。
     * DTO 对象前面需要使用 @Valid 注解，表示开启参数验证。
     * @param user
     * @return
     */
    @PostMapping("/createUser")
    public boolean createUser(@Valid @RequestBody User user){
        log.info("user: {}", user);
        return true;
    }
}
