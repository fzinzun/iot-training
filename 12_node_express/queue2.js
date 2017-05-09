

 $(document).ready(function() {



      // the client is notified when it is connected to the server.
          var number = 0;

          var timerID = setInterval(function() {
                var message = "Sensor X | Valor: ";
                var color = "redToast";

                if(number > 5){
                    message = "Sensor Y | Valor: ";
                    color = "blueToast"
                }
                if(number > 10){
                    message = "Sensor Y | Valor: ";
                    color = "greenToast"
                }

                $("#messages").append("<paper-toast class='" + color + "' text='" + message + " " + number  + "' opened></paper-toast>");
                number ++;
          }, 1000);




});