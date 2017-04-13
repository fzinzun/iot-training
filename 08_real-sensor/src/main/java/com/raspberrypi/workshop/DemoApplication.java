package com.raspberrypi.workshop;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

@SpringBootApplication
@RestController
public class DemoApplication implements GpioPinListenerDigital {
	
	@Autowired
	RabbitConfig rabbit;

	private int value = 0;
	

    public static void main(String[] args) throws InterruptedException {
    	SpringApplication.run(DemoApplication.class, args);
    }
    
    @PostConstruct
    public void init(){
    	GpioController gpio = GpioFactory.getInstance();
        GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07,"MyButton",PinPullResistance.PULL_DOWN);
        
    	myButton.addListener(this);
    }

    @RequestMapping(value = "/hi", method = RequestMethod.GET , produces="application/json; charset=UTF-8")
    public String getMessage(){
    	return "Hello World!!";
    }
    
    @RequestMapping(value = "/value", method = RequestMethod.GET , produces="application/json; charset=UTF-8")
    public int getValue(){
    	return value;
    }
	
	

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        // display pin state on console
        System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = "
                + event.getState());
        if(event.getState()== PinState.HIGH){
        	value = 0;
        }else{
        	value = 1;
        }
        
        try {
			rabbit.sendMessage("003","pir",""+value);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
