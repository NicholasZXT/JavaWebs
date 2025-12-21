package pers.zxt.cloud.product.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.web.bind.annotation.*;
import pers.zxt.cloud.commons.domain.Result;
import pers.zxt.cloud.commons.domain.Product;

@RestController
@RequestMapping("/product")
public class ProductMockController {
    // 模拟内存数据库
    private static final Map<String, Product> productDb = new ConcurrentHashMap<>();

    static {
        productDb.put("Car", new Product(1L, "Car", 100.00, 10));
        productDb.put("Aircraft", new Product(2L, "Aircraft", 1000.00, 10));
    }

    @GetMapping("/list")
    public Result<List<Product>> listProduct() {
        List<Product> products = new ArrayList<>(productDb.values());
        return Result.success(products);
    }

    @GetMapping("/{name:string}")
    public Result<Product> getProductByName(@PathVariable String name) {
        Product product = productDb.get(name);
        if (product == null) {
            return Result.notFound("商品不存在");
        }
        return Result.success(product);
    }

    @GetMapping("/{id:Long}")
    public Result<Product> getProductById(@PathVariable Long id) {
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
}
