<%@ include file="../common/header.jspf"%>


<style type="text/css">
			.highcharts-figure, .highcharts-data-table table {
			    min-width: 320px;
			    max-width: 700px;
			    margin: 1em auto;
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
			.highcharts-axis-line {
			    fill: none;
			    stroke: #FFF;
			}

			.highcharts-xaxis-grid .highcharts-grid-line {
			    stroke-width: 1px;
			}

		</style>
		
		
		<div class="row">
			<div class="col-2">
				<a class="btn btn-success" href="/test-start" role="button">New Test</a>
			</div>
			
			<div class="col">
				<figure class="highcharts-figure">
				    <div id="container"></div>
				</figure>
			
				<script type="text/javascript">
					Highcharts.chart('container', {
					
					    chart: {
					        polar: true,
					        type: 'line'
					    },
					
					    accessibility: {
					        description: 'Current test'
					    },
					
					    title: {
					        text: 'All Tests Results',
					        x: -80
					    },
					
					    pane: {
					        size: '80%'
					    },
					
					    xAxis: {
					        categories: [
					        	<c:forEach var = "i" begin = "1" end = "8">
					        		'<spring:message code="test.lifecycle.t${i}.title" />',
					        	</c:forEach>
					        	
					        	],
					        tickmarkPlacement: 'on',
					        lineWidth: 0
					    },
					
					    yAxis: {
					        gridLineInterpolation: 'polygon',
					        lineWidth: 0,
					        min: 0
					    },
					
					    tooltip: {
					        shared: true
					        
					    },
					
					    legend: {
					        align: 'right',
					        verticalAlign: 'middle',
					        layout: 'vertical'
					    },
					
						//ONLY data of current test
					    series: [
						    <c:forEach items="${allTests}" var="test">
							    {
							        name: '<fmt:formatDate value="${test.test_date}" pattern="yyyy.MM.dd" />',
							        data: [${test.body_health}, ${test.mental_health}, ${test.financial}, ${test.business}, ${test.life_style}, ${test.spiritual}, ${test.family}, ${test.relationship}],
							        pointPlacement: 'on'
							    },
						   	</c:forEach>
					    ],
					
					    responsive: {
					        rules: [{
					            condition: {
					                maxWidth: 500
					            },
					            chartOptions: {
					                legend: {
					                    align: 'center',
					                    verticalAlign: 'bottom',
					                    layout: 'horizontal'
					                },
					                pane: {
					                    size: '70%'
					                }
					            }
					        }]
					    }
					
					});
				</script>
			</div>
			
			<div class="col-2">
			</div>
		</div>


<%@ include file="../common/footer.jspf"%>