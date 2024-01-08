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



<%@ include file="../common/footer.jspf" %>