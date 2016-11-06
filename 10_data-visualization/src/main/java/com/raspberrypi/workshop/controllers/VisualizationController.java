package com.raspberrypi.workshop.controllers;

import java.util.Calendar;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raspberrypi.workshop.dto.EncoderDto;
import com.raspberrypi.workshop.dto.TemperatureDto;

@RestController
public class VisualizationController {

	@RequestMapping (value="/getTemperature")
	public TemperatureDto getTemperature(){
		
		Calendar cal = Calendar.getInstance();
		
		// RabbitMq here to read temp
		double temp = Math.random();
		long now = cal.getTime().getTime() * 1000;
		
		TemperatureDto tempReading = new TemperatureDto( "Temperature",new  double[]{ now, temp} );
				
		return tempReading;
	}
	
	@RequestMapping (value="/getEncoder")
	public EncoderDto getEncoder(){
		
		Calendar cal = Calendar.getInstance();
		
		// RabbitMq here to read temp
		double rand = Math.random() * 100;
		long now = cal.getTime().getTime() * 1000;
		
		EncoderDto encoderDto = new EncoderDto("Encoder", rand, false);
				
		return encoderDto;
	}
	
	private static boolean humidity = false;
	private static boolean ipr = true;
	
	@RequestMapping (value="/getHumidity")
	public boolean getHumidity(){
		humidity = !humidity;
		return humidity;
	}
	
	@RequestMapping (value="/getIpr")
	public boolean getIpr(){
		ipr = !ipr;
		return ipr;
	}
	
	
}
