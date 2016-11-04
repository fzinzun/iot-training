package com.raspberrypi.workshop;

import java.io.IOException;

import javax.annotation.PostConstruct;

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
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;

@SpringBootApplication
@RestController
public class DemoApplication {
	
	@Value("${myProperty}")
	private int myProperty;
	
	private I2CDevice accele=null;
	

    public static void main(String[] args) throws InterruptedException {
    	SpringApplication.run(DemoApplication.class, args);
    }
    
    @PostConstruct
    public void init() throws UnsupportedBusNumberException, IOException{
    	I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
    	this.accele = bus.getDevice(0x68);
    	//System.out.println("data " + accele.getAddress());
    	//byte[] buf = new byte[256];
    	//int data = accele.read(0,buf,0,6);
    	//System.out.println("data " + data + " " + buf.length);
    }

    @RequestMapping(value = "/hi", method = RequestMethod.GET , produces="application/json; charset=UTF-8")
    public String getMessage(){
    	return "Hello World!!";
    }
    
    @RequestMapping(value = "/value", method = RequestMethod.GET , produces="application/json; charset=UTF-8")
    public String getValue() throws IOException{
    	byte[] buf = new byte[256];
    	int data = accele.read(buf,0,256);
    	
    	for(byte b:buf){
    		System.out.print(b + " ");
    	}System.out.println("");

    	String dataStr = "data " + data + " " + buf[0] + " " +  buf[1] + " " +  buf[2] + " ";
    	System.out.println(dataStr );
    	return dataStr;
    }
    
}
