<%@ include file="common/header.jspf" %>

	<div class="row">
		<div class="col">
			<div class="row border-bottom">
				<div class="col-7">
					<p>Todos</p>
				</div>
				<div class="col">
					<a href="#" class="badge badge-pill badge-primary">${todoCount}</a>
					<a href="/add-todo" class="badge badge-success"><i class="fas fa-plus"></i> New</a>
				</div>
			</div>
		</div>

		<div class="col-6 ml-2 mr-2">
			<div class="row border-bottom">
				<div class="col-7">
					<p>Recent activities</p>
				</div>
			</div>

			<c:forEach items="${todos}" var="todo">
				<c:choose>
					<c:when test="${todo.completed == true}">
						<p>
							<i class="fas fa-clipboard-check text-success"></i> <a
								href="/update-todo?id=${todo.id}">${todo.name}</a> by @${todo.user.username}
						</p>
					</c:when>
					<c:otherwise>
						<p>
							<i class="fas fa-clipboard text-warning"></i> <a
								href="/update-todo?id=${todo.id}">${todo.name}</a> by @${todo.user.username}
						</p>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>


		<div class="col bg-light">...</div>
	</div>

<%@ include file="common/footer.jspf" %>