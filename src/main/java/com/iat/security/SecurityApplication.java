package com.iat.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
public class SecurityApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;	

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String passw = "123456";

		for (int i = 0; i < 2; i++) {
			String ps = passwordEncoder.encode(passw);
			System.out.println(ps);
		}	
	}

}
