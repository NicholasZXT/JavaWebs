package pers.zxt.springboot.redis.controller;


import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import pers.zxt.springboot.redis.domain.User;
import pers.zxt.springboot.redis.service.RedisService;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private RedisService redisService;

    @PostMapping("/set")
    public String set(@RequestParam String key, @RequestParam String value) {
        redisService.set(key, value);
        return "OK";
    }

    @PostMapping("/set/user")
    public String setUser(@RequestBody User user) {
        redisService.set("user:" + user.getId(), user, 30, TimeUnit.MINUTES);
        return "User saved to Redis";
    }

    @GetMapping("/get/{key}")
    public Object get(@PathVariable String key) {
        return redisService.get(key);
    }

    @DeleteMapping("/delete/{key}")
    public Boolean delete(@PathVariable String key) {
        return redisService.delete(key);
    }
}
