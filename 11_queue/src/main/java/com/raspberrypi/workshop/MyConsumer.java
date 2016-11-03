package com.raspberrypi.workshop;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class MyConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println(new String(message.getBody()));
    }
}