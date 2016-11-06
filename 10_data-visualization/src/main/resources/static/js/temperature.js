
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
	        credits: {
	            enabled: false
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

function getTemperature(){

}

/*
* Gets Temperature from the server, add it to the graph and set a timeout 
* to request again
*/
function processTemperature(reading) {
   
   var series = chartTemperature.series[0],
   shift = series.data.length > 20; // shift if the series is 

   // add the point
   chartTemperature.series[0].addPoint(JSON.parse(reading), true, shift);
}



