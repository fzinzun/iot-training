
	$('#humidity-container-true').hide();
	$('#humidity-container-false').hide();
	$('#ipr-container-true').hide();
	$('#ipr-container-false').hide();
	
	function processHumidity(reading){
	   if (reading == "true") {
		   $('#humidity-container-false').hide();
		   $('#humidity-container-true').show();
	   } else {
		   $('#humidity-container-true').hide();
		   $('#humidity-container-false').show();
	   }
	}
	
	function processIpr(reading){
	   if (reading == "true") {
		   $('#ipr-container-false').hide();
		   $('#ipr-container-true').show();
	   } else {
		   $('#ipr-container-true').hide();
		   $('#ipr-container-false').show();
	   }
	}
	
	//getHumidity();
	//getIpr();
