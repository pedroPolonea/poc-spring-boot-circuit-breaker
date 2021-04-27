package com.sb.cb.factory;

import com.github.javafaker.Faker;
import com.sb.cb.record.ProductRecord;
import org.springframework.stereotype.Component;

@Component
public final class ProductFactory {

    private static Faker faker = new Faker();

    public static ProductRecord createProduct() {
        return new ProductRecord(
                1L,
                faker.gameOfThrones().character(),
                faker.chuckNorris().fact(),
                faker.random().nextInt(1, 100),
                faker.random().nextBoolean()
        );
    }
}
