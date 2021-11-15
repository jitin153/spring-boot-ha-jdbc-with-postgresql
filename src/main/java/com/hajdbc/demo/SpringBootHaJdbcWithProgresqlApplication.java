package com.hajdbc.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;

@SpringBootApplication
@EnableMBeanExport
public class SpringBootHaJdbcWithProgresqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHaJdbcWithProgresqlApplication.class, args);
	}

}
