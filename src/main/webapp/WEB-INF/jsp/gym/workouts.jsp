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

	<div class="row mt-2">
		<div class="col border-bottom">
			<h2>${workouts.size()} Workouts</h2>
		</div>
	</div>

	<div class="row">
		<c:forEach items="${workouts}" var="workout">
			<div class="card m-1 p-2" style="width: 18rem;" >
				<a href="/gym/workouts/${workout.id}" class="card-link"><img src="/resources/img/gym/${workout.img}" class="card-img-top" alt="${workout.name}"></a>
				<div class="card-body">
				  	<h5 class="card-title">
						${workout.name}
					</h5>
				  <a class="btn btn-success btn-sm" href="/gym/?filter=workout&muscle=${workout.muscleCategory}" role="button">${workout.muscleCategory}</a>
				  <p class="card-text">${fn:substring(workout.suggestion, 0, 90)}...</p>
				</div>
			  </div>
		</c:forEach>
	</div>

<%@ include file="../common/footer.jspf" %>