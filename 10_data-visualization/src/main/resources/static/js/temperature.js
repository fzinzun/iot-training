
var chartTemperature;

$(function () { 

	chartTemperature = new Highcharts.Chart({
	        chart: {
	            renderTo: 'temperature-container',
	            defaultSeriesType: 'spline',
	            events: {
	                load: getTemperature
	            }
	        },
	        title: {
	            text: 'Live Ambient Temperature'
	        },
	        xAxis: {
	            type: 'datetime',
	            tickPixelInterval: 150,
	            maxZoom: 20 * 1000
	        },
	        yAxis: {
	            minPadding: 0.2,
	            maxPadding: 0.2,
	            title: {
	                text: 'Fahrenheit degrees',
	                margin: 80
	            }
	        },
	        series: [{
	            name: 'Ambient Temperature',
	            data: []
	        }]
	    });    
});

/*
* Gets Temperature from the server, add it to the graph and set a timeout 
* to request again
*/
function getTemperature() {
   $.ajax({
       url: '/getTemperature',
       success: function(reading) {

           var series = chartTemperature.series[0],
               shift = series.data.length > 20; // shift if the series is 
                                                // longer than 20

           // add the point
               chartTemperature.series[0].addPoint(reading.point, true, shift);
           
           // call it again after one second
           setTimeout(getTemperature, 1000);    
       },
       cache: false
   });
}