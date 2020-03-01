<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>


	<div class="container mt-5 mb-5">
		
		<table class="table table-striped table-hover">
			<thead>
				<tr>
					<th>Description</th>
					<th>Date</th>
					<th>Finished?</th>
					<th></th>
				</tr>
			</thead>
				<c:forEach items="${todos}" var="todo">
					<tr>
						<th>${todo.desc}</th>
						<th><fmt:formatDate value="${todo.targetDate}" pattern="dd/MM/yyyy"/></th>
						<th>${todo.done}</th>
						<th>
						<a type="button" class="btn btn-success" href="/todo-state?id=${todo.id}"><i class="fas fa-sync-alt"></i></a>
						<a type="button" class="btn btn-info" href="/update-todo?id=${todo.id}"><i class="fas fa-edit"></i></a>
						<a type="button" class="btn btn-warning" href="/delete-todo?id=${todo.id}"><i class="fas fa-trash-alt"></i></a>
						</th>
					</tr>
				</c:forEach>
			<tbody>
			</tbody>
		</table>
		
		<div><a class="btn btn-primary" href="/add-todo" role="button">Add a Todo</a></div>
	</div>

<%@ include file="common/footer.jspf" %>