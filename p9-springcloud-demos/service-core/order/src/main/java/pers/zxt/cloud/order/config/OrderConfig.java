package pers.zxt.cloud.order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;

@Configuration
public class OrderConfig {

    //@LoadBalanced  // 注解式负载均衡
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
