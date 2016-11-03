package com.raspberrypi.workshop.sensor.temp;

import java.beans.PropertyChangeListener;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.raspberrypi.workshop.sensor.Sensor;

@Component
public class VirtualTemperatureSensor implements Sensor{
	private VirtualTemperatureFrame frame ;
	
	@PostConstruct
	public void init(){
		frame = new VirtualTemperatureFrame();
		frame.setVisible(true);
	}

	@Override
	public void addListener(PropertyChangeListener listener) {
		this.frame.addPropertyChangeListener("value",listener);
	}

}
