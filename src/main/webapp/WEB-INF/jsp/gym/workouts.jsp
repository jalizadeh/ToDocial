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

	<div class="row mt-2 border-bottom"">
		<div class="col">
			<h2>${workouts.size()} Workouts</h2>
		</div>

		<div class="col">
			<a class="btn btn-outline-primary btn-sm" href="/gym/workouts" role="button">All</a>
			<c:forEach items="${muscleCategories}" var="mc">
				<c:choose>
					<c:when test="${mc == value}">
						<a class="btn btn-success btn-sm" href="/gym/workouts?filter=muscle&value=${mc}" role="button">${mc}</a>
					</c:when>    
					<c:otherwise>
						<a class="btn btn-secondary btn-sm" href="/gym/workouts?filter=muscle&value=${mc}" role="button">${mc}</a>
					</c:otherwise>
				</c:choose>	
			</c:forEach>
		</div>
	</div>

	<div class="row">
		<c:forEach items="${workouts}" var="workout">
			<div class="card m-1 p-2" style="width: 18rem;" >
				<a href="/gym/workouts/${workout.id}" class="card-link">
					<img src="/photo/gym/${workout.photo}" class="d-block mx-lg-auto img-fluid" alt="${workout.name}" width="400" height="400" loading="lazy">
				</a>
				<div class="card-body">
				  	<h5 class="card-title">
						${workout.name}
					</h5>
				  <a class="btn btn-success btn-sm" href="/gym/workouts?filter=muscle&value=${workout.muscleCategory}" role="button">${workout.muscleCategory}</a>
				  <p class="card-text">${fn:substring(workout.description, 0, 90)}...</p>
				</div>
			  </div>
		</c:forEach>
	</div>

<%@ include file="../common/footer.jspf" %>