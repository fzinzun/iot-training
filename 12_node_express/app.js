var express = require('express');
var Stomp = require('stompjs');
var app = express();

app.use(express.static(__dirname + '/'));


// Use raw TCP sockets
var client = Stomp.overWS('ws://192.168.1.72:15674/ws');
// uncomment to print out the STOMP frames
// client.debug = console.log;

client.connect('admin', 'admin', function(frame) {
  console.log('connected to Stomp');

  client.subscribe('/exchange/simple.exchange', function(message) {
    console.log("received message " + message.body);
    // once we get a message, the client disconnects
    //client.disconnect();
  });

  //console.log ('sending a message');
  //client.send('/queue/myqueue', {}, 'Hello, node.js!');
});


// Render index.html on the main page, specify the root
app.get('/', function(req, res){
  res.sendFile("index.html", {root: '.'});
});

app.listen(3000, function () {
  console.log('Example app listening on port 3000!');
});