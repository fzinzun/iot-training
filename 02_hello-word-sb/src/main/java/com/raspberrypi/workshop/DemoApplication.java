package com.raspberrypi.workshop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{
	
	@Value("${myProperty}")
	private int myProperty;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("Hello Word!!! ");
		System.out.println("myProperty: " + myProperty);
	}
	
}
