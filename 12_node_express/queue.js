

 $(document).ready(function() {
  if(window.WebSocket) {
    var client, destination;

      var url = "ws://192.168.1.72:15674/ws";
      var login = "admin";
      var passcode = "admin";
      destination = "/exchange/simple.exchange";
      client = Stomp.client(url);


      // the client is notified when it is connected to the server.
      client.connect(login, passcode, function(frame) {
        client.debug("connected to Stomp");
        client.subscribe(destination, function(message) {
          $("#messages").append("<p>" + message.body + "</p>\n");
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