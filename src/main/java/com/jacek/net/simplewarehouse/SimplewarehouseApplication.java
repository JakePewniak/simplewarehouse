package com.jacek.net.simplewarehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.jacek.net.simplewarehouse.controller", "com.jacek.net.simplewarehouse.repositories",
        "com.jacek.net.simplewarehouse.services" })
public class SimplewarehouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimplewarehouseApplication.class, args);
	}

}
