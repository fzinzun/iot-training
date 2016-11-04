package com.raspberrypi.workshop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
public class DemoApplicationTests {

	public DemoApplicationTests(){
		System.setProperty("java.awt.headless", "false");
	}
	
	@Test
	public void contextLoads() {
	}

}
