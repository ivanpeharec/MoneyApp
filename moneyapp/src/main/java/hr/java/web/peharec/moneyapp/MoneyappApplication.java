package hr.java.web.peharec.moneyapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
@Configuration
@ComponentScan
@SpringBootApplication
@EnableAutoConfiguration
public class MoneyappApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyappApplication.class, args);
	}

}
