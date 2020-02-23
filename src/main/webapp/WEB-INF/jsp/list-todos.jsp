<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Todo Lists</title>

<link href="webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
	<div class="container">
	<h4 class="display-4">Hey <mark>${name}</mark>, here is the list of your todos:</h4>
	<br/>
	<table class="table table-striped table-hover">
		<thead>
			<tr>
				<th>#</th>
				<th>Description</th>
				<th>Date</th>
				<th>Finished?</th>
			</tr>
		</thead>
			<c:forEach items="${todos}" var="todo">
				<tr>
					<th>${todo.id}</th>
					<th>${todo.desc}</th>
					<th>${todo.targetDate}</th>
					<th>${todo.done}</th>
				</tr>
			</c:forEach>
		<tbody>
		</tbody>
	</table>
	<div><a class="btn btn-primary" href="/add-todo" role="button">Add a Todo</a></div>
	</div>
	
	<script src="webjars/jquery/3.4.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>