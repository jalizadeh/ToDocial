<%@ include file="../common/header.jspf" %>

<c:if test="${error != null}">
  <div class="alert alert-danger" role="alert">${error}</div>
</c:if>

<c:if test="${flash != null}">
  <div
    class="alert alert-${flash.status} alert-dismissible fade show"
    role="alert"
  >
    ${flash.message}
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
      <span aria-hidden="true">&times;</span>
    </button>
  </div>
</c:if>


<%@ include file="common/filter.jspf" %>


<div class="row mt-2">
	<div class="col border-bottom">
	  <h2>Found Plans</h2>
	</div>
  </div>
  
  <div class="row">
	<c:forEach items="${plans}" var="plan">
	  <div class="card p-2">
		<div class="card-body">
		  <h5 class="card-title">${plan.title}</h5>
		  <h6 class="card-subtitle mb-2 text-muted">
			${plan.gymPlanIntroduction.trainingLevel} [${plan.numberOfWeeks}W /
			${plan.numberOfDays}D]
		  </h6>
		  <p class="card-text">
			${fn:substring(plan.gymPlanIntroduction.moreInfo, 0, 45)}...
		  </p>
		  <a href="/gym/plan/${plan.id}" class="card-link">View details</a>
		  <a href="/gym/plan/${plan.id}/delete" class="card-link">Delete plan</a>
		</div>
	  </div>
	</c:forEach>
  </div>

<%@ include file="../common/footer.jspf" %>
