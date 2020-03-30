<%@ include file="../common/header.jspf"%>


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

<div class="card">
	<div class="card-body">
		<div class="row">
			<div class="col-8">
				<h4 class="display-4">Admin Panel > todos</h4>
			</div>
			<div class="col-4 text-center">
				<a class="btn btn-primary" href="#" role="button">...</a>
			</div>
		</div>

		<p>Total number of users: ${all_todos.size()}</p>

		<table class="table table-striped table-hover">
			<thead>
				<tr>
					<th>Description</th>
					<th>Creation Date</th>
					<th>Due Date</th>
					<th>Completed?</th>
					<th>Public?</th>
					<th>Likes</th>
					<th>User</th>
					<th></th>
				</tr>
			</thead>
			<c:forEach items="${all_todos}" var="todo">
				<tr>
					<th>${todo.desc}</th>
					<th><fmt:formatDate value="${todo.creation_date}"
							pattern="yyyy/MM/dd" /></th>
					<th><fmt:formatDate value="${todo.due_date}"
							pattern="yyyy/MM/dd" /></th>
					<th>${todo.completed}</th>
					<th>${todo.publicc}</th>
					<th>${todo.like}</th>
					<th><a href="/@${todo.user.username}">${todo.user.username}</a></th>
					<th><a type="button" class="btn btn-warning"
						href="/admin/delete_todo?id=${todo.id}"><i
							class="fas fa-trash-alt"></i></a></th>
				</tr>
			</c:forEach>
			<tbody>
			</tbody>
		</table>

	</div>
</div>



<%@ include file="../common/footer.jspf"%>