package com.sb.cb.resources;

import com.sb.cb.record.ProductRecord;
import com.sb.cb.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductRecord getById(@PathVariable(name = "id") final Long id){
        log.info("M=getById, id={}", id);
        ProductRecord productRecord = productService.getById(id);
        log.info("M=getById, productRecord={}", productRecord);
        return productRecord;
    }

    @GetMapping(value = "{id}/retry", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductRecord getByIdRetry(@PathVariable(name = "id") final Long id){
        log.info("M=getByIdRetry, id={}", id);
        ProductRecord productRecord = productService.getByIdRetry(id);
        log.info("M=getByIdRetry, productRecord={}", productRecord);
        return productRecord;
    }

    @GetMapping(value = "{id}/ratelimiter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductRecord getByIdRateLimiter(@PathVariable(name = "id") final Long id){
        log.info("M=getByIdRateLimiter, id={}", id);
        ProductRecord productRecord = productService.getByIdRateLimiter(id);
        log.info("M=getByIdRateLimiter, productRecord={}", productRecord);
        return productRecord;
    }

    @GetMapping(value = "{id}/bulkhead", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductRecord getByIdBulkhead(@PathVariable(name = "id") final Long id){
        log.info("M=getByIdBulkhead, id={}", id);
        ProductRecord productRecord = productService.getByIdBulkhead(id);
        log.info("M=getByIdBulkhead, productRecord={}", productRecord);
        return productRecord;
    }

}
