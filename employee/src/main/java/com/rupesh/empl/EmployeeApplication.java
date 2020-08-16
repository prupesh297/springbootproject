package com.rupesh.empl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class EmployeeApplication{

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

}
