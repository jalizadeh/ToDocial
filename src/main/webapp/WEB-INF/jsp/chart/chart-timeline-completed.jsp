<%@ include file="../common/header.jspf" %>
	
	<style type="text/css">
		#container {
			min-width: 300px;
			max-width: 800px;
			height: 100%;
			margin: 1em auto;
		}
		
		.highcharts-xrange-series .highcharts-point {
			stroke-width: 1px;
			stroke: gray;
		}
		
		.highcharts-partfill-overlay {
			fill: #010101;
			fill-opacity: 0.3;
		}
		
		.highcharts-data-label text {
			fill: white;
			text-shadow: 1px 1px black, -1px 1px black, -1px -1px black, 1px -1px black;
		}
		
		.highcharts-plot-line {
		    fill: none;
		    stroke: #f00;
		    stroke-width: 1px;
		    stroke-dasharray: 4,3;
		}
	</style>


	<div id="container-chart"></div>


	<jsp:useBean id="today" class="java.util.Date" />
	<fmt:formatDate value="${today}" pattern="MM" var="todayMonth"/>
	<c:set var="todayMonth" value="${todayMonth - 1}" />

	<script type="text/javascript">
		Highcharts.chart('container-chart', {
			chart : {
				type : 'xrange',
				styledMode : true
			},
			title : {
				text : 'Timeline of your Completed Todos'
			},
			tooltip: {
			    crosshairs: [true, true],
			},
			plotOptions: {
		        series: {
		            animation: {
		                duration: 2000
		            }
		        }
		    },
			xAxis : {
				type : 'datetime',
				plotLines: [{
		            value: Date.UTC(
						<fmt:formatDate value="${today}" pattern="yyyy" />,
						<c:out value="${todayMonth}" />,
						<fmt:formatDate value="${today}" pattern="dd" />
					),
		            dashStyle: 'Dash',
		            width: 1,
		            color: '#f00'
		        }]
			},
			yAxis : {
				title : {
					text : ''
				},
				categories : [ 
					<c:forEach items="${list}" var="data">
		 				'<c:out value="${data.name}" />',
		    		</c:forEach>
				],
				reversed : true
			},
			series : [
				<c:forEach items="${list}" var="data" varStatus="i">
					{
					name : '<c:out value="${data.name}" />',
					pointWidth : 20,
					data : [ {
						<fmt:formatDate value="${data.start}" pattern="MM" var="startMonth"/>
						<c:set var="startMonth" value="${startMonth - 1}" />
						<fmt:formatDate value="${data.end}" pattern="MM" var="endMonth"/>
						<c:set var="endMonth" value="${endMonth - 1}" />

						x : Date.UTC(
							<fmt:formatDate value="${data.start}" pattern="yyyy" />,
							<c:out value="${startMonth}" />,
							<fmt:formatDate value="${data.start}" pattern="dd" />
							),
						x2 : Date.UTC(
							<fmt:formatDate value="${data.end}" pattern="yyyy" />,
							<c:out value="${endMonth}" />,
							<fmt:formatDate value="${data.end}" pattern="dd" />
							),
						y : <c:out value="${i.index}"/>,
						partialFill : <c:out value="${data.progress}" />,
					}],
					dataLabels : {
						enabled : true
					}
				},
			</c:forEach>
			]

		});
	</script>

<%@ include file="../common/footer.jspf" %>