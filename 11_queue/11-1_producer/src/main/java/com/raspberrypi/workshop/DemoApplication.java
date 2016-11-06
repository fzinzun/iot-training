package com.raspberrypi.workshop;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

@SpringBootApplication
@RestController
public class DemoApplication {
	
	@Autowired
	RabbitConfig rabbit;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@RequestMapping(value = "/send", method = RequestMethod.GET , produces="application/json; charset=UTF-8")
    public void getValue() throws IOException, InterruptedException{
		rabbit.sendMessage("100","pir", "true");
    }
}
