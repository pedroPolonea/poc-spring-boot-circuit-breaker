package com.sb.cb.resources;

import com.sb.cb.record.ProductRecord;
import com.sb.cb.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductResources {

    private ProductService productService;

    @Autowired
    public ProductResources(final ProductService productService){
        this.productService = productService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductRecord> getById(@PathVariable(name = "id") final Long id) throws InterruptedException {
        log.info("M=getById, id={}", id);
        ProductRecord productRecord = productService.getById(id);

        log.info("M=getById, productRecord={}", productRecord);
        Thread.sleep(30000);
        return ResponseEntity.ok(productRecord);
    }

}
