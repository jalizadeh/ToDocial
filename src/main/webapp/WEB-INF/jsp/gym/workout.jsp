<%@ include file="../common/header.jspf" %>

	<style type="text/css">
		#container {
			max-width: 800px;
			margin: 1em auto;
		}
	</style>

	<c:if test="${error != null}">
		<div class="alert alert-danger" role="alert">${error}</div>
	</c:if>

	<c:if test="${flash != null}">
		<div class="alert alert-${flash.status} alert-dismissible fade show"
			role="alert">
			${flash.message}
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
	</c:if>


	<div class="row flex-lg-row-reverse align-items-center g-5 py-5">
		<div class="col-10 col-sm-8 col-lg-6">
			<img src="/photo/gym/${workout.photo}" class="d-block mx-lg-auto img-fluid" alt="${workout.name}" width="400" height="400" loading="lazy">
		</div>
		<div class="col-lg-6">
			<sec:authorize access="hasAuthority('PRIVILEGE_UPDATE')">
				<a href="/gym/workouts/${workout.id}/edit" class="icon-link">Edit</a>
			</sec:authorize>
			<sec:authorize access="hasAuthority('PRIVILEGE_DELETE')">
				| <a href="/gym/workouts/${workout.id}/delete" class="icon-link text-danger">Delete</a>
			</sec:authorize>
			<h1 class="display-5 fw-bold text-body-emphasis lh-1 mb-3">${workout.name}</h1>
			<p class="lead">${workout.description}</p>
		</div>
	  </div>


	<div class="row">
		<div class="col">
			<div class="container px-4 py-5">
				<h2 class="pb-2 border-bottom">Muscle Group</h2>
			
				<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4 py-5">
					<c:forEach items="${workout.muscleCategory}" var="mc">
					  <div class="col d-flex align-items-start">
						<div>
						  <h3 class="fw-bold mb-0 fs-4 text-body-emphasis">${mc}</h3>
						  <p>Paragraph of text beneath the heading to explain the heading.</p>
						</div>
					  </div>
					</c:forEach>
				</div>
			</div>
		</div>

		<div class="col">
			<div class="container px-4 py-5">
				<h2 class="pb-2 border-bottom">Equipment</h2>
			
				<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4 py-5">
					<c:forEach items="${workout.equipment}" var="e">
					  <div class="col d-flex align-items-start">
						<div>
						  <h3 class="fw-bold mb-0 fs-4 text-body-emphasis">${e}</h3>
						  <p>Paragraph of text beneath the heading to explain the heading.</p>
						</div>
					  </div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>

	<c:if test="${viewerIsOwner == true}">
		<!-- This section is dedicated for the owner of the workout -->
		
		<div class="container px-4 py-5" id="featured-3">
			<h2 class="pb-2 border-bottom">In Your Plans</h2>

			<c:choose>
				<c:when test="${plans.size() > 0}">
					<div class="row g-4 py-5 row-cols-1 row-cols-lg-3">
						<c:forEach items="${plans}" var="plan">
							<div class="feature col">
								<h3 class="fs-2 text-body-emphasis">${plan.title}</h3>
								<p>${fn:substring(plan.gymPlanIntroduction.moreInfo, 0, 90)}...</p>
								<a href="/gym/plan/${plan.id}" class="icon-link">View plan <i class="fa fa-chevron-right"></i></a>
							</div>
						</c:forEach>
					</div>
				</c:when>    
				<c:otherwise>
					<div class="p-5 text-center bg-body-tertiary rounded-3">
						<h1 class="text-body-emphasis"><i class="fa fa-exclamation text-warning"></i></h1>
						<p class="lead">No plan found</p>
					</div>
				</c:otherwise>
			</c:choose>
		</div>

		<div class="container px-4 py-5" id="featured-3">
			<c:choose>
				<c:when test="${logsByDate.size() > 0}">
					<h2 class="pb-2 border-bottom">Your workout history for <mark>${logsByDate.size()}</mark> days</h2>

					<div id="container-chart-overall"></div>

					<div id="container-chart-fullhistory"></div>
					
					<!--
					<table class="table table-striped table-hover mt-3">
						<thead>
							<tr>
								<th>Date</th>
								<th>Set Number</th>
								<th>Weight</th>
								<th>Reps</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${history}" var="hi">
								<tr>
									<th>
										<p>${hi.logDate}</p>
									</th>
									<th>
										<p>${hi.setNumber}</p>
									</th>
									<th>
										<p>${hi.weight}</p>
									</th>
									<th>${hi.reps}</th>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					-->
				</c:when>    
				<c:otherwise>
					<h2 class="pb-2 border-bottom">Your workout history</h2>
					<div class="p-5 text-center bg-body-tertiary rounded-3">
						<h1 class="text-body-emphasis"><i class="fa fa-exclamation text-warning"></i></h1>
						<p class="lead">No history found</p>
						</div>
				</c:otherwise>
			</c:choose>
		</div>

		<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.18.1/moment.min.js"></script>


		<script type="text/javascript">

			Highcharts.chart('container-chart-fullhistory', {
				chart: {
					type: 'line'
				},
				title: {
					text: 'Workout progress by reps',
					align: 'left'
				},
				xAxis: {
					categories: ['rep 1', 'rep 2', 'rep 3', 'rep 4', 'rep 5']
				},
				yAxis: {
					title: {
						text: 'Weight (KG)'
					}
				},
				plotOptions: {
					line: {
						dataLabels: {
							enabled: true
						},
						enableMouseTracking: true
					}
				},
				series: [
					<c:forEach var="entry" items="${logsByDate}">
					{
						name : '<c:out value="${entry.key}" />',
						data: [
							<c:forEach var="log" items="${entry.value}">
								<c:out value="${log.weight}" />,
							</c:forEach>
						]
					},
					</c:forEach>
				]
			});
		</script>

		<script type="text/javascript">

			function calculateOverallAverage(data) {
				const flattenedData = data.flat(); // Flatten the array
				const sum = flattenedData.reduce((acc, value) => acc + value, 0);
				const average = sum / flattenedData.length;
				return average;
			}

			var dates = [
				<c:forEach var="entry" items="${logsByDate}">
					'<c:out value="${entry.key}" />',
				</c:forEach>
			];

			// Calculate distances between dates
			var distances = [];
			for (var i = 1; i < dates.length; i++) {
				var distance = moment(dates[i], 'YYYY-MM-DD').diff(moment(dates[i - 1], 'YYYY-MM-DD'), 'days');
				distances.push(distance);
			}

			const ranges = [
				<c:forEach var="stat" items="${logStats}">
					[<c:out value="${stat.min}"/>, <c:out value="${stat.max}"/>],
				</c:forEach>
			],	
			averages = [
				<c:forEach var="stat" items="${logStats}">
					[<c:out value="${stat.average}" />],
				</c:forEach>
			];

			// Calculate the average
			var average = calculateOverallAverage(averages)

			Highcharts.chart('container-chart-overall', {
				title: {
					text: 'Progress trend',
					align: 'left'
				},
				xAxis: {
					categories: [
						<c:forEach var="entry" items="${logsByDate}">
							'<c:out value="${entry.key}" />',
						</c:forEach>
					],
					labels: {
						useHTML: true,
						formatter: function () {
							var index = this.pos;
							return dates[index] + '<br>' + (index > 0 ? distances[index - 1] + ' days' : '');
						}
					},
					plotBands: [
						<c:forEach var="entry" items="${plansTimeline}">
							{
								color: 'red',
								from: <c:out value="${entry.value[0]}"/>,
								to: <c:out value="${entry.value[1]}"/>,
								useHTML: true,
								label: {
									text: '<a href="/gym/plan/<c:out value="${entry.key.id}" />"><c:out value="${entry.key.title}"/><br><c:out value="${entry.value[1] - entry.value[0] + 1}"/> sessions</a>'
								}
							},
						</c:forEach>	
					]
				},
				yAxis: {
					title: {
						text: 'Weight (kg)'
					},
					plotLines: [{
						value: average,
						color: 'red',
						dashStyle: 'shortdash',
						width: 2,
						label: {
							text: 'Avg: ' + average.toFixed(2),
							align: 'right',
							x: 0,
							y: 0
						}
						
					}]
				},

				tooltip: {
					crosshairs: true,
					shared: true,
					valueSuffix: 'Kg'
				},

				plotOptions: {
					line: {
						dataLabels: {
							enabled: true
						},
						enableMouseTracking: true
					}
				},
				series: [{
					name: 'Average',
					data: averages,
					zIndex: 1,
					marker: {
						fillColor: 'white',
						lineWidth: 2,
						lineColor: Highcharts.getOptions().colors[0]
					}
				},{
					name: 'Min - Max',
					data: ranges,
					type: 'arearange',
					lineWidth: 0,
					linkedTo: ':previous',
					color: Highcharts.getOptions().colors[0],
					fillOpacity: 0.3,
					zIndex: 0,
					marker: {
						enabled: false
					}
				}]
			});

		</script>

	</c:if>


<%@ include file="../common/footer.jspf" %>