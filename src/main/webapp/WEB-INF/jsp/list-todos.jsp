<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>


<div class="container mt-5 mb-5">
	<div class="row">
		<div class="col">
			<div class="sticky-top">
				<div class="row border-bottom">
					<div class="col-7">
						<p>Your todos</p>
					</div>
					<div class="col">
						<a href="#" class="badge badge-pill badge-primary">${todoCount}</a>
						<a href="/add-todo" class="badge badge-success"><i
							class="fas fa-plus"></i> New</a>
					</div>
				</div>

				<c:forEach items="${todos}" var="todo">
					<c:choose>
						<c:when test="${todo.done == true}">
							<p>
								<i class="fas fa-clipboard-check text-success"></i> <a
									href="/update-todo?id=${todo.id}">${todo.desc}</a>
							</p>

						</c:when>
						<c:otherwise>
							<p>
								<i class="fas fa-clipboard text-warning"></i> <a
									href="/update-todo?id=${todo.id}">${todo.desc}</a>
							</p>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
		</div>
		<div class="col-6 ml-2 mr-2">
			<c:forEach items="${todos}" var="todo">
				<div class="card mb-3">
					<c:choose>
						<c:when test="${todo.done == true}">
							<div class="card-header">
								<div class="row">
									<div class="col-8">
										<h5 class="card-title">
											<i class="fas fa-clipboard text-success"></i> <a
												href="/update-todo?id=${todo.id}">${todo.desc}</a>
										</h5>
									</div>
									<div class="col">
										<p>
											<i class="fas fa-calendar-alt"></i>
											<fmt:formatDate value="${todo.targetDate}"
												pattern="dd/MM/yyyy" />
										</p>
									</div>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="card-header">
								<div class="row">
									<div class="col-8">
										<h5 class="card-title">
											<i class="fas fa-clipboard text-warning"></i> <a
												href="/update-todo?id=${todo.id}">${todo.desc}</a>
										</h5>
									</div>
									<div class="col">
										<p>
											<i class="fas fa-calendar-alt"></i>
											<fmt:formatDate value="${todo.targetDate}"
												pattern="dd/MM/yyyy" />
										</p>
									</div>
								</div>
							</div>
						</c:otherwise>
					</c:choose>

					<div class="card-body">
						<h5 class="card-title"></h5>
						<p class="card-text"></p>

						<div class="input-group">
							<input type="text" class="form-control"
								aria-label="Text input with segmented dropdown button">
							<div class="input-group-append">
								<button type="button" class="btn btn-outline-secondary">Post</button>
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
										href="#"><i class="fas fa-archive"></i> Complete & Archive</a>
									<div role="separator" class="dropdown-divider"></div>
									<a class="dropdown-item text-danger"
										href="/delete-todo?id=${todo.id}"><i
										class="fas fa-trash-alt"></i> Delete</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>

		<div class="col ml-2 mr-2 bg-light">...</div>
	</div>
</div>

<%@ include file="common/footer.jspf"%>