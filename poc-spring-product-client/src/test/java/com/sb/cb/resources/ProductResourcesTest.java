package com.sb.cb.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.sb.cb.config.RestAssureConf;
import com.sb.cb.config.WireMockConfig;
import com.sb.cb.factory.ProductFactory;
import com.sb.cb.record.ProductRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.sb.cb.factory.ProductFactory.createProduct;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.OK;


@ActiveProfiles("test")
@ContextConfiguration(classes = { WireMockConfig.class })
class ProductResourcesTest  extends RestAssureConf {

    private static String URL_PRODUCT = "/products/1";

    @Autowired
    private WireMockServer wireMockServer;

    @AfterEach
    public void afterEach(){
        wireMockServer.resetAll();
    }

    @Test
    public void shouldReturnOk(){
        final ProductRecord productRecord = createProduct();
        createStubGetOk(productRecord, URL_PRODUCT, 0);
        final ProductRecord newProductRecord = given()
                    .spec(specification)
                .when()
                    .get(URL_PRODUCT)
                .then()
                    .statusCode(OK.value())
                    .extract()
                    .as(ProductRecord.class);

        assertEquals(productRecord.id(), newProductRecord.id());
        assertEquals(productRecord.name(), newProductRecord.name());
        assertEquals(productRecord.active(), newProductRecord.active());
        assertEquals(productRecord.amount(), newProductRecord.amount());
        assertEquals(productRecord.description(), newProductRecord.description());

    }

    private void createStubGetOk(final Object object, final String url, final int fixedDelay) {
        final byte[] json = createJson(object);
        wireMockServer.stubFor(
                WireMock.get(urlEqualTo(url))
                        .willReturn(
                                aResponse()
                                        .withStatus(OK.value())
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                        .withBody(json)
                                        .withFixedDelay(fixedDelay)
                        )
        );
    }

    private byte[] createJson(final Object object){
        final ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = null;
        try {
            bytes = objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return bytes;
    }

}