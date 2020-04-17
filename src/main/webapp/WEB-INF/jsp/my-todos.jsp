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
			<div class="row">
				<div class="col-7">
					<p>Jump to</p>
				</div>
				<div class="col">
					<a class="badge badge-pill badge-primary text-white">${todosNotCompleted.size()}</a>
					<a href="/add-todo" class="badge badge-success"><i
						class="fas fa-plus"></i> New</a>
				</div>

			</div>
			<hr>
			<c:forEach items="${todosNotCompleted}" var="todo">
				<p>
					<i class="fas fa-clipboard text-warning"></i> <a href="#${todo.id}"
						data-toggle="tooltip" title="${todo.name}">${fn:substring(todo.name, 0, 25)}...</a>
				</p>
			</c:forEach>

			<div class="row">
				<div class="col-7">
					<p>Completed</p>
				</div>
				<div class="col">
					<a class="badge badge-pill badge-primary text-white">${todosCompleted.size()}</a>

				</div>

			</div>
			<hr>
			<c:forEach items="${todosCompleted}" var="todo">
				<p>
					<i class="fas fa-clipboard-check text-success"></i> <a
						href="/update-todo?id=${todo.id}" data-toggle="tooltip" title="${todo.name}">${fn:substring(todo.name, 0, 25)}...</a>
				</p>
			</c:forEach>
		</div>
	</div>

	<!-- todosNotCompleted -->
	<!-- ///// -->
	<jsp:useBean id="today" class="java.util.Date" />
	<div class="col-6 mx-2">
		<c:forEach items="${todosNotCompleted}" var="todo">
			<div class="card mb-3">
				<c:choose>
					<c:when test="${todo.completed == true}">
						<div class="accordion" id="accordionExample">
							<div class="card">
								<div class="card-header" id="heading${todo.id}">
									<h3 class="mb-0">
										<a id="${todo.id}"> <i
											class="fas fa-clipboard-check text-success"></i>
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
												<fmt:parseNumber var="daystotal"
													value="${(todo.target_date.time - todo.creation_date.time) / (1000*60*60*24) }"
													integerOnly="true" />
												<fmt:parseNumber var="dayspassed"
													value="${(today.time - todo.creation_date.time) / (1000*60*60*24) }"
													integerOnly="true" />
												<fmt:parseNumber var="daysleft"
													value="${(todo.target_date.time - today.time) / (1000*60*60*24) }"
													integerOnly="true" />
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
									<input type="hidden" id="todoId" name="todoId"
										value="${todo.id}"> <input type="text"
										class="form-control"
										aria-label="Text input with segmented dropdown button"
										name="log" id="log">
									<div class="input-group-append">
										<button type="submit" class="btn btn-outline-secondary">Post</button>
										<button type="button"
											class="btn btn-outline-secondary dropdown-toggle dropdown-toggle-split"
											data-toggle="dropdown" aria-haspopup="true"
											aria-expanded="false">
											<span class="sr-only">Toggle Dropdown</span>
										</button>
										<div class="dropdown-menu">
											<a class="dropdown-item" href="/todo-state?id=${todo.id}"><i
												class="fas fa-sync-alt"></i> Change State</a> <a
												class="dropdown-item" href="/update-todo?id=${todo.id}"><i
												class="fas fa-edit"></i> Modify</a> <a class="dropdown-item"
												href="#"><i class="fas fa-archive"></i> Complete &
												Archive</a>
											<div role="separator" class="dropdown-divider"></div>
											<a class="dropdown-item text-danger"
												href="/delete-todo?id=${todo.id}"><i
												class="fas fa-trash-alt"></i> Delete</a>
										</div>
									</div>
								</div>
							</form>
						</div>
					</c:when>
					<c:otherwise>
						<div class="accordion" id="accordionExample">
							<div class="card">
								<div class="card-header" id="heading${todo.id}">
									<h3 class="mb-0">
										<a id="${todo.id}"> <i
											class="fas fa-clipboard text-warning"></i>
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
												<fmt:parseNumber var="daystotal"
													value="${(todo.target_date.time - todo.creation_date.time) / (1000*60*60*24) }"
													integerOnly="true" />
												<fmt:parseNumber var="dayspassed"
													value="${(today.time - todo.creation_date.time) / (1000*60*60*24) }"
													integerOnly="true" />
												<fmt:parseNumber var="daysleft"
													value="${(todo.target_date.time - today.time) / (1000*60*60*24) }"
													integerOnly="true" />
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
									<input type="hidden" id="todoId" name="todoId"
										value="${todo.id}"> <input type="text"
										class="form-control"
										aria-label="Text input with segmented dropdown button"
										name="log" id="log">
									<div class="input-group-append">
										<button type="submit" class="btn btn-outline-secondary">Post</button>
										<button type="button"
											class="btn btn-outline-secondary dropdown-toggle dropdown-toggle-split"
											data-toggle="dropdown" aria-haspopup="true"
											aria-expanded="false">
											<span class="sr-only">Toggle Dropdown</span>
										</button>
										<div class="dropdown-menu">
											<a class="dropdown-item" href="/todo-state?id=${todo.id}"><i
												class="fas fa-sync-alt"></i> Change State</a> <a
												class="dropdown-item" href="/update-todo?id=${todo.id}"><i
												class="fas fa-edit"></i> Modify</a> <a class="dropdown-item"
												href="#"><i class="fas fa-archive"></i> Complete &
												Archive</a>
											<div role="separator" class="dropdown-divider"></div>
											<a class="dropdown-item text-danger"
												href="/delete-todo?id=${todo.id}"><i
												class="fas fa-trash-alt"></i> Delete</a>
										</div>
									</div>
								</div>
							</form>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</c:forEach>
	</div>

	<div class="col mx-2">
		<div class="row">
			<div class="col-7">
				<p>Friends</p>
			</div>
			<div class="col">
				<a class="badge badge-pill badge-primary text-white">${user.followings.size()}</a>
				<a href="#" class="badge badge-success"> Explore</a>
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