<%@ include file="common/header.jspf" %>

		<h4 class="display-4">${result}</h4>
		<table class="table table-striped table-hover mt-3">
			<thead>
				<tr>
					<th>Description</th>
					<th>Creation Date</th>
					<th>Due Date</th>
					<th>Completed?</th>
					<th></th>
				</tr>
			</thead>
				<c:forEach items="${todos}" var="todo">
					<tr>
						<th>${todo.desc}</th>
						<th><fmt:formatDate value="${todo.creation_date}" pattern="dd/MM/yyyy"/></th>
						<th><fmt:formatDate value="${todo.due_date}" pattern="dd/MM/yyyy"/></th>
						<th>${todo.completed}</th>
						<th>
						<a type="button" class="btn btn-success" href="/todo-finished?id=${todo.id}"><i class="fas fa-check-circle"></i></a>
						<a type="button" class="btn btn-info" href="/update-todo?id=${todo.id}"><i class="fas fa-edit"></i></a>
						<a type="button" class="btn btn-warning" href="/delete-todo?id=${todo.id}"><i class="fas fa-trash-alt"></i></a>
						</th>
					</tr>
				</c:forEach>
			<tbody>
			</tbody>
		</table>

<%@ include file="common/footer.jspf" %>