package com.raspberrypi.workshop;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class DemoApplication {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(DemoApplication.class).headless(false).web(false).run(args);
	    AppPrincipalFrame appFrame = context.getBean(AppPrincipalFrame.class);
	    appFrame.setVisible(true);
	}
}
