package com.raspberrypi.workshop.config;

import java.io.IOException;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Component
public class RabbitConfig {
	
	public Channel channel;
    
     
    String hostName = "192.168.0.10";
    String userName = "user";
    String pass = "pass";
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
    
	protected boolean sendMessage(String teamName,double value) throws IOException, InterruptedException {
		final String corrId = UUID.randomUUID().toString();

		byte[] body;
		body = ("Id:"+corrId+" TeamName:" + teamName + " Value:" + value).getBytes();

		synchronized (this) {
			this.channel.basicPublish(EXCHANGE_NAME,"",null, body);
		}
		return true;
	}

}