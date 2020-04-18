<%@ include file="common/header.jspf"%>

<c:if test="${error != null}">
	<div class="alert alert-danger" role="alert">${error}</div>
</c:if>

<div class="row">
	<div class="col-8">
		<div class="card">
			<div class="card-body">
				<h3>${todo.name}</h3>
				<hr />

				<p class="font-weight-bold">Description</p>
				<p class="font-weight-normal">${todo.description}</p>

				<p class="font-weight-bold">The reasons why this todo was/should
					be added</p>
				<p class="font-weight-normal">${todo.reason}</p>

				<p class="font-weight-bold">Type</p>
				<p class="font-weight-normal">${todo.ttype}</p>

				<p class="font-weight-bold">Priority</p>
				<p class="font-weight-normal">${todo.priority}</p>

				<p class="font-weight-bold">Closing Note</p>
				<p class="font-weight-normal">${todo.completion_note}</p>

			</div>
		</div>
	</div>

	<c:if test="${todo.id != null}">
		<div class="col">
			<div class="row">
				<!-- likes -->
				<div class="col">
					<div class="card  bg-info text-white">
						<div class="card-body">
							<p class="h2">
								<i class="fas fa-heart"></i> ${todo.like}
							</p>
						</div>
					</div>
				</div>

				<!-- dates -->
				<div class="col">
					<div class="card  bg-info text-white">
						<div class="card-body">
							<i class="fas fa-play-circle"></i>
							<fmt:formatDate value="${todo.creation_date}"
								pattern="yyyy/MM/dd" />
							<br /> <i class="fas fa-crosshairs"></i>
							<fmt:formatDate value="${todo.target_date}" pattern="yyyy/MM/dd" />
						</div>
					</div>
				</div>
			</div>

			<button type="button" class="btn btn-success btn-lg btn-block mt-2">
				<i class="fas fa-clipboard-check"></i>
				<fmt:formatDate value="${todo.completion_date}" pattern="yyyy/MM/dd" />
			</button>

			<div class="card mt-2">
				<h5 class="card-header">Logs</h5>
				<div class="card-body">
					<c:forEach items="${todo.logs}" var="log">
						<p>
							<a href="/delete-todo-log?id=${log.id}"> <i
								class="fas fa-times btn-delete-todo"></i>
							</a> <span class="badge badge-secondary"> <fmt:formatDate
									value="${log.logDate}" pattern="yyyy/MM/dd" />
							</span> ${log.log}
						</p>
					</c:forEach>
				</div>
			</div>
		</div>
	</c:if>
</div>





<script type="text/javascript">
	$('.date-picker').datepicker({
		format : 'dd/mm/yyyy'
	});
</script>

<%@ include file="common/footer.jspf"%>