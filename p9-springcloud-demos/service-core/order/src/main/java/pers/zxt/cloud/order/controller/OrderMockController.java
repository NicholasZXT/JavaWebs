package pers.zxt.cloud.order.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pers.zxt.cloud.commons.domain.Result;
import pers.zxt.cloud.commons.domain.User;
import pers.zxt.cloud.commons.domain.Product;
import pers.zxt.cloud.commons.domain.Order;
import pers.zxt.cloud.order.service.OrderService;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderMockController {

    @Autowired
    OrderService orderService;

    @GetMapping("/create")
    public Result<Order> createOrderByRestTemplate(
            @RequestParam("uid") Long userId,
            @RequestParam("pid") Long productId
    ){
        Order order = orderService.createOrder(userId, productId);
        if (order == null){
            return Result.fail("创建订单失败");
        }
        return Result.success(order);
    }

}
