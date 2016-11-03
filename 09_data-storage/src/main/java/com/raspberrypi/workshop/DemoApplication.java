package com.raspberrypi.workshop;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.raspberrypi.workshop.model.TmpValue;
import com.raspberrypi.workshop.sensor.temp.VirtualTemperatureSensor;

@SpringBootApplication
@RestController
public class DemoApplication {
	
	//@Value("${myProperty}")
	//private int myProperty;
	
	@Autowired
	VirtualTemperatureSensor temp;
	
	@Autowired
	TempService tmpService;
	
	private double value;

    public static void main(String[] args) {
    	System.setProperty("java.awt.headless", "false");
        SpringApplication.run(DemoApplication.class, args);
    }
    
    @PostConstruct
    public void init(){
    	temp.addListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				value = (double) evt.getNewValue();
				tmpService.addValue(new TmpValue(value));
			}
		});
    }
    
    @RequestMapping(value = "/hi", method = RequestMethod.GET , produces="application/json; charset=UTF-8")
    public String getMessage(){
    	return "Hello World!!";
    }
    
    @RequestMapping(value = "/value", method = RequestMethod.GET , produces="application/json; charset=UTF-8")
    public double getValue(){
    	return value;
    }
    
    @RequestMapping(value = "/values", method = RequestMethod.GET , produces="application/json; charset=UTF-8")
    public List<TmpValue>  getValues(){
    	return tmpService.getValueList();
    }
}
