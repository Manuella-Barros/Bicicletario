package com.trabalho.bicicletario;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BicicletarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(BicicletarioApplication.class, args);

		Dotenv dotenv = Dotenv.load();
		String dbUsername = dotenv.get("DATABASE_USERNAME");
		String dbPassword = dotenv.get("DATABASE_PASSWORD");
		String dbUrl = dotenv.get("DATABASE_URL");
	}

}
