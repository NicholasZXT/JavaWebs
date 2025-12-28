package pers.zxt.cloud.product.config;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * 自定义Nacos配置，和nacos中的配置进行对象绑定
 */
@Component
@ConfigurationProperties(prefix = "conf")
@Data
public class NacosCustomConfig {
    String env;
    String service;
    String group;
    String some;
}
