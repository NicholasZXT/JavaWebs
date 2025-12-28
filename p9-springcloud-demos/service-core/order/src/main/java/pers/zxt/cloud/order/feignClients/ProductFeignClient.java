package pers.zxt.cloud.order.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pers.zxt.cloud.commons.domain.Result;
import pers.zxt.cloud.commons.domain.User;
import pers.zxt.cloud.commons.domain.Product;
import pers.zxt.cloud.commons.domain.Order;

/**
 * OpenFeign 声明式远程调用接口
 */
@FeignClient(name = "service-product", path = "/product")
public interface ProductFeignClient {

    @GetMapping("/{id}")
    public Result<Product> getProductById(@PathVariable("id") Long id);

    @GetMapping("/{name}")
    public Result<Product> getProductByName(@PathVariable("id") String name);
}
