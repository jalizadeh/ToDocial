<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"    uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Highcharts Example</title>

		<style type="text/css">
.highcharts-figure, .highcharts-data-table table {
  min-width: 320px; 
  max-width: 800px;
  margin: 1em auto;
}

#container {
  height: 400px;
}

.highcharts-data-table table {
font-family: Verdana, sans-serif;
border-collapse: collapse;
border: 1px solid #EBEBEB;
margin: 10px auto;
text-align: center;
width: 100%;
max-width: 500px;
}
.highcharts-data-table caption {
  padding: 1em 0;
  font-size: 1.2em;
  color: #555;
}
.highcharts-data-table th {
font-weight: 600;
  padding: 0.5em;
}
.highcharts-data-table td, .highcharts-data-table th, .highcharts-data-table caption {
  padding: 0.5em;
}
.highcharts-data-table thead tr, .highcharts-data-table tr:nth-child(even) {
  background: #f8f8f8;
}
.highcharts-data-table tr:hover {
  background: #f1f7ff;
}

		</style>
	</head>
	<body>
<script src="/resources/js/Highchart-8.0.4/highcharts.js"></script>
<script src="/resources/js/Highchart-8.0.4/highcharts-more.js"></script>
<script src="/resources/js/Highchart-8.0.4/modules/exporting.js"></script>
<script src="/resources/js/Highchart-8.0.4/modules/export-data.js"></script>
<script src="/resources/js/Highchart-8.0.4/modules/accessibility.js"></script>

<figure class="highcharts-figure">
    <div id="container"></div>
    <p class="highcharts-description">
        Chart showing ranges using horizontal columns. Each range is represented
        with a low and high value, with a bar between them.
    </p>
</figure>



		<script type="text/javascript">
		
		var json = [{
  month: 6,
  year: 2019,
  starts: 21278998,
  completes: 9309458
}, {
  month: 7,
  year: 2019,
  starts: 38850115,
  completes: 17790105
}];

var series1 = {
    name: 'starts',
    data: []
  },
  series2 = {
    name: 'completes',
    data: []
  };

json.forEach(elem => {
  series1.data.push({
    x: +new Date(elem.year, elem.month),
    y: elem.starts
  });

  series2.data.push({
    x: +new Date(elem.year, elem.month),
    y: elem.completes
  });
});


Highcharts.chart('container', {

    chart: {
        type: 'columnrange',
        inverted: true
    },

    accessibility: {
        description: 'Image description: A column range chart compares the monthly temperature variations throughout 2017 in Vik I Sogn, Norway. The chart is interactive and displays the temperature range for each month when hovering over the data. The temperature is measured in degrees Celsius on the X-axis and the months are plotted on the Y-axis. The lowest temperature is recorded in March at minus 10.2 Celsius. The lowest range of temperatures is found in December ranging from a low of minus 9 to a high of 8.6 Celsius. The highest temperature is found in July at 26.2 Celsius. July also has the highest range of temperatures from 6 to 26.2 Celsius. The broadest range of temperatures is found in May ranging from a low of minus 0.6 to a high of 23.1 Celsius.'
    },

    title: {
        text: 'Temperature variation by month'
    },

    subtitle: {
        text: 'Observed in Vik i Sogn, Norway, 2017'
    },

    xAxis: {
     type: 'datetime',
        categories: [
        /*
			<c:forEach items="${list}" var="data">
		 		<c:out value="${data.name}," />
		    </c:forEach>
		    */
		    'todo1', 'todo2', 'todo3'
		]
    },

    yAxis: {
        title: {
            text: 'Temperature ( °C )'
        }
    },

    tooltip: {
        valueSuffix: '°C'
    },

    
    legend: {
        enabled: false
    },

    series: [{
        name: 'Dates',
        data: [
        	[new Date(2020, 10,10),new Date(2020, 10,15)]
 			/*
		 	<c:forEach items="${list}" var="data">
		 		<c:out value="[${data.start},${data.end}]," />
		    </c:forEach>
		    */
        ]
    }
    /*,
    {
        name: 'Temperatures 2',
        data: [
        	[-19.0, 20.0]
        ]
    }
    */]

});

		</script>
	</body>
</html>
