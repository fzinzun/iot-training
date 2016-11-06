package com.raspberrypi.workshop.util;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;

import com.raspberrypi.workshop.dto.ValueMessage;

/**
 * Created by nick on 30/09/2015.
 */
public class StompClient {

    private static Logger logger = Logger.getLogger(StompClient.class);

	public static final String HUMIDITY = "humidity";
    
    private final static WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

	public static final String PIR = "pir";

	public static final String TEMPERATURE = "temperature";

	public static final String ENCODER = "encoder";
    
    private ListenableFuture<StompSession> listenableStompSession = null;
    private StompSession stompSession = null;

    public ListenableFuture<StompSession> connect() throws InterruptedException, ExecutionException {

        Transport webSocketTransport = new WebSocketTransport(new StandardWebSocketClient());
        List<Transport> transports = Collections.singletonList(webSocketTransport);

        SockJsClient sockJsClient = new SockJsClient(transports);
        sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());

        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);

        String url = "ws://{host}:{port}/gs-guide-websocket";
        this.listenableStompSession = stompClient.connect(url, headers, new MyHandler(), "localhost", 8080);
        this.stompSession = this.listenableStompSession.get();
        return this.listenableStompSession;
    }

    public void subscribeGreetings(StompSession stompSession) throws ExecutionException, InterruptedException {
        stompSession.subscribe("/topic/greetings", new StompFrameHandler() {

            public Type getPayloadType(StompHeaders stompHeaders) {
                return byte[].class;
            }

            public void handleFrame(StompHeaders stompHeaders, Object o) {
                logger.info("Received greeting " + new String((byte[]) o));
            }
        });
    }

    public void sendMessage( ValueMessage value) throws InterruptedException, ExecutionException {
    	if(!isConnected()){
    		connect();
    	}
        this.stompSession.send("/topic/greetings", value.toJson().getBytes());
    }

    private boolean isConnected() {
		if(stompSession == null)
			return false;
		return true;
	}

	private class MyHandler extends StompSessionHandlerAdapter {
        public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
            logger.info("Now connected");
        }
    }
    
   
}