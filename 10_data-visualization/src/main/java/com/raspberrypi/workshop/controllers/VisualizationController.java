package com.raspberrypi.workshop.controllers;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raspberrypi.workshop.dto.ValueMessage;
import com.raspberrypi.workshop.util.StompClient;

@RestController
public class VisualizationController {
	
	private static Logger log = Logger.getLogger(VisualizationController.class);
	
	private StompClient stompClient = null;
	
	@PostConstruct
	public void init() throws InterruptedException, ExecutionException{
		this.stompClient  = new StompClient();
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
		log.info("Send PIR message");
		ipr = !ipr;

        ValueMessage valueMessage = new ValueMessage("002",StompClient.PIR,ipr);
        this.stompClient.sendMessage(valueMessage);
		return valueMessage;
	}
	
	
	
	 /*@MessageMapping("/hello")
	 @SendTo("/topic/greetings")
	 public ValueMessage greeting(HelloMessage message) {
		 System.out.println("greeting");
	      return new ValueMessage("","","Hello, " + message.getName() + "!");
	}*/

}
