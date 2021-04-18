package com.sb.cb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PocSpringBootCircuitBreakerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocSpringBootCircuitBreakerApplication.class, args);
	}

}
