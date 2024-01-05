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


	<div class="row flex-lg-row-reverse align-items-center g-5 py-5">
		<div class="col-10 col-sm-8 col-lg-6">
		  <img src="/resources/img/gym/${workout.img}" class="d-block mx-lg-auto img-fluid" alt="${workout.name}" width="400" height="400" loading="lazy">
		</div>
		<div class="col-lg-6">
		  <h1 class="display-5 fw-bold text-body-emphasis lh-1 mb-3">${workout.name}</h1>
		  <p class="lead">${workout.suggestion}</p>
		</div>
	  </div>


	<div class="row">
		<div class="col">
			<div class="container px-4 py-5">
				<h2 class="pb-2 border-bottom">Muscle Group</h2>
			
				<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4 py-5">
				  <div class="col d-flex align-items-start">
					<div>
					  <h3 class="fw-bold mb-0 fs-4 text-body-emphasis">${workout.muscleCategory}</h3>
					  <p>Paragraph of text beneath the heading to explain the heading.</p>
					</div>
				  </div>
				</div>
			</div>
		</div>

		<div class="col">
			<div class="container px-4 py-5">
				<h2 class="pb-2 border-bottom">Equipment</h2>
			
				<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4 py-5">
				  <div class="col d-flex align-items-start">
					<div>
					  <h3 class="fw-bold mb-0 fs-4 text-body-emphasis">${workout.equipment}</h3>
					  <p>Paragraph of text beneath the heading to explain the heading.</p>
					</div>
				  </div>
				</div>
			</div>
		</div>
	</div>

	<div class="container px-4 py-5" id="featured-3">
		<h2 class="pb-2 border-bottom">In Plans</h2>

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
					<p class="lead">This workout has never been added to any plan</p>
				  </div>
			</c:otherwise>
		</c:choose>
		
	</div>


<%@ include file="../common/footer.jspf" %>