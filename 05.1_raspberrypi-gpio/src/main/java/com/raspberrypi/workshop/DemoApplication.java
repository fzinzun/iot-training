package com.raspberrypi.workshop;

import javax.annotation.PostConstruct;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
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
	
	//@Value("${myProperty}")
	private int myProperty;
	
	private int value = 0;
	
	
	String topic        = "itq/room1/temp/value";
    int qos             = 2;
    String broker       = "tcp://192.168.2.21:1883";
    String clientId     = "equipo1";
    MemoryPersistence persistence = new MemoryPersistence();
    MqttClient sampleClient = null;
	

    public static void main(String[] args) throws InterruptedException {
    	SpringApplication.run(DemoApplication.class, args);
    }
    
   @PostConstruct
    public void init(){
    	GpioController gpio = GpioFactory.getInstance();
        GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07,"MyButton",PinPullResistance.PULL_DOWN);
        
    	myButton.addListener(this);
	    
    	//Connect to MQTT Server
    	connectToServer();
    }

	
	

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        // display pin state on console
        System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = "
                + event.getState());
        if(event.getState()== PinState.HIGH){
        	value = 0;
        	sendMessage("0");
        }else{
        	value = 1;
        	sendMessage("1");
        }
    }
    
    
    
    
    
    public void connectToServer(){
    	try {
            sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }
    
    public void disconnectFromServer(){
    	try {
    		sampleClient.disconnect();
    	    System.out.println("Disconnected");
    	    System.exit(0);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }
    
    public void sendMessage(String content){
    	try {
	    	System.out.println("Publishing message: "+content);
	        MqttMessage message = new MqttMessage(content.getBytes());
	        message.setQos(qos);
	        sampleClient.publish(topic, message);
	        System.out.println("Message published");
    	} catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }
    
    
}
