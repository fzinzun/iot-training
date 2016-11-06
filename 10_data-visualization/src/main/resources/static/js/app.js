var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            processMessage(JSON.parse(greeting.body));
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
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
	
	
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

/////////////////////////////////////////////////////////////////////////





$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    
    //Auto-connect
    connect();
    processHumidity(false);
    processIpr(false);
});