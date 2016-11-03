package com.raspberrypi.workshop.sensor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{
	
	@Autowired
	Sensor sensor;
	
    public static void main(String[] args) {
    	//-Djava.awt.headless=false
    	System.setProperty("java.awt.headless", "false");
        SpringApplication.run(DemoApplication.class, args);
    }

	@Override
	public void run(String... arg0) throws Exception {
		sensor.addListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				System.out.println(evt.getNewValue());
			}
		});
	}
	
}
