var stompClient = null;


function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            processMessage(JSON.parse(greeting.body));
        });
    });
}



/////////////////////////////////////////////////////////////////////////

function processMessage(message) {
	console.log(message);
	
	if(message.type == "humidity"){
		processHumidity(message.value);
	}else if(message.type == "pir"){
		processIpr(message.value);
	}else if(message.type == "temperature"){
		processTemperature(message.value);
	}else if(message.type == "encoder"){
		processEncoder(message.value);
	}
}

/////////////////////////////////////////////////////////////////////////





$(function () {
    //Auto-connect
    connect();
    processHumidity(false);
    processIpr(false);
});