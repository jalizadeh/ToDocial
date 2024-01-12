<%@ include file="../common/header.jspf" %>

	<div class="row">
		<div class="col-8">
			<div class="row mt-2">
				<div class="col">
					<h2>${plan.title} <span class="badge rounded-pill bg-light text-dark">${plan.numberOfWeeks} weeks</span> <span class="badge rounded-pill bg-light text-dark">${plan.numberOfDays} days</span></h2>
					<h5 class="card-title">
						<div class="progress">
							<div class="progress-bar" role="progressbar" style="width: ${plan.progress}%;" aria-valuenow="${plan.progress}" aria-valuemin="0" aria-valuemax="100">${plan.progress}%</div>
						</div>	
					</h5>
				</div>
			</div>
			<div class="row">
				<c:forEach var = "i" begin = "1" end = "${plan.numberOfWeeks}">
					<div class="col-4 p-2">
						<div class="card p-2" >
							<div class="card-body">
								<h5 class="card-title">Week #${i}</h5>

								<!-- calculating week's progress -->
								<c:set var="totalProgress" value="0" />
								<c:forEach items="${pwd}" var="singlePWD">
									<c:if test="${singlePWD.weekNumber == i}">
										<c:set var="totalProgress" value="${totalProgress + singlePWD.progress}" />
									</c:if>
								</c:forEach>
								<fmt:parseNumber var="totalProgress" value="${totalProgress / plan.numberOfDays}" integerOnly="true"/>

								<h5 class="card-title">
									<div class="progress">
										<div class="progress-bar" role="progressbar" style="width: ${totalProgress}%;" aria-valuenow="${totalProgress}" aria-valuemin="0" aria-valuemax="100">${totalProgress}%</div>
									</div>	
								</h5>
								
								<c:forEach items="${days}" var="day">
									<p class="card-text">
										<!-- TODO: change day color by it's progress -->
										<c:choose>
											<c:when test="${day.progress == 100}">
												<span class="badge rounded-pill bg-success">${day.dayNumber}</span> <a href="${plan.id}/week/${i}/day/${day.dayNumber}">${day.focus}</a>
											</c:when>    
											<c:otherwise>
												<span class="badge rounded-pill bg-secondary">${day.dayNumber}</span> <a href="${plan.id}/week/${i}/day/${day.dayNumber}">${day.focus}</a>
											</c:otherwise>
										</c:choose>
									</p>
								</c:forEach>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>

		<!-- right pane -->
		<div class="col-4">
			<c:if test="${plan.active == false and plan.startDate ne null and plan.completeDate ne null}">
				<div class="row">
					<p>Started at: ${plan.startDate}</p>
					<p>Completed at: ${plan.completeDate}</p>
				</div>
			</c:if>

			<div class="row mt-2">
				<div class="col border-bottom">
					<h2>Summary</h2>
					<br/>
				</div>
			</div>

			<div class="row p-2">
				<div>
					<p>${plan.gymPlanIntroduction.moreInfo}</p>
					<p>Main Goal: ${plan.gymPlanIntroduction.mainGoal}</p>
					<p>Workout Type: ${plan.gymPlanIntroduction.workoutType}</p>
					<p>Training Level: ${plan.gymPlanIntroduction.trainingLevel}</p>
					<p>Program Duration: ${plan.numberOfWeeks} weeks</p>
					<p>Days per Week: ${plan.numberOfDays} days</p>
					<p>Time per Workout: ${plan.gymPlanIntroduction.timePerWorkout}</p>
					<p>Equipment Required: ###</p>
					<c:choose>
						<c:when test="${plan.gymPlanIntroduction.targetGender == 0}">
							<p>Target Gender: Men</p>
						</c:when>
						<c:otherwise>
							<p>Target Gender: Women</p>
						</c:otherwise>
					</c:choose>
				</div>
			</div>

			
			<c:if test="${plan.active == false and plan.startDate == null and plan.completeDate == null}">
				<div class="row">
					<a class="btn btn-success" href="${plan.id}/start-plan" role="button">Start plan</a>
				</div>
			</c:if>

			<c:if test="${plan.active == true and plan.startDate ne null and plan.completeDate == null}">
				<div class="row">
					<a class="btn btn-success" href="${plan.id}/end-plan" role="button">End plan</a>
				</div>
			</c:if>
		</div>
	</div>


	</style>
	<div class="my-3">
		<h2 class="border-bottom">Statistics</h2>

		<div id="container-chart-planSessionsTimeline"></div>

		<div class="row">
			<div class="col">
				<div id="container-chart-muscles"></div>
			</div>
			<div class="col">
				<div id="container-chart-muscles-percentage"></div>
			</div>
		</div>
	</div>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.18.1/moment.min.js"></script>

	<script type="text/javascript">

		var dates = [
			<c:forEach var="session" items="${allSessionsForPlan}">
				'<c:out value="${session.logDate}" />',
			</c:forEach>
		];

		// Calculate distances between dates
		var distances = [];
		for (var i = 1; i < dates.length; i++) {
			var distance = moment(dates[i], 'YYYY-MM-DD').diff(moment(dates[i - 1], 'YYYY-MM-DD'), 'days');
			distances.push(-1 * distance);
		}
		distances.push(0);

		var sum = distances.reduce(function (accumulator, currentValue) { 
			return accumulator + currentValue; 
		}, 0);

		Highcharts.chart('container-chart-planSessionsTimeline', {
			chart: {
				type: 'bar'
			},
			title: {
				text: 'Sessions timeline<br><c:out value="${allSessionsForPlan.size()}" /> sessions in ' + sum + ' days',
				align: 'left'
			},
			xAxis: {
				tickInterval: 1,
				categories: ['Days between']
			},
			yAxis: {
				min: 0,
				max: sum,
				title: {
					text: 'Accumulative days'
				}
			},
			legend: {
				reversed: true
			},
			plotOptions: {
				series: {
					stacking: 'normal',
					dataLabels: {
						enabled: true
					}
				}
			},
			series: [
				<c:forEach items="${allSessionsForPlan}" var="session" varStatus="i">
					{
						name: '<c:out value="${session.logDate}" />',
						data: [distances[<c:out value="${i.index}" />]],
					},
				</c:forEach>
			]
		});

	</script>


	<script type="text/javascript">
		Highcharts.chart('container-chart-muscles', {
			chart: {
				type: 'column'
			},
			title: {
				text: 'Workouts by muscle group',
				align: 'left'
			},
			subtitle: {
				text: '<c:out value="${muscleGroupsInPlan.size()}"/> muscle category / <c:out value="${countOfAllMuscleWorkouts}"/> muscles*<br>* some workouts may involve more than one muscle',
				align: 'left'
			},
			xAxis: {
				categories: ['Muscles'],
			},
			yAxis: {
				min: 0,
				title: {
					text: 'Workouts'
				},
				tickInterval: 1
			},
			plotOptions: {
				column: {
					pointPadding: 0,
					borderWidth: 0
				},
				series: {
					dataLabels: {
						enabled: true
					}
				}
			},
			series: [
				<c:forEach items="${muscleGroupsInPlan}" var="mc">
					{
						name: '<c:out value="${mc[0]}" />',
						data: [<c:out value="${mc[1]}" />]
					},
				</c:forEach>
			]
		});


		Highcharts.chart('container-chart-muscles-percentage', {
			chart: {
				type: 'pie'
			},
			title: {
				text: 'Percentage of workouts by muscle group'
			},
			tooltip: {
				valueSuffix: '%'
			},
			plotOptions: {
				series: {
					allowPointSelect: true,
					cursor: 'pointer',
					dataLabels: {
						enabled: true,
					}
				}
			},
			series: [
				{
					name: 'Percentage',
					colorByPoint: true,
					data: [
						<c:forEach items="${muscleGroupsInPlan}" var="mc">
							{
								name: '<c:out value="${mc[0]}" />',
								y: <c:out value="Math.floor((${mc[1]} * 100) / ${countOfAllMuscleWorkouts})" />
							},
						</c:forEach>
					]
				}
			]
		});

	
	</script>


	<!-- <div id="container-chart"></div> -->

	<pre id="data" style="display: none;">
		[[
		{"x": 1, "y": 1, "z": 20, "t": 1 },
		{"x": 2, "y": 1, "z": 45, "t": 1 },
		{"x": 3, "y": 1, "z": 12, "t": 1 },
		{"x": 4, "y": 1, "z": 37, "t": 1 },
		{"x": 5, "y": 1, "z": 42, "t": 1 },
		{"x": 8, "y": 1, "z": 57, "t": 1 },
		{"x": 9, "y": 1, "z": 86, "t": 1 },
		{"x": 10, "y": 1, "z": 92, "t": 1 },
		{"x": 11, "y": 1, "z": 49, "t": 1 },
		{"x": 12, "y": 1, "z": 42, "t": 1 },
		{"x": 15, "y": 1, "z": 48, "t": 1 },
		{"x": 16, "y": 1, "z": 97, "t": 1 },
		{"x": 17, "y": 1, "z": 87, "t": 1 },
		{"x": 18, "y": 1, "z": 92, "t": 1 },
		{"x": 19, "y": 1, "z": 83, "t": 1 },
		{"x": 22, "y": 1, "z": 35, "t": 1 },
		{"x": 23, "y": 1, "z": 21, "t": 1 },
		{"x": 24, "y": 1, "z": 45, "t": 1 },
		{"x": 25, "y": 1, "z": 96, "t": 1 },
		{"x": 26, "y": 1, "z": 82, "t": 1 }],[
		{"x": 1, "y": 2, "z": 73, "t": 2 },
		{"x": 2, "y": 2, "z": 92, "t": 2 },
		{"x": 3, "y": 2, "z": 41, "t": 2 },
		{"x": 4, "y": 2, "z": 33, "t": 2 },
		{"x": 5, "y": 2, "z": 63, "t": 2 },
		{"x": 8, "y": 2, "z": 20, "t": 2 },
		{"x": 9, "y": 2, "z": 27, "t": 2 },
		{"x": 10, "y": 2, "z": 25, "t": 2 },
		{"x": 11, "y": 2, "z": 12, "t": 2 },
		{"x": 12, "y": 2, "z": 75, "t": 2 },
		{"x": 15, "y": 2, "z": 84, "t": 2 },
		{"x": 16, "y": 2, "z": 71, "t": 2 },
		{"x": 17, "y": 2, "z": 94, "t": 2 },
		{"x": 18, "y": 2, "z": 79, "t": 2 },
		{"x": 19, "y": 2, "z": 53, "t": 2 },
		{"x": 22, "y": 2, "z": 94, "t": 2 },
		{"x": 23, "y": 2, "z": 63, "t": 2 },
		{"x": 24, "y": 2, "z": 67, "t": 2 },
		{"x": 25, "y": 2, "z": 86, "t": 2 },
		{"x": 26, "y": 2, "z": 27, "t": 2 }],[
		{"x": 1, "y": 3, "z": 20, "t": 3 },
		{"x": 2, "y": 3, "z": 53, "t": 3 },
		{"x": 3, "y": 3, "z": 83, "t": 3 },
		{"x": 4, "y": 3, "z": 11, "t": 3 },
		{"x": 5, "y": 3, "z": 2, "t": 3 },
		{"x": 8, "y": 3, "z": 71, "t": 3 },
		{"x": 9, "y": 3, "z": 28, "t": 3 },
		{"x": 10, "y": 3, "z": 84, "t": 3 },
		{"x": 11, "y": 3, "z": 65, "t": 3 },
		{"x": 12, "y": 3, "z": 3, "t": 3 },
		{"x": 15, "y": 3, "z": 60, "t": 3 },
		{"x": 16, "y": 3, "z": 49, "t": 3 },
		{"x": 17, "y": 3, "z": 96, "t": 3 },
		{"x": 18, "y": 3, "z": 46, "t": 3 },
		{"x": 19, "y": 3, "z": 33, "t": 3 },
		{"x": 22, "y": 3, "z": 28, "t": 3 },
		{"x": 23, "y": 3, "z": 28, "t": 3 },
		{"x": 24, "y": 3, "z": 46, "t": 3 },
		{"x": 25, "y": 3, "z": 57, "t": 3 },
		{"x": 26, "y": 3, "z": 66, "t": 3 }
		],[
		{"x": 1, "low": 0, "high": 113, "week": 1, "avg": 37, "highscore": 73, "topEarner": "Sofia" } ,
		{"x": 2, "low": 0, "high": 190, "week": 1, "avg": 63, "highscore": 92, "topEarner": "Sofia" } ,
		{"x": 3, "low": 0, "high": 136, "week": 1, "avg": 45, "highscore": 83, "topEarner": "Asmara" } ,
		{"x": 4, "low": 0, "high": 81, "week": 1, "avg": 27, "highscore": 37, "topEarner": "Ulambaator" } ,
		{"x": 5, "low": 0, "high": 107, "week": 1, "avg": 35, "highscore": 63, "topEarner": "Sofia" } ,
		{"x": 8, "low": 0, "high": 148, "week": 2, "avg": 49, "highscore": 71, "topEarner": "Asmara" } ,
		{"x": 9, "low": 0, "high": 141, "week": 2, "avg": 47, "highscore": 86, "topEarner": "Ulambaator" } ,
		{"x": 10, "low": 0, "high": 201, "week": 2, "avg": 67, "highscore": 92, "topEarner": "Ulambaator" } ,
		{"x": 11, "low": 0, "high": 126, "week": 2, "avg": 42, "highscore": 65, "topEarner": "Asmara" } ,
		{"x": 12, "low": 0, "high": 120, "week": 2, "avg": 40, "highscore": 75, "topEarner": "Sofia" } ,
		{"x": 15, "low": 0, "high": 192, "week": 3, "avg": 64, "highscore": 84, "topEarner": "Sofia" } ,
		{"x": 16, "low": 0, "high": 217, "week": 3, "avg": 72, "highscore": 97, "topEarner": "Ulambaator" } ,
		{"x": 17, "low": 0, "high": 277, "week": 3, "avg": 92, "highscore": 96, "topEarner": "Asmara" } ,
		{"x": 18, "low": 0, "high": 217, "week": 3, "avg": 72, "highscore": 92, "topEarner": "Ulambaator" } ,
		{"x": 19, "low": 0, "high": 169, "week": 3, "avg": 56, "highscore": 83, "topEarner": "Ulambaator" } ,
		{"x": 22, "low": 0, "high": 157, "week": 4, "avg": 52, "highscore": 94, "topEarner": "Sofia" } ,
		{"x": 23, "low": 0, "high": 112, "week": 4, "avg": 37, "highscore": 63, "topEarner": "Sofia" } ,
		{"x": 24, "low": 0, "high": 158, "week": 4, "avg": 52, "highscore": 67, "topEarner": "Sofia" } ,
		{"x": 25, "low": 0, "high": 239, "week": 4, "avg": 79, "highscore": 96, "topEarner": "Ulambaator" } ,
		{"x": 26, "low": 0, "high": 175, "week": 4, "avg": 58, "highscore": 82, "topEarner": "Ulambaator" }
		]]
		</pre>
	

	<script type="text/javascript">
		const 
			data = JSON.parse(document.getElementById('data').innerHTML),
			scoreData = data[3],
			colors = Highcharts.getOptions().colors.map(Highcharts.Color.parse),

			// Defining recurring values.
			monthExtremes = { min: 0, max: 26 },
			weekExtremes = { min: 1, max: 5 },
			paneOpeningAngles = { startAngle: 40.5, endAngle: 319.5 },
			noLabelProp = { labels: { enabled: false } },
			specialSeriesProps = {
				showInLegend: false,
				groupPadding: 0,
				pointPadding: 0
			},

			// A gradient background for the inner circle (aka pane.)
			toggleableGradient = {
				pattern: undefined,
				radialGradient: [1, 0.25, 0.1],
				stops: [
					[0, '#1f1836'],
					[1, '#45445d']
				]
			},

			// A function which (re)sets the inner circles background to our gradient.
			setGradient = function () {
				const chart = this.series.chart;
				chart.setMidPaneBg({
					backgroundColor: toggleableGradient,
					outerRadius: '75%'
				});
				chart.subtitle.element.style.opacity = 1;
			},

			// A function used in the creation of our second custom tooltip.
			asColFieldStr = str => (
				'<span class="col-display-fieldwrap">' +
				'<span class="symbolSize" ' +
				'style="color:{point.color};">●</span> ' + str + '</span>'
			),

			// We create our teams, 1 serie per team.
			teamNames = ['Ulambaator', 'Sofia', 'Asmara'],
			teamColors = [
				colors[9 % colors.length].tweenTo(colors[0], 0.25),
				colors[9 % colors.length].tweenTo(colors[8 % colors.length], 0.65),
				colors[9 % colors.length].tweenTo(colors[3], 0.85)
			],
			teamSeries = Array(3).fill({
				type: 'bubble',
				shadow: true,
				maxSize: '4%',
				minSize: '1%',
				clip: false,
				point: {
					events: {

						// When hovering a bubble, change the inner circles background
						// to the color of its parent series.
						mouseOver: function () {
							const chart = this.series.chart;
							chart.subtitle.element.style.opacity = 0;
							chart.setMidPaneBg({
								backgroundColor: teamColors[this.series.index],
								innerRadius: '0%'
							});
						},
						mouseOut: setGradient
					}
				},
				colorKey: 't',

				// A custom tooltip, using our own CSS
				tooltip: {
					headerFormat: (
						'<div class="team-day center">' +
						'<span class="team-header">' +
						'<b class="team-index">Day {point.x}</b></span>' +
						'<span class="team-name" style="' +
						'border: 0 outset {series.color};' +
						'border-block-end: 0 outset {series.color};">' +
						'<b>{series.name}</b></span>'
					),
					pointFormat: (
						'<span class="team-points">' +
						'<span class="team-salescount-header">Daily Sales:</span>' +
						'</br>' +
						'<span class="team-salescount">{point.z}</span>'
					),
					footerFormat: '</div>'
				}
			}).map((seriesProps, i) => ({
				...seriesProps,
				name: teamNames[i],
				data: data[i],
				color: teamColors[i],
				marker: {
					fillColor: teamColors[i],
					fillOpacity: 1,
					lineColor: '#46465C',
					lineWidth: 2
				}
			})),

			// We create a series which only purpose is to act as a label
			// indicating the week in which a given datapoint occurred.
			weekLabels = Array(4)
				.fill(0)
				.map((_value, index) => ({
					dataLabels: {
						format: 'Week {x}',
						enabled: true,
						inside: true,
						style: {
							textOutline: undefined,
							fontSize: '0.7em',
							fontWeight: '700',
							textTransform: 'uppercase',
							fontStyle: 'normal',
							letterSpacing: '0.01em'
						},
						textPath: {
							enabled: true,
							attributes: {
								startOffset: (
									index % 3 ? '75%' : (
										index % 2 ? '22%' : '28%'
									)
								),
								dx: index % 2 ? '-2%' : '0%',
								dy: index % 3 ? '2.8%' : '3.3%'
							}
						}
					},
					x: index + 1,
					y: 1.5
				}));

		Highcharts.chart('container-chart', {
			chart: {
				polar: true,
				height: '100%',
				events: {

					load: function () {
						const midPane = this.pane[1];

						// Our custom background functions are actually wrappers of the
						// function defined below. This function needs to be defined in
						// the load-event so that it is able to reference an instance
						// of Highcharts, without Highcharts being instantiated yet.
						this.setMidPaneBg = function (background) {
							midPane.update({ background: background });
						};
					},

					// We assign a function which positions our  dynamically
					// regardless of viewport or chart dimensions.
					render: function () {
						if (this.legend.group) {

							const
								{ chartWidth, chartHeight, legend  } = this,
								{ legendWidth, legendHeight } = legend;

							legend.group.translate(
								(
									(chartWidth - legendWidth) / 2
								),
								legendHeight * (chartWidth / chartHeight)
							);
						}
					}
				}
			},
			title: {
				text: 'Advanced Polar Chart'
			},
			subtitle: {
				text: 'Sales Team<br>Performance',
				useHTML: 'true',
				align: 'center',
				y: 35,
				verticalAlign: 'middle',
				style: {
					fontSize: '1em',
					color: 'white',
					textAlign: 'center'
				}
			},
			tooltip: {
				animation: false,
				backgroundColor: undefined,
				hideDelay: 0,
				useHTML: true,

				// This function positions our tooltip in the center,
				// regardless of viewport or chart dimensions.
				positioner: function (labelWidth, labelHeight) {
					const { chartWidth, chartHeight } = this.chart;
					return {
						x: (chartWidth / 2) - (labelWidth / 2),
						y: (chartHeight / 2) - (labelHeight / 2)
					};
				}
			},
			colorAxis: [{
				minColor: colors[0].brighten(0.05).get('rgba'),
				maxColor: colors[5].brighten(0.05).get('rgba'),
				showInLegend: false,
				...weekExtremes
			}, {
				minColor: colors[1].tweenTo(colors[5], 0.5),
				maxColor: colors[8 % colors.length].tweenTo(
					colors[8 % colors.length],
					0.5
				),
				showInLegend: false,
				...monthExtremes
			}],

			// Our chart is made of 3 different panes/circles
			pane: [{
				size: '80%',
				innerSize: '75%',
				...paneOpeningAngles,
				background: {
					borderColor: colors[4],
					backgroundColor: toggleableGradient,
					innerRadius: '40%'
				}
			}, {
				size: '55%',
				innerSize: '45%',
				...paneOpeningAngles,
				background: {
					borderWidth: 0,
					backgroundColor: toggleableGradient,
					outerRadius: '75%'
				}

			// ...And this the one we alter
			}, {
				size: '100%',
				innerSize: '88%',
				startAngle: 16.5,
				endAngle: 343.5,
				background: {
					borderWidth: 1,
					borderColor: colors[4],
					backgroundColor: '#46465C',
					innerRadius: '55%',
					outerRadius: '100%'
				}
			}],
			xAxis: [{
				pane: 0,
				tickInterval: 1,
				lineWidth: 0,
				gridLineWidth: 0,
				min: 1,
				max: 26,
				...noLabelProp
			}, {
				pane: 1,
				linkedTo: 0,
				gridLineWidth: 0,
				lineWidth: 0,

				// We put some plotbands on the chart to represent weekends
				// when no datapoints occur.
				plotBands: Array(3).fill(7).map(
					(weekendOffset, week) => {
						const
							from = weekendOffset * (week + 1),
							to = from - 1;
						return { from, to, color: '#BBBAC5' };
					}
				),
				...monthExtremes,
				...noLabelProp
			}, {
				pane: 2,
				tickAmount: 4,
				tickInterval: 0.5,
				gridLineWidth: 0,
				lineWidth: 0,
				...weekExtremes,
				...noLabelProp
			}],
			yAxis: [{
				pane: 0,
				gridLineWidth: 0.5,
				gridLineDashStyle: 'longdash',
				tickInterval: 1,
				title: null,
				...noLabelProp,
				min: 1,
				max: 3
			}, {
				pane: 1,
				reversed: true,
				gridLineWidth: 0,
				tickInterval: 100,
				min: 0,
				max: 400,
				title: null,
				...noLabelProp
			}, {
				pane: 2,
				tickInterval: 0.25,
				gridLineWidth: 0,
				gridLineColor: colors[1].brighten(0.05).get('rgba'),
				min: -3,
				max: 1,
				title: null,
				...noLabelProp
			}],
			legend: {
				enabled: true,
				floating: true,
				layout: 'vertical',
				verticalAlign: 'center',
				align: 'center',
				backgroundColor: '#1f1836',
				borderRadius: 14,
				borderColor: 'transparent',
				borderWidth: 0,
				lineHeight: 8,
				itemStyle: {
					color: '#FFF',
					fontSize: '0.8em'
				},
				itemHoverStyle: {
					color: '#BBBAC5',
					fontSize: '0.9em'
				},
				padding: 2,
				itemDistance: 0,
				symbolPadding: 8,
				symbolHeight: 8,
				width: '36%',
				maxHeight: '14%'
			},
			plotOptions: {
				columnrange: {
					custom: {
						textSizeClass: 'small-size'
					}
				}
			},
			responsive: {
				rules: [
					{
						condition: {
							minWidth: 400
						},
						chartOptions: {
							legend: {
								lineHeight: 16,
								padding: 3,
								borderWidth: 0.5,
								itemStyle: {
									fontSize: '0.9em'
								},
								itemHoverStyle: {
									fontSize: '1.1em'
								},
								width: '34%'
							},
							subtitle: {
								style: {
									fontSize: '1em'
								}
							}
						}
					}, {
						condition: {
							minWidth: 520
						},
						chartOptions: {
							legend: {
								borderWidth: 1,
								itemStyle: {
									fontSize: '1.1em'
								},
								itemHoverStyle: {
									fontSize: '1.25em'
								},
								width: '30%'
							},
							subtitle: {
								style: {
									fontSize: '1.4em'
								}
							}
						}
					}, {
						condition: {
							minWidth: 600
						},
						chartOptions: {
							legend: {
								borderWidth: 1.5,
								itemStyle: {
									fontSize: '1.2em'
								},
								itemHoverStyle: {
									fontSize: '1.4em'
								},
								width: '26%'
							},
							plotOptions: {
								columnrange: {
									custom: {
										textSizeClass: 'mid-size'
									}
								}
							},
							subtitle: {
								style: {
									fontSize: '1.8em'
								}
							}
						}
					}, {
						condition: {
							minWidth: 680
						},
						chartOptions: {
							legend: {
								borderWidth: 2,
								symbolPadding: 12,
								symbolHeight: 12,
								itemStyle: {
									fontSize: '1.4em'
								},
								itemHoverStyle: {
									fontSize: '1.6em'
								}
							},
							plotOptions: {
								columnrange: {
									custom: {
										textSizeClass: 'large-size'
									}
								}
							},
							subtitle: {
								style: {
									fontSize: '2em'
								}
							}
						}
					}
				]
			},
			series: [
				...teamSeries, {
					...specialSeriesProps,
					animation: false,
					name: 'Month',
					type: 'column',
					data: weekLabels,
					xAxis: 2,
					yAxis: 2,
					borderRadius: 50,
					colorKey: 'x',
					pointWidth: 1.2,
					pointPlacement: 'between',
					enableMouseTracking: false
				}, {
					showInLegend: false,
					...specialSeriesProps,
					animation: false,
					name: 'Total',
					type: 'columnrange',
					data: scoreData,
					xAxis: 1,
					yAxis: 1,
					shadow: false,
					colorAxis: 1,
					colorKey: 'x',
					borderColor: '#46465C',
					borderWidth: 2,
					pointPlacement: 'on',
					pointStart: 1,
					point: {
						events: {

							// Here we change our circle once again but this time
							// it is when the innermost column series is hovered.
							mouseOver: function () {
								const chart = this.series.chart;
								chart.setMidPaneBg({
									backgroundColor: toggleableGradient,
									outerRadius: '75%'
								});
								chart.subtitle.element.style.opacity = 0;
							},

							// We reuse our originally defined "setGradient" function
							// to reset the circles background when the mouse leaves
							// a hovered column.
							mouseOut: setGradient
						}
					},

					// ...And here is the custom tooltip content for the columns
					tooltip: {
						headerFormat: (
							'<span class="team-day center">' +
							'<span class="{series.options.custom.textSizeClass}">' +
							'<b style="color:{point.color};">Day {point.x}</b></span>'
						),
						hideDelay: 0,
						pointFormat: (
							asColFieldStr(
								'<b>Sales: </b><span>{point.high}</span>'
							) +
							asColFieldStr(
								'<b>Average: </b><span>{point.avg}</span>'
							) +
							asColFieldStr(
								'<b>Highscore: </b><span>{point.highscore}</span>'
							) +
							asColFieldStr(
								'<b>Top earner: </b><span>{point.topEarner}</span>'
							)
						),
						footerFormat: (
							'<i class="col-display-footer center">' +
							'Week {point.week}</i></span></span>'
						)
					}
				}
			]
		});

	</script>

<%@ include file="../common/footer.jspf" %>