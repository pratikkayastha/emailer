package com.pratik.emailer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
public class EmailerApplication {

	public static void main(String[] args) {
		System.out.println("Setting certificates for HTTPS calls...");

		try {
			/**
			 * Had to import Mailgun's certificate to a truststore
			 * The truststore being used is /src/main/resources/cacerts
			 * */
			String path = new ClassPathResource("cacerts").getFile().getPath();
			System.setProperty("javax.net.ssl.trustStore", path);
			System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
		} catch (Exception e) {
			System.out.println("Could not set truststore; HTTPS call might not work!");
		}

		SpringApplication.run(EmailerApplication.class, args);
	}
}
