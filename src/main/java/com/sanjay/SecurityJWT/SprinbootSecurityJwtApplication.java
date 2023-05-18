package com.sanjay.SecurityJWT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan({"src/main/java/CONTROLLER","src/main/java/DAO","src/main/java/ENTITY","src/main/java/SERVICE"})
public class SprinbootSecurityJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SprinbootSecurityJwtApplication.class, args);
	}

}
