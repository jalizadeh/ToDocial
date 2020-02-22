<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Todo Lists</title>

<style>
table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

td, th {
  border: 1px solid #dddddd;
  text-align: left;
  padding: 8px;
}

tr:nth-child(even) {
  background-color: #dddddd;
}
</style>

</head>
<body>
Hey ${name}, here is the list of your todos:
<br>
<table>
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
<br>
<a href="/add-todo">Add a Todo</a>
</body>
</html>