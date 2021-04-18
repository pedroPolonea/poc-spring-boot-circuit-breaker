package com.sb.cb.service;

import com.sb.cb.record.ProductRecord;

public interface ProductService {

    ProductRecord getById(Long id);

    ProductRecord getByIdRateLimiter(Long id);

    ProductRecord getByIdRetry(Long id);

    ProductRecord getByIdBulkhead(Long id);

}
