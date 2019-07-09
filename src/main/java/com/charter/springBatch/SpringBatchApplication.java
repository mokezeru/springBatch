package com.charter.springBatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class SpringBatchApplication {

	public static void main(String[] args) {
		System.setProperty("input", "file:" + new File("C:/Charter/springBatch/students.csv").getAbsolutePath());
		System.setProperty("output", "file:" + new File("C:/Charter/springBatch/age.csv").getAbsolutePath());
		SpringApplication.run(SpringBatchApplication.class, args);
	}

}
