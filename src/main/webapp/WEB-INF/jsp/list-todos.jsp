<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>


	<div class="container">
	<h4 class="display-4">Hey <mark>${name}</mark>, here is the list of your todos:</h4>
	<br/>
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
					<th><a type="button" class="btn btn-warning" href="/delete-todo?id=${todo.id}">Delete</a>
					<a type="button" class="btn btn-info" href="/update-todo?id=${todo.id}">Update</a></th>
				</tr>
			</c:forEach>
		<tbody>
		</tbody>
	</table>
	<div><a class="btn btn-primary" href="/add-todo" role="button">Add a Todo</a></div>
	</div>

<%@ include file="common/footer.jspf" %>