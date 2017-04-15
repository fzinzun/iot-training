var express = require('express');
var app = express();

app.use(express.static(__dirname + '/public'));


// Render index.html on the main page, specify the root
app.get('/', function(req, res){
  res.sendFile("index.html", {root: '.'});
});

app.listen(3000, function () {
  console.log('Example app listening on port 3000!');
});