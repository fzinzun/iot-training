package com.raspberrypi.workshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {
	

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    
    
    @RequestMapping(value = "/hi", method = RequestMethod.GET , produces="application/json; charset=UTF-8")
    public String getMessage(){
    	return "Hello World!!";
    }
}
