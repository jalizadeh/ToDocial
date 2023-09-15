<%@ include file="../common/header.jspf" %>

	<div class="row">
		<div class="col-8">
			<div class="row mt-2">
				<div class="col border-bottom">
					<h2>${plan.title} <span class="badge rounded-pill bg-light text-dark">${plan.numberOfWeeks} weeks</span> <span class="badge rounded-pill bg-light text-dark">${plan.numberOfDays} days</span></h2>
				</div>
			</div>

			<div class="row">
				<c:forEach var = "i" begin = "1" end = "${plan.numberOfWeeks}">
					<div class="col-4 p-2">
						<div class="card p-2" >
							<div class="card-body">
								<h5 class="card-title">Week #${i}</h5>
								<h5 class="card-title">
									<div class="progress">
										<div class="progress-bar" role="progressbar" style="width: 25%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">25%</div>
									</div>	
								</h5>
								
								<c:forEach items="${days}" var="day">
									<p class="card-text">
										<span class="badge rounded-pill bg-secondary">${day.dayNumber}</span> <a href="${plan.id}/week/${i}/day/${day.id}">${day.focus}</a>
									</p>
								</c:forEach>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="col-4">
			<div class="row mt-2">
				<div class="col border-bottom">
					<h2>Summary</h2>
				</div>
			</div>

			<div class="row">
				<p>${plan.gymPlanIntroduction.moreInfo}</p>
				</br>
				<h4>Main Goal: ${plan.gymPlanIntroduction.mainGoal}</h4>
				<h4>Workout Type: ${plan.gymPlanIntroduction.mainGoal}</h4>
				<h4>Training Level: ${plan.gymPlanIntroduction.mainGoal}</h4>
				<h4>Program Duration: ${plan.numberOfWeeks} weeks</h4>
				<h4>Days per Week: ${plan.numberOfDays} days</h4>
				<h4>Time per Workout: 60-70 min</h4>
				<h4>Equipment Required: ${plan.gymPlanIntroduction.mainGoal}</h4>
				</br>
				<c:forEach items="${days}" var="day">
					<div>
						<h5>Day ${day.dayNumber}: ${day.focus}</h5>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>



<%@ include file="../common/footer.jspf" %>