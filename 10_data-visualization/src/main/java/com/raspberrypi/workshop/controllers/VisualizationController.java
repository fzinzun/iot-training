package com.raspberrypi.workshop.controllers;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.raspberrypi.workshop.config.RabbitConfig;
import com.raspberrypi.workshop.dto.ValueMessage;
import com.raspberrypi.workshop.util.StompClient;

@RestController
public class VisualizationController {
	
	private static Logger log = Logger.getLogger(VisualizationController.class);
	
	private StompClient stompClient = null;
	
	@Autowired
	RabbitConfig rabbit;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@PostConstruct
	public void init() throws InterruptedException, ExecutionException, IOException{
		this.stompClient  = new StompClient();
		
		
		String queueName = rabbit.channel.queueDeclare().getQueue();
		rabbit.channel.queueBind(queueName, rabbit.EXCHANGE_NAME, "");

		
		Consumer consumer = new DefaultConsumer(rabbit.channel) {
		  @Override
		  public void handleDelivery(String consumerTag, Envelope envelope,
		                             AMQP.BasicProperties properties, byte[] body) throws IOException {
		    String message = new String(body, "UTF-8");
		    System.out.println(" [Controller] Received '" + message + "'");
		    
		    ValueMessage valueMessage = objectMapper.readValue(message, ValueMessage.class);
		    
		    try {
				stompClient.sendMessage(valueMessage);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		};
		rabbit.channel.basicConsume(queueName, true, consumer);
		System.out.println(" [x] Waiting for new meesages");
	}
	
	
	
	@RequestMapping (value="/sendTemperature")
	public ValueMessage sendTemperature() throws InterruptedException, ExecutionException{
		log.info("Send Temperature message");
		Calendar cal = Calendar.getInstance();
		double temp = Math.random();
		long now = cal.getTime().getTime() * 1000;

        ValueMessage valueMessage = new ValueMessage("003",StompClient.TEMPERATURE,"[" + now + "," + temp + "]");
        this.stompClient.sendMessage(valueMessage);
		return valueMessage;
		
	}
	
	@RequestMapping (value="/sendEncoder")
	public ValueMessage sendEncoder() throws InterruptedException, ExecutionException{
		log.info("Send Encoder message");
		double rand = Math.random() * 100;

        ValueMessage valueMessage = new ValueMessage("004",StompClient.ENCODER,rand);
        this.stompClient.sendMessage(valueMessage);
		return valueMessage;
	}
	
	private static boolean humidity = false;
	private static boolean ipr = false;
	
	@RequestMapping (value="/sendHumidity")
	public ValueMessage sendHumidity() throws InterruptedException, ExecutionException{
		log.info("Send Humidity message");
		humidity = !humidity;

        ValueMessage valueMessage = new ValueMessage("001",StompClient.HUMIDITY,humidity);
        this.stompClient.sendMessage(valueMessage);
		return valueMessage;
	}
	
	
	@RequestMapping (value="/sendPir")
	public ValueMessage sendPir() throws InterruptedException, ExecutionException{
		return sendPirMessage();
	}

	private ValueMessage sendPirMessage() throws InterruptedException, ExecutionException {
		log.info("Send PIR message");
		ipr = !ipr;

        ValueMessage valueMessage = new ValueMessage("002",StompClient.PIR,ipr);
        this.stompClient.sendMessage(valueMessage);
		return valueMessage;
	}
	
	
	
	

}
