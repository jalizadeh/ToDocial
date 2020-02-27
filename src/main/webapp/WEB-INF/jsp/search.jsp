<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>


	<div class="container mt-5 mb-5">
		<h4 class="display-4">${result}</h4>
		<table class="table table-striped table-hover mt-3">
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
						<a type="button" class="btn btn-success" href="/todo-finished?id=${todo.id}"><i class="fas fa-check-circle"></i></a>
						<a type="button" class="btn btn-info" href="/update-todo?id=${todo.id}"><i class="fas fa-edit"></i></a>
						<a type="button" class="btn btn-warning" href="/delete-todo?id=${todo.id}"><i class="fas fa-trash-alt"></i></a>
						</th>
					</tr>
				</c:forEach>
			<tbody>
			</tbody>
		</table>
	</div>

<%@ include file="common/footer.jspf" %>