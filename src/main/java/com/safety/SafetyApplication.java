package com.safety;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration
@EnableTransactionManagement
@SpringBootApplication
public class SafetyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafetyApplication.class, args);
	}
}
