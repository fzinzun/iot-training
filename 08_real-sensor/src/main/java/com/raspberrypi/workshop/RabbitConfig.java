package com.raspberrypi.workshop;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

@Component
public class RabbitConfig {
	
	public Channel channel;
    
	@Value("${spring.rabbitmq.host}")
    String hostName = "localhost";
	
	@Value("${spring.rabbitmq.username}")
    String userName = "guest";
	
	@Value("${spring.rabbitmq.password}")
    String pass = "guest";
	
	@Value("${spring.rabbitmq.port}")
    int port = 5672;
    
    public static String EXCHANGE_NAME = "simple.exchange";
    
    @PostConstruct
    public void createConnection() {
    	ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostName);
        factory.setUsername(userName);
        factory.setPassword(pass);
        factory.setPort(port);
        
        try {
			Connection connection = factory.newConnection();
			this.channel = connection.createChannel();
			this.channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	protected boolean sendMessage(String id, String type, String value) throws IOException, InterruptedException {
		//final String corrId = UUID.randomUUID().toString();

		byte[] body;
		body = ("{\"id\":\"" + id + "\", \"type\":\"" + type + "\", \"value\":\"" + value + "\"}").getBytes();

		synchronized (this) {
			this.channel.basicPublish(EXCHANGE_NAME,"",null, body);
		}
		return true;
	}

}