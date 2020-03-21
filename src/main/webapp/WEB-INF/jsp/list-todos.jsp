<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>


<div class="container my-5" >

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
				<div class="row border-bottom">
					<div class="col-7">
						<p>Jump to</p>
					</div>
					<div class="col">
						<a href="#" class="badge badge-pill badge-primary">${todoCount}</a>
						<a href="/add-todo" class="badge badge-success"><i
							class="fas fa-plus"></i> New</a>
					</div>
				</div>

				<c:forEach items="${todos}" var="todo">
					<c:choose>
						<c:when test="${todo.completed == true}">
							<p>
								<i class="fas fa-clipboard-check text-success"></i> <a
									href="#${todo.id}">${todo.desc}</a>
							</p>

						</c:when>
						<c:otherwise>
							<p>
								<i class="fas fa-clipboard text-warning"></i> <a
									href="#${todo.id}">${todo.desc}</a>
							</p>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
		</div>

		<!-- todos -->
		<!-- ///// -->
		<div class="col-6 mx-2">
			<c:forEach items="${todos}" var="todo">
				<div class="card mb-3">
					<c:choose>
						<c:when test="${todo.completed == true}">
							<div class="card-header">
								<div class="row">
									<div class="col-8">
										<h5 class="card-title">
											<a id="${todo.id}"><i
												class="fas fa-clipboard-check text-success"></i></a> <a
												href="/update-todo?id=${todo.id}">${todo.desc}</a>
										</h5>
									</div>
									<div class="col text-secondary">
										<i class="fas fa-calendar-alt"></i>
										<fmt:formatDate value="${todo.due_date}"
											pattern="yyyy/MM/dd" />
									</div>
								</div>
							</div>

							<div class="card-body">
								<h5 class="card-title"></h5>
								<c:forEach items="${todo.logs}" var="log">
									<p>
										<a href="/delete-todo-log?id=${log.id}"><i
											class="fas fa-times btn-delete-todo"></i></a> ${log.log}
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
							<div class="card-header">
								<div class="row">
									<div class="col-8">
										<h5 class="card-title">
											<a id="${todo.id}"> <i
												class="fas fa-clipboard text-warning"></i></a> <a
												href="/update-todo?id=${todo.id}">${todo.desc}</a>
										</h5>
									</div>
									<div class="col text-secondary">
										<i class="fas fa-calendar-alt"></i>
										<fmt:formatDate value="${todo.due_date}"
											pattern="yyyy/MM/dd" />
									</div>
								</div>
							</div>

							<div class="card-body">
								<h5 class="card-title"></h5>
								<c:forEach items="${todo.logs}" var="log">
									<p>
										<a href="/delete-todo-log?id=${log.id}"><i
											class="fas fa-times btn-delete-todo"></i></a> ${log.log}
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

		<div class="col ml-2 mr-2 bg-light">...</div>
	</div>
</div>

<%@ include file="common/footer.jspf"%>