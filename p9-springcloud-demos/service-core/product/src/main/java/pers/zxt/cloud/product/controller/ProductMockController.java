package pers.zxt.cloud.product.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.zxt.cloud.commons.domain.Result;
import pers.zxt.cloud.commons.domain.Product;
import pers.zxt.cloud.product.config.NacosCustomConfig;

@RestController
@RequestMapping("/product")
public class ProductMockController {
    // 模拟内存数据库
    private static final Map<String, Product> productDb = new ConcurrentHashMap<>();

    static {
        productDb.put("Car", new Product(1L, "Car", 100.00, 10));
        productDb.put("Aircraft", new Product(2L, "Aircraft", 1000.00, 10));
    }

    @Autowired
    NacosCustomConfig nacosCustomConfig;

    @GetMapping("/list")
    public Result<List<Product>> listProduct() {
        List<Product> products = new ArrayList<>(productDb.values());
        return Result.success(products);
    }

    @GetMapping("/name/{name}")
    public Result<Product> getProductByName(@PathVariable("name") String name) {
        Product product = productDb.get(name);
        if (product == null) {
            return Result.notFound("商品不存在");
        }
        return Result.success(product);
    }

    @GetMapping("/{id}")
    public Result<Product> getProductById(@PathVariable("id") Long id) {
        Product product = null;
        for(Map.Entry<String, Product> entry : productDb.entrySet()){
            if(entry.getValue().getId().equals(id)){
                product = entry.getValue();
            }
        }
        if (product == null) {
            return Result.notFound("商品不存在");
        }
        return Result.success(product);
    }

    /**
     * 获取nacos配置信息，可动态刷新，无需使用 @RefreshScope 注解
     * @return
     */
    @GetMapping("/config")
    public String getNacosConfig(){
        return nacosCustomConfig.toString();
    }
}
