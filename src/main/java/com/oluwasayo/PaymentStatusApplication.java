package com.oluwasayo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PaymentStatusApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentStatusApplication.class, args);
	}

}
