package com.sb.cb.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestAssureConf {

    public static RequestSpecification specification;

    @LocalServerPort
    private int port;

    @BeforeAll
    void upClass() {
        RequestSpecBuilder specBuilder = new RequestSpecBuilder();
       // specBuilder.addHeader("version", "V1");
        specBuilder.setBaseUri("http://localhost/");
        specBuilder.setAccept(MediaType.APPLICATION_JSON_VALUE);
        specBuilder.setPort(port);

        specification = specBuilder.build();
    }
}
