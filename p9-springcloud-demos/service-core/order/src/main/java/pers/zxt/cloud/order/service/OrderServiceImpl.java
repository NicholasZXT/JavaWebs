package pers.zxt.cloud.order.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.ServiceInstance;
import pers.zxt.cloud.commons.domain.Result;
import pers.zxt.cloud.commons.domain.User;
import pers.zxt.cloud.commons.domain.Product;
import pers.zxt.cloud.commons.domain.Order;
import pers.zxt.cloud.order.feignClients.ProductFeignClient;


@Slf4j
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    LoadBalancerClient loadBalancerClient; //需要导入 spring-cloud-starter-loadbalancer

    @Autowired
    ProductFeignClient productFeignClient;  // OpenFeign客户端

    @Override
    public Order createOrder(Long userId, Long productId) {
        System.out.println(">>> 创建订单开始");
        Order order = new Order();
        order.setId(1L);
        order.setAddress("Some-Address");
        // 设置用户信息
        order.setUserId(userId);
        order.setUserName("SomeOne");

        // -------- 远程调用访问 Product 服务的 4 种方式 --------
        // TODO
        Product product = getProductByRestTemplate(productId);
        //Product product = getProductByLoadBalancer(productId);
        //Product product = getProductByAnnotatedLoadBalancer(productId);
        //Product product = getProductByFeign(productId);

        // 设置产品信息
        order.setTotalAmount(product.getPrice() * product.getNum());
        order.setProductList(List.of(product));
        return order;
    }

    /**
     * 请求Product-V1：使用 DiscoveryClient 获取服务实例 + 手动使用 RestTemplate 手动发起请求
     * @param productId
     * @return
     */
    public Product getProductByRestTemplate(Long productId) {
        // 1、获取到商品服务所在的所有机器IP+port
        List<ServiceInstance> instances = discoveryClient.getInstances("service-product");
        // 2. 随便选择一个服务，这里选择第一个
        ServiceInstance instance = instances.get(0);
        // 3. 向远程URL发起请求
        String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/product/" + productId;
        log.info("[getProductByRestTemplate] 远程请求：{}", url);
        Product product = restTemplate.getForObject(url, Product.class);
        log.info("[getProductByRestTemplate] 响应结果：{}", product);
        return product;
    }

    /**
     * 请求Product-V2：使用 LoadBalancerClient 发起请求
     * @param productId
     * @return
     */
    public Product getProductByLoadBalancer(Long productId){
        // 1.获取到商品服务所在的所有机器IP+port
        ServiceInstance choose = loadBalancerClient.choose("service-product");
        // 远程URL
        String url = "http://" + choose.getHost() + ":" + choose.getPort() + "/product/" + productId;
        log.info("[getProductByLoadBalancer] 远程请求：{}", url);
        // 2.请求远程服务
        Product product = restTemplate.getForObject(url, Product.class);
        log.info("[getProductByLoadBalancer] 响应结果：{}", product);
        return product;
    }

    /**
     * 请求Product-V3：使用 DiscoveryClient 获取服务实例 + 使用 @LoadBalanced 注解的 RestTemplate 发起请求
     * @param productId
     * @return
     */
    public Product getProductByAnnotatedLoadBalancer(Long productId) {
        String url = "http://service-product/product/"+productId;
        log.info("[getProductByAnnotatedLoadBalancer] 远程请求：{}", url);
        // 直接给远程发送请求； service-product 会被动态替换
        Product product = restTemplate.getForObject(url, Product.class);
        log.info("[getProductByAnnotatedLoadBalancer] 响应结果：{}", product);
        return product;
    }

    /**
     * 请求Product-V4：使用 OpenFeign 声明式 REST-ful 服务调用
     * @param productId
     * @return
     */
    public Product getProductByFeign(Long productId) {
        log.info("[getProductByFeign] 远程请求");
        Result<Product> result = productFeignClient.getProductById(productId);
        log.info("[getProductByFeign] 响应结果：{}", result);
        if (result.getCode() == 200) {
            return result.getData();
        }
        return null;
    }

}
