package com.sb.cb.client;

import com.sb.cb.record.ProductRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name = "ProductClient",url = "${product.client.url}")
public interface ProductClient {

    @GetMapping("/products/{id}")
    ProductRecord getById(@PathVariable(value = "id") final Long id);

}
