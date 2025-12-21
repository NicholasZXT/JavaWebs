package pers.zxt.cloud.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.web.bind.annotation.*;
import pers.zxt.cloud.commons.domain.Result;
import pers.zxt.cloud.commons.domain.User;

@RestController
@RequestMapping("/user")
public class UserMockController {

    // 模拟内存数据库
    private static final Map<Long, User> userDb = new ConcurrentHashMap<>();

    static {
        userDb.put(1L, new User(1L, "Alice"));
        userDb.put(2L, new User(2L, "Bob"));
    }

    @GetMapping("/list")
    public Result<List<User>> listUser() {
        List<User> users = new ArrayList<>(userDb.values());
        return Result.success(users);
    }

    @GetMapping("/{id:Long}")
    public Result<User> getUserById(@PathVariable Long id) {
        User user = userDb.get(id);
        if (user == null) {
            return Result.notFound("用户不存在");
        }
        return Result.success(user);
    }

    @GetMapping("/{name:string}")
    public Result<User> getUserByName(@PathVariable String name) {
        User user = null;
        for(Map.Entry<Long, User> entry : userDb.entrySet()){
            if(entry.getValue().getName().equals(name)){
                user = entry.getValue();
            }
        }
        if (user == null) {
            return Result.notFound("用户不存在");
        }
        return Result.success(user);
    }

    @PostMapping
    public Result<User> createUser(@RequestBody User user) {
        userDb.put(user.getId(), user);
        return Result.success(user);
    }
}
