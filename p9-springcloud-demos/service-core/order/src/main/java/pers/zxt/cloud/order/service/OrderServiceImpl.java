package pers.zxt.cloud.order.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.ServiceInstance;
import pers.zxt.cloud.commons.domain.Result;
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
    public Order createOrder(Long userId, Long productId, Integer version) {
        //System.out.printf(">>> 创建订单开始: %d, %d, %d\n", userId, productId, version);
        log.debug(">>> 创建订单开始: {}, {}, {}", userId, productId, version);
        Order order = new Order();
        order.setId(1L);
        order.setAddress("Some-Address");
        // 设置用户信息
        order.setUserId(userId);
        order.setUserName("SomeOne");

        // -------- 远程调用访问 Product 服务的 4 种方式 --------
        Product product = switch (version) {
            case 1 ->
                    // 1.1 使用 DiscoveryClient 获取服务实例 + 手动使用 RestTemplate 手动发起请求
                    getProductByRestTemplate(productId);
            case 2 ->
                    // 1.2 使用 LoadBalancerClient 发起请求
                    getProductByLoadBalancer(productId);
            case 3 ->
                    // 1.3 使用 DiscoveryClient 获取服务实例 + 使用 @LoadBalanced 注解的 RestTemplate 发起请求
                    getProductByAnnotatedLoadBalancer(productId);
            case 4 ->
                    // 1.4 使用 OpenFeign 客户端发起请求
                    getProductByFeign(productId);
            default -> null;
        };
        if(product == null) return null;

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
        // 注意泛型
        //Result<Product> result = restTemplate.getForObject(url, Result.class);
        // 应当使用下面的方式
        ResponseEntity<Result<Product>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<Result<Product>>() {}
        );
        Result<Product> result = response.getBody();
        log.info("[getProductByRestTemplate] 响应结果：{}", result);
        if (result != null && result.getCode() == 200) {
            return result.getData();
        }
        return null;
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
        ResponseEntity<Result<Product>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<Result<Product>>() {}
        );
        Result<Product> result = response.getBody();
        log.info("[getProductByLoadBalancer] 响应结果：{}", result);
        if (result != null && result.getCode() == 200) {
            return result.getData();
        }
        return null;
    }

    /**
     * 请求Product-V3：使用 DiscoveryClient 获取服务实例 + 使用 @LoadBalanced 注解的 RestTemplate 发起请求
     * @param productId
     * @return
     */
    public Product getProductByAnnotatedLoadBalancer(Long productId) {
        // TODO
        String url = "http://service-product/product/"+productId;
        log.info("[getProductByAnnotatedLoadBalancer] 远程请求：{}", url);
        // 直接给远程发送请求； service-product 会被动态替换
        ResponseEntity<Result<Product>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<Result<Product>>() {}
        );
        Result<Product> result = response.getBody();
        log.info("[getProductByAnnotatedLoadBalancer] 响应结果：{}", result);
        if (result != null && result.getCode() == 200) {
            return result.getData();
        }
        return null;
    }

    /**
     * 请求Product-V4：使用 OpenFeign 声明式 REST-ful 服务调用
     * @param productId
     * @return
     */
    public Product getProductByFeign(Long productId) {
        log.info("[getProductByFeign] 远程请求: {}", productId);
        Result<Product> result = productFeignClient.getProductById(productId);
        log.info("[getProductByFeign] 响应结果：{}", result);
        if (result.getCode() == 200) {
            return result.getData();
        }
        return null;
    }

}
