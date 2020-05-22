<%@ include file="../common/header.jspf" %>
	
	<style type="text/css">
		#container {
		    max-width: 800px;
		    margin: 1em auto;
		}
	</style>


	<div id="container-chart"></div>



	<script type="text/javascript">
		//source: https://jsfiddle.net/gh/get/library/pure/highcharts/highcharts/tree/master/samples/gantt/demo/with-navigation
		
		Highcharts.ganttChart('container-chart', {

	    title: {
	        text: 'Gantt Chart with Navigation'
	    },
	
	    yAxis: {
	        uniqueNames: false
	    },
	
	    navigator: {
	        enabled: true,
	        liveRedraw: true,
	        series: {
	            type: 'gantt',
	            pointPlacement: 0.5,
	            pointPadding: 0.25
	        },
	        yAxis: {
	            min: 0,
	            max: 0,
	            reversed: true,
	            categories: []
	        }
	    },
	    scrollbar: {
	        enabled: true
	    },
	    rangeSelector: {
	        enabled: true,
	        selected: 0
	    },
	
	    series: [
	    	<c:forEach items="${list}" var="data" varStatus="i">
				{
					name : '<c:out value="${data.name}" />',
					data : [ {
						<fmt:formatDate value="${data.start}" pattern="MM" var="startMonth"/>
						<c:set var="startMonth" value="${startMonth - 1}" />
						<fmt:formatDate value="${data.end}" pattern="MM" var="endMonth"/>
						<c:set var="endMonth" value="${endMonth - 1}" />

						start : Date.UTC(
							<fmt:formatDate value="${data.start}" pattern="yyyy" />,
							<c:out value="${startMonth}" />,
							<fmt:formatDate value="${data.start}" pattern="dd" />
							),
						end : Date.UTC(
							<fmt:formatDate value="${data.end}" pattern="yyyy" />,
							<c:out value="${endMonth}" />,
							<fmt:formatDate value="${data.end}" pattern="dd" />
							),
						completed : <c:out value="${data.progress}" />,
						name : '<c:out value="${data.name}" />',
					}]
				},
			</c:forEach>
	    ]
	});
		
	</script>

<%@ include file="../common/footer.jspf" %>