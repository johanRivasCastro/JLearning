package com.johanrivas.jlearning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class JlearningApplication implements CommandLineRunner {

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	public static void main(String[] args) {
		SpringApplication.run(JlearningApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String pass = "1234";

		String bcryptPass = bcrypt.encode(pass);
		System.out.println(bcryptPass);
	}

}
