package com.sb.cb.service.impl;

import com.sb.cb.client.ProductClient;
import com.sb.cb.record.ProductRecord;
import com.sb.cb.service.ProductService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private ProductClient productClient;

    @Autowired
    public ProductServiceImpl(final ProductClient productClient){
        this.productClient = productClient;
    }

    @Override
    @CircuitBreaker(name = "products-get-id", fallbackMethod = "fallbackGetById")
    public ProductRecord getById(Long id) {
        log.info("M=getById, id={}", id);
        final ProductRecord productRecord = productClient.getById(id);
        log.info("M=getById, productRecord={}", productRecord);
        return productRecord;
    }

    @Override
    @RateLimiter(name = "products-get-id", fallbackMethod = "fallbackForRatelimitGetById")
    public ProductRecord getByIdRateLimiter(Long id) {
        log.info("M=getByIdRateLimiter, id={}", id);
        final ProductRecord productRecord = productClient.getById(id);
        log.info("M=getByIdRateLimiter, productRecord={}", productRecord);
        return productRecord;
    }

    @Override
    @Retry(name = "get", fallbackMethod = "fallbackRetry")
    public ProductRecord getByIdRetry(Long id) {
        log.info("M=getByIdRetry, id={}", id);
        final ProductRecord productRecord = productClient.getById(id);
        log.info("M=getByIdRetry, productRecord={}", productRecord);
        return productRecord;
    }

    @Override
    @Bulkhead(name = "get", type = Bulkhead.Type.SEMAPHORE, fallbackMethod = "fallbackBulkhead")
    public ProductRecord getByIdBulkhead(Long id) {
        log.info("M=getByIdBulkhead, id={}", id);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final ProductRecord productRecord = productClient.getById(id);
        log.info("M=getByIdBulkhead, productRecord={}", productRecord);
        return null;
    }


    public ProductRecord fallbackGetById(Long id, Throwable t) {
        log.error("Inside circuit breaker fallbackGetById, id={}, cause - {}", id, t.getMessage());
        return null;
    }

    public ProductRecord fallbackForRatelimitGetById(Long id, Throwable t) {
        log.error("Inside fallbackForRatelimitGetById, id={}, cause - {}", id, t.getMessage());
        return null;
    }

    public ProductRecord fallbackRetry(Throwable t) {
        log.error("Inside fallbackRetry, cause - {}", t.getMessage());
        return null;
    }

    public ProductRecord fallbackBulkhead(Throwable t) {
        log.error("Inside fallbackBulkhead, cause - {}", t.getMessage());
        return null;
    }
}
