package com.sb.cb.service.impl;

import com.github.javafaker.Faker;
import com.sb.cb.record.ProductRecord;
import com.sb.cb.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private Faker faker;

    @Autowired
    public ProductServiceImpl(final Faker faker){
        this.faker = faker;
    }

    @Override
    public ProductRecord getById(Long id) {
        log.info("M=getById, id={}", id);
        return new ProductRecord(
                id,
                faker.name().name(),
                faker.chuckNorris().fact(),
                faker.random().nextInt(1, 50),
                faker.random().nextBoolean()
        );
    }
}
