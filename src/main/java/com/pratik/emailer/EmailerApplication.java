package com.pratik.emailer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;

@SpringBootApplication
public class EmailerApplication {

	public static void main(String[] args) {
		System.out.println("Setting certificates for HTTPS calls...");

		try {
			/**
			 * Had to import Mailgun's certificate to a truststore
			 * The truststore being used is /src/main/resources/cacerts
			 * */
			ClassPathResource classPathResource = new ClassPathResource("cacerts");
			InputStream inputStream = classPathResource.getInputStream();
			File tempFile = File.createTempFile("cacerts", "");
			FileUtils.copyInputStreamToFile(inputStream, tempFile);

			System.setProperty("javax.net.ssl.trustStore", tempFile.getAbsolutePath());
			System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
		} catch (Exception e) {
			System.out.println("Could not set truststore; HTTPS call might not work!");
			e.printStackTrace();
		}

		SpringApplication.run(EmailerApplication.class, args);
	}
}