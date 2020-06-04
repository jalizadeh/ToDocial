<%@ include file="common/header.jspf"%>


<div class="row">
	<div class="col">
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

		<div class="sticky-top">
			<div class="accordion" id="accordionStatus">

				<div class="card">
					<div class="card-header" id="headingActive">
						<div class="row align-items-center">
							<div class="col px-0">
								<button class="btn btn-link" type="button" data-toggle="collapse"
								data-target="#collapseActive" aria-expanded="true"
								aria-controls="collapseActive"><spring:message code="mytodos.jumpto" /></button>
							</div>
							<div class="col-auto">
								<span class="badge badge-primary badge-pill">${todosNotCompleted.size()}</span>
								<a href="/add-todo" class="badge badge-success">
									<i class="fas fa-plus"></i> <spring:message code="mytodos.new" />
								</a>
							</div>
						</div>
					</div>

					<div id="collapseActive" class="collapse" aria-labelledby="headingActive"
						data-parent="#accordionStatus">
						<div class="card-body" style="padding: 0px;">
							<ul class="list-group list-group-flush">
								<c:forEach items="${todosNotCompleted}" var="todo">
									<li class="list-group-item">
										<a href="#${todo.id}" data-toggle="tooltip"
											title="${todo.name}"> <i
											class="fas fa-clipboard text-warning"></i>
											${fn:substring(todo.name, 0, 20)}...
										</a>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>


				<div class="card">
					<div class="card-header" id="headingCompleted">
						<div class="row align-items-center">
							<div class="col px-0">
								<button class="btn btn-link" type="button" data-toggle="collapse"
									data-target="#collapseCompleted" aria-expanded="true"
									aria-controls="collapseCompleted"><spring:message code="mytodos.completed" /></button>
							</div>
							<div class="col-auto">
								<span class="badge badge-primary badge-pill">${todosCompleted.size()}</span>
							</div>
						</div>
					</div>

					<div id="collapseCompleted" class="collapse" aria-labelledby="headingCompleted"
						data-parent="#accordionStatus">
						<div class="card-body" style="padding: 0px;">
							<ul class="list-group list-group-flush">
								<c:forEach items="${todosCompleted}" var="todo">
									<li class="list-group-item">
										<a href="/completed-todo?id=${todo.id}" data-toggle="tooltip"
											title="${todo.name}" > <i
											class="fas fa-clipboard-check text-success"></i>
											${fn:substring(todo.name, 0, 20)}...
										</a>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>

				
				
				<div class="card">
					<div class="card-header" id="headingCanceled">
						<div class="row align-items-center">
							<div class="col px-0">
								<button class="btn btn-link" type="button" data-toggle="collapse"
									data-target="#collapseCanceled" aria-expanded="false"
									aria-controls="collapseCanceled"><spring:message code="mytodos.canceled" /></button>
							</div>
							<div class="col-auto">
								<span class="badge badge-primary badge-pill">${todosCanceled.size()}</span>
							</div>
						</div>
					</div>

					<div id="collapseCanceled" class="collapse" aria-labelledby="headingCanceled"
						data-parent="#accordionStatus">
						<div class="card-body" style="padding: 0px;">
							<ul class="list-group list-group-flush">
								<c:forEach items="${todosCanceled}" var="todo">
									<li class="list-group-item">
										<a href="/update-todo?id=${todo.id}" data-toggle="tooltip"
											title="${todo.name}" > <i
											class="fas fa-clipboard text-danger"></i>
											${fn:substring(todo.name, 0, 20)}...
										</a>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>

			</div>

		</div>
	</div>

	<!-- todosNotCompleted -->
	<!-- ///// -->
	<jsp:useBean id="today" class="java.util.Date" />
	<div class="col-6">
		<c:forEach items="${todosNotCompleted}" var="todo">
			<div class="card mb-3">
				<div class="accordion" id="accordionExample">
					<div class="card">
						<div class="card-header" id="heading${todo.id}">
							<h3 class="mb-0">
								<a id="${todo.id}"> <i class="fas fa-clipboard text-warning"></i>
								</a>
								<button class="btn btn-link collapsed" type="button"
									data-toggle="collapse" data-target="#collapse${todo.id}"
									aria-expanded="false" aria-controls="collapse${todo.id}">
									<h5 class="card-title">${todo.name}</h5>
								</button>
							</h3>
						</div>

						<div id="collapse${todo.id}" class="collapse"
							aria-labelledby="heading${todo.id}"
							data-parent="#accordionExample">
							<div class="card-body">
								<div class="row">
									<div class="col-4 text-secondary">
										<i class="fas fa-play"></i>
										<c:choose>
											<c:when test="${settings.dateStructure == 'pattern'}">
												<fmt:formatDate value="${todo.creation_date}"
													pattern="yyyy/MM/dd" />
											</c:when>
											<c:when test="${settings.dateStructure == 'short'}">
												<fmt:formatDate type="date" dateStyle="short"
													value="${todo.creation_date}" />
											</c:when>
											<c:when test="${settings.dateStructure == 'medium'}">
												<fmt:formatDate type="date" dateStyle="medium"
													value="${todo.creation_date}" />
											</c:when>
											<c:when test="${settings.dateStructure == 'long'}">
												<fmt:formatDate type="date" dateStyle="long"
													value="${todo.creation_date}" />
											</c:when>
										</c:choose>
									</div>
									<div class="col-4 text-secondary">
										<i class="fas fa-stopwatch"></i>
										<c:choose>
											<c:when test="${settings.dateStructure == 'pattern'}">
												<fmt:formatDate value="${todo.target_date}"
													pattern="yyyy/MM/dd" />
											</c:when>
											<c:when test="${settings.dateStructure == 'short'}">
												<fmt:formatDate type="date" dateStyle="short"
													value="${todo.target_date}" />
											</c:when>
											<c:when test="${settings.dateStructure == 'medium'}">
												<fmt:formatDate type="date" dateStyle="medium"
													value="${todo.target_date}" />
											</c:when>
											<c:when test="${settings.dateStructure == 'long'}">
												<fmt:formatDate type="date" dateStyle="long"
													value="${todo.target_date}" />
											</c:when>
										</c:choose>
									</div>
									<div class="col-4">
										<fmt:parseNumber var="dayspassed"
											value="${(today.time - todo.creation_date.time) / (1000*60*60*24) }"
											integerOnly="true" />
										<fmt:parseNumber var="daystotal"
											value="${((todo.target_date.time - todo.creation_date.time) + (1000*60*60*24)) / (1000*60*60*24) }"
											integerOnly="true" />
										<fmt:parseNumber var="daysleft"
											value="${(todo.target_date.time - today.time) / (1000*60*60*24) }"
											integerOnly="true" />
										<c:choose>
											<c:when test="${daysleft < 0}">
												 		Finished
												 	</c:when>
											<c:when test="${dayspassed > 0}">
												<c:out value="${daystotal} - ${dayspassed} - ${daysleft}" />
												<fmt:formatNumber var="percentage"
													value="${((today.time - todo.creation_date.time) / (todo.target_date.time - todo.creation_date.time)) * 100}"
													maxFractionDigits="0" />
												<c:if test="${percentage > 100}">
													<c:set var="percentage" value="100" />
												</c:if>
												<div class="progress">
													<div class="progress-bar bg-info" role="progressbar"
														style="width: ${percentage}%"
														aria-valuenow="${percentage}" aria-valuemin="0"
														aria-valuemax="100">${percentage}%</div>
												</div>
											</c:when>
											<c:otherwise>
														Not started
													</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>


				<div class="card-body">
					<h5 class="card-title"></h5>
					<c:forEach items="${todo.logs}" var="log">
						<p>
							<a href="/delete-todo-log?id=${log.id}"> <i
								class="fas fa-times btn-delete-todo"></i>
							</a> <span class="badge badge-secondary"> <fmt:formatDate
									value="${log.logDate}" pattern="yyyy/MM/dd" />
							</span> ${log.log}
						</p>
					</c:forEach>

					<form method="post" autocomplete="off">
						<div class="input-group">
							<input type="hidden" id="todoId" name="todoId" value="${todo.id}">
							<input type="text" class="form-control"
								aria-label="Text input with segmented dropdown button"
								name="log" id="log">
							<div class="input-group-append">
								<button type="submit" class="btn btn-outline-secondary">
									<spring:message code="mytodos.post" />
								</button>
								<button type="button"
									class="btn btn-outline-secondary dropdown-toggle dropdown-toggle-split"
									data-toggle="dropdown" aria-haspopup="true"
									aria-expanded="false">
									<span class="sr-only">Toggle Dropdown</span>
								</button>
								<div class="dropdown-menu">
									<a class="dropdown-item" href="/update-todo?id=${todo.id}"><i
										class="fas fa-edit"></i> <spring:message code="mytodos.modify" /></a>
									<a class="dropdown-item" href="/complete-todo?id=${todo.id}"><i
										class="fas fa-archive"></i> <spring:message
											code="mytodos.completeandarchive" /></a>
									<div role="separator" class="dropdown-divider"></div>
									<a class="dropdown-item text-danger"
										href="/cancel-todo?id=${todo.id}"><i
										class="fas fa-trash-alt"></i> <spring:message
											code="mytodos.delete" /></a>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</c:forEach>
	</div>

	<div class="col">
		<div class="row align-items-center">
			<div class="col">
				<strong><spring:message code="mytodos.friends" /></strong>
			</div>
			<div class="col-auto">
				<a class="badge badge-pill badge-primary text-white">${user.followings.size()}</a>
				<a href="#" class="badge badge-success"> <spring:message
						code="mytodos.explore" /></a>
			</div>

		</div>
		<hr>
		<c:forEach items="${user.followings}" var="following">
			<a href="/@${following.username}"><img
				class="img-profile rounded-circle" width="32px" height="32px"
				src="/user-avatar/${following.photo}"></a>
			<a href="/@${following.username}">${following.firstname}
				${following.lastname}</a>
			<hr>
		</c:forEach>
	</div>
</div>



<%@ include file="common/footer.jspf"%>