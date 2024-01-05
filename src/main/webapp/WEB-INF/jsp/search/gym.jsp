<%@ include file="../common/header.jspf" %>

		<%@ include file="common/result.jspf" %>
		
		<div class="row">
			<c:forEach items="${items}" var="plan">
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
					</div>
				</div>
			</c:forEach>
		</div>

<%@ include file="../common/footer.jspf" %>