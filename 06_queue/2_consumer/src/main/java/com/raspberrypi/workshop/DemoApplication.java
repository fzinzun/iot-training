package com.raspberrypi.workshop;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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
public class DemoApplication  implements CommandLineRunner {
	
	@Autowired
	RabbitConfig rabbit;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@PostConstruct
    public void init() throws IOException{
		
		String queueName = rabbit.channel.queueDeclare().getQueue();
		rabbit.channel.queueBind(queueName, rabbit.EXCHANGE_NAME, "");

		
		Consumer consumer = new DefaultConsumer(rabbit.channel) {
		  @Override
		  public void handleDelivery(String consumerTag, Envelope envelope,
		                             AMQP.BasicProperties properties, byte[] body) throws IOException {
		    String message = new String(body, "UTF-8");
		    System.out.println(" [x] Received '" + message + "'");
		  }
		};
		rabbit.channel.basicConsume(queueName, true, consumer);
		System.out.println(" [x] Waiting for new meesages");
    }

	@Override
	public void run(String... arg0) throws Exception {
		while(true);
	}
	
}
