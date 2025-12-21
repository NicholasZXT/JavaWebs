package pers.zxt.cloud.order.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.zxt.cloud.commons.domain.Result;
import pers.zxt.cloud.commons.domain.User;
import pers.zxt.cloud.commons.domain.Product;
import pers.zxt.cloud.commons.domain.Order;
import pers.zxt.cloud.order.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderMockController {

    @Autowired
    OrderService orderService;

    @GetMapping("/create")
    public Result<Order> createOrder(
            @RequestParam("userId") Long userId,
            @RequestParam("productId") Long productId
    ){
        Order order = orderService.createOrder(userId, productId);
        return Result.success(order);
    }

}
