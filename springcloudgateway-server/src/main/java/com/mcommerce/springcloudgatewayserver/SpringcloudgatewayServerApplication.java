package com.mcommerce.springcloudgatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringcloudgatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudgatewayServerApplication.class, args);
	}

}
