

 $(document).ready(function() {
  if(window.WebSocket) {
    var client, destination;
    var url = "ws://192.168.1.73:15674/ws";
    var login = "guest";
    var passcode = "guest";


    destination = "/exchange/simple.exchange";
    client = Stomp.client(url);

    // the client is notified when it is connected to the server.
    client.connect(login, passcode, function(frame) {

      client.debug("connected to Stomp");

      client.subscribe(destination, function(message) {

        console.log(message);
        let color = "defaultToast";
        let messageBody = JSON.parse(message.body);
        let messageId = messageBody.id;
        let messageType = messageBody.type;
        let messageValue = messageBody.value;

        //Format the message and color
        if(messageType == "temperature"){
            color = "redToast"
        }

        $("#messages").append("<paper-toast class='" + color + "' text='Message Type: " + messageType + " | Value: " + messageValue + " | Id: " +messageId + "' opened></paper-toast>");

      });

    });
  } else {
    $("#connect").html("\
        <h1>Get a new Web Browser!</h1>\
        <p>\
        Your browser does not support WebSockets. This example will not work properly.<br>\
        Please use a Web Browser with WebSockets support (WebKit or Google Chrome).\
        </p>\
    ");
  }
});