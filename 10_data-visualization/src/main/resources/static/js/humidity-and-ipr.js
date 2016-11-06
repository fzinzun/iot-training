$(function () {
	
	$('#humidity-container-true').hide();
	$('#humidity-container-false').hide();
	$('#ipr-container-true').hide();
	$('#ipr-container-false').hide();
	
	function getHumidity(){
		$.ajax({
		       url: '/getHumidity',
		       success: function(reading) {

	    		   console.log(reading);
		    	   if (reading) {
		    		   $('#humidity-container-false').hide();
		    		   $('#humidity-container-true').show();
		    	   } else {
		    		   $('#humidity-container-true').hide();
		    		   $('#humidity-container-false').show();
		    	   }
	
		    	   setTimeout(getHumidity, 1000); 
		       },
		       cache: false
		   });
	}
	
	function getIpr(){
		$.ajax({
		       url: '/getIpr',
		       success: function(reading) {

	    		   console.log(reading);
		    	   if (reading) {
		    		   $('#ipr-container-false').hide();
		    		   $('#ipr-container-true').show();
		    	   } else {
		    		   $('#ipr-container-true').hide();
		    		   $('#ipr-container-false').show();
		    	   }
	
		    	   setTimeout(getIpr, 700); 
		       },
		       cache: false
		   });
	}
	
	getHumidity();
	getIpr();
});