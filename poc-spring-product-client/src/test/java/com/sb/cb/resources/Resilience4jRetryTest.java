package com.sb.cb.resources;

import com.sb.cb.config.RestAssureConf;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpServerErrorException;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;



@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Resilience4jRetryTest extends RestAssureConf {

    @Test
    public void shouldPerformAsPerConfiguration() {
        RetryConfig config = RetryConfig.ofDefaults();
        Retry retry = Retry.of("get-retry", config);
        AtomicInteger count = new AtomicInteger(0);

        Callable<Integer> c = () -> {
            count.getAndIncrement();
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "vixi");
        };

        try {
            Retry.decorateCallable(retry, c).call();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThat(count).isEqualTo(3);
    }

    @Test
    public void shouldReturnSuccessInTheSecondInteraction() throws Exception {
        RetryConfig config = RetryConfig.ofDefaults();
        Retry retry = Retry.of("get-retry", config);
        AtomicInteger count = new AtomicInteger(0);

        Callable<Integer> c = () -> {
            /*
            * 1 -> Erro
            * 2 -> Sucesso
            * */
            if(count.intValue() <= 1) {
                count.incrementAndGet();
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "vixi");
            }
            return count.get();
        };

        final Integer call = Retry.decorateCallable(retry, c).call();

        assertThat(call).isEqualTo(2);
    }



}