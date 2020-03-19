<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>


<div class="container mt-5 mb-5">

	<div class="row">
		<div class="col">
			<div class="row border-bottom">
				<div class="col-7">
					<p>Your todos</p>
				</div>
				<div class="col">
					<a href="#" class="badge badge-pill badge-primary">${todoCount}</a>
					<a href="/add-todo" class="badge badge-success"><i class="fas fa-plus"></i> New</a>
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
		<div class="col-6 ml-2 mr-2 bg-light">Recent activities</div>
		<div class="col bg-light">...</div>
	</div>
</div>

<%@ include file="common/footer.jspf"%>