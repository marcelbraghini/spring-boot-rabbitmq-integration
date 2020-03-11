package br.com.marcelbraghini.springbootrabbitmqintegration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootRabbitmqIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRabbitmqIntegrationApplication.class, args);
	}

}
