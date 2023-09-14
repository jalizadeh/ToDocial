<%@ include file="../common/header.jspf" %>

	<div class="row mt-2">
		<div class="col border-bottom">
			<h2>${plan.title} [${plan.numberOfWeeks}W / ${plan.numberOfDays}D]</h2>
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
						
						
						<!-- <c:forEach var = "j" begin = "1" end = "${plan.numberOfDays}">
							<p class="card-text">
								<span class="badge rounded-pill bg-secondary">${j}</span>
							</p>
						</c:forEach> -->

						<c:forEach items="${days}" var="day">
							<p class="card-text">
								<span class="badge rounded-pill bg-secondary">${day.dayNumber}</span> <a href="${plan.id}/week/${i}/day/${day.id}">${day.focus}</a>
							</p>
						</c:forEach>
						<!-- <a href="/gym/plan/${plan.id}/week/${i}" class="card-link">View details</a> -->
					</div>
				</div>
			</div>
		</c:forEach>
	</div>



<%@ include file="../common/footer.jspf" %>