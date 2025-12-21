package pers.zxt.cloud.order.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.ServiceInstance;
import pers.zxt.cloud.commons.domain.Order;


@Slf4j
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    LoadBalancerClient loadBalancerClient; //需要导入 spring-cloud-starter-loadbalancer

    @Override
    public Order createOrder(Long userId, Long productId) {
        return null;
    }

    /**
     * 创建订单-V1：使用 DiscoveryClient 获取服务实例 + 手动使用 RestTemplate 手动发起请求
     * @param userId
     * @param productId
     * @return
     */
    public Order createOrderV1(Long userId, Long productId) {
        return null;
    }

    /**
     * 创建订单-V2：使用 DiscoveryClient 获取服务实例 + 使用 @LoadBalanced 注解的 RestTemplate 发起请求
     * @param userId
     * @param productId
     * @return
     */
    public Order createOrderV2(Long userId, Long productId) {
        return null;
    }

    /**
     * 创建订单-V3：使用 LoadBalancerClient 发起请求
     * @param userId
     * @param productId
     * @return
     */
    public Order createOrderV3(Long userId, Long productId) {
        return null;
    }

    /**
     * 创建订单-V4：使用 OpenFeign 声明式 RESTful 服务调用
     * @param userId
     * @param productId
     * @return
     */
    public Order createOrderV4(Long userId, Long productId) {
        return null;
    }

}
