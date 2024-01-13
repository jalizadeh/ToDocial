<%@ include file="../common/header.jspf" %>

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

	<%@ include file="common/filter.jspf" %>

	<div class="row mt-2">
		<div class="col border-bottom">
			<h2><img src="/resources/img/status_active.gif" width="50px"> Your Active Plans <a href="gym/plan/new?step=1" class="badge badge-success"><i class="fas fa-plus"></i> New</a></h2>
		</div>
	</div>

	<div class="row">
		<c:forEach items="${activePlans}" var="plan">
			<div class="card p-2" >
				<div class="card-body">
				  	<h5 class="card-title">
						${plan.title}
					</h5>
					<h6 class="card-subtitle mb-2 text-muted">${plan.gymPlanIntroduction.trainingLevel} [${plan.numberOfWeeks}W / ${plan.numberOfDays}D]</h6>
					<p class="card-text">${fn:substring(plan.gymPlanIntroduction.moreInfo, 0, 45)}...</p>
					
					<h5 class="card-title">
						<div class="progress">
							<div class="progress-bar" role="progressbar" style="width: ${plan.progress}%;" aria-valuenow="${plan.progress}" aria-valuemin="0" aria-valuemax="100">${plan.progress}%</div>
						</div>	
					</h5>
					<a href="/gym/plan/${plan.id}" class="card-link">View details</a>
					<a href="/gym/plan?edit=${plan.id}" class="card-link">Edit plan</a>
				</div>
			  </div>
		</c:forEach>
	</div>

	<div class="row mt-2">
		<div class="col border-bottom">
			<h2><img src="/resources/img/status_completed.gif" width="50px"> Completed Plans</h2>
		</div>
	</div>

	<div class="row">
		<c:forEach items="${completedPlans}" var="plan">
			<div class="card p-2" >
				<div class="card-body">
				  	<h5 class="card-title">
						${plan.title}
					</h5>
				  <h6 class="card-subtitle mb-2 text-muted">${plan.gymPlanIntroduction.trainingLevel} [${plan.numberOfWeeks}W / ${plan.numberOfDays}D]</h6>
				  <p class="card-text">${fn:substring(plan.gymPlanIntroduction.moreInfo, 0, 45)}...</p>
				  <a href="/gym/plan/${plan.id}" class="card-link">View details</a>
				  <a href="/gym/plan/${plan.id}/delete" class="card-link">Delete plan</a>
				</div>
			  </div>
		</c:forEach>
	</div>

	<div class="row mt-2">
		<div class="col border-bottom">
			<h2>All Plans</h2>
		</div>
	</div>

	<div class="row">
		<c:forEach items="${allPlans}" var="plan">
			<div class="card m-1 p-2" >
				<div class="card-body">
				  	<h5 class="card-title">
						<c:choose>
							<c:when test="${plan.active == true}">
								<img src="/resources/img/status_active.gif" width="50px">
							</c:when>    
							<c:otherwise>
								<span class="badge rounded-pill bg-secondary">&nbsp;</span> 
							</c:otherwise>
						</c:choose>
						${plan.title}
					</h5>
				  <h6 class="card-subtitle mb-2 text-muted">${plan.gymPlanIntroduction.trainingLevel} [${plan.numberOfWeeks}W / ${plan.numberOfDays}D]</h6>
				  <p class="card-text">${fn:substring(plan.gymPlanIntroduction.moreInfo, 0, 45)}...</p>
				  <a href="/gym/plan/${plan.id}" class="card-link">View details</a>
				  <a href="/gym/plan/${plan.id}/delete" class="card-link">Delete plan</a>
				</div>
			  </div>
		</c:forEach>
	</div>

	<div class="row border-top">
		<div class="col-3">
			<p>Blood</p>
			<p>Stretching</p>
			<p>Vitamines & Minerals</p>
			<p>FAQ</p>
			<p>Mr. Olympia</p>
		</div>

		<div class="col-3">
			<p>Food Diary</p>
			<p>Yoga</p>
			<p>Best Foods</p>
			<p>Selection</p>
			<p>Quotes</p>
		</div>

		<div class="col-3">
			<p>Diet & Recipes</p>
			<p>Challenges</p>
			<p>Supplements Guide</p>
			<p>Equipments</p>
			<p>Terms</p>
		</div>

		<div class="col-3">
			<p>Notes</p>
			<p>Height Increase</p>
			<p>General Health Tips</p>
			<p>BMI</p>
			<p>BMR</p>
		</div>
	</div>

<%@ include file="../common/footer.jspf" %>