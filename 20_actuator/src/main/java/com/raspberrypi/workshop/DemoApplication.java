package com.raspberrypi.workshop;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{
	
	@Autowired
	RabbitConfig rabbit;


    public static void main(String[] args) throws InterruptedException {
    	SpringApplication.run(DemoApplication.class, args);
    }
    
    @PostConstruct
    public void init() throws IOException{
    	GpioController gpio = GpioFactory.getInstance();
        GpioPinDigitalOutput red = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00);
        GpioPinDigitalOutput green = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02);
        GpioPinDigitalOutput blue = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03);
        
    	/*myButton.addListener(this);*/
        
        String queueName = rabbit.channel.queueDeclare().getQueue();
		rabbit.channel.queueBind(queueName, rabbit.EXCHANGE_NAME, "");

		
		Consumer consumer = new DefaultConsumer(rabbit.channel) {
		  @Override
		  public void handleDelivery(String consumerTag, Envelope envelope,
		                             AMQP.BasicProperties properties, byte[] body) throws IOException {
		    String message = new String(body, "UTF-8");
		    System.out.println(" [x] Received '" + message + "'");
		    red.pulse(100);
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
