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

	<div class="row mt-2 border-bottom">
		<div class="col">
			<h3>Showing ${workouts.size()} Workouts</h3>

			<!-- Pagination -->
			<div class="pagination">
				<c:if test="${pager.indexOutOfBounds()}">
					Page is out of bounds. Go back to <a class="pageLink" href="@{${baseUrl}(page=1)}">Home</a>.
				</c:if>

				<c:if test="${pager.indexOutOfBounds() eq false}">
					<c:if test="${pager.hasPrevious()}">
						<span>
							<a class="pageLink" href="workouts?page=1">&laquo; first</a>
							<a class="pageLink" href="workouts?page=${pager.getPageIndex() - 1}"> previous</a>
						</span>
					</c:if>

					<c:if test="${pager.getTotalPages() != 1}">
						<span> Page ${pager.getPageIndex()} of ${pager.getTotalPages()} </span>
					</c:if>

					<c:if test="${pager.hasNext()}">
						<span>
							<a class="pageLink" href="workouts?page=${pager.getPageIndex() + 1}">next</a>
							<a class="pageLink" href="workouts?page=${pager.getTotalPages()}">last &raquo;</a>
						</span>
					</c:if>
				</c:if>
			</div>
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
					<c:forEach items="${workout.muscleCategory}" var="mc">
						<a class="btn btn-success btn-sm" href="/gym/workouts?filter=muscle&value=${mc}" role="button">${mc}</a>
					</c:forEach>
				  <p class="card-text">${fn:substring(workout.description, 0, 90)}...</p>
				</div>
			  </div>
		</c:forEach>
	</div>
	

<%@ include file="../common/footer.jspf" %>