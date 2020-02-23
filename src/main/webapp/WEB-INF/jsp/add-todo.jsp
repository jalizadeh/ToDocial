<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
		
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add a new Todo</title>

<link href="webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
	<div class="container">
		<h4 class="display-4">Add a new Todo:</h4>
		<c:if test="${error != null}">
			<div class="alert alert-danger" role="alert">
  				${error}
			</div>
		</c:if>
		
		
		<form:form method="post" modelAttribute="todo">
		  <div class="form-row align-items-center">
		  <div class="col-auto">
		      <p>Description</p>
		    </div>
		    
		    <div class="col-auto">
		      <form:input type="text" class="form-control mb-2" path="desc" required="required" />
		      <form:errors path="desc" cssClass="text-warning"/>
		    </div>
		
			<div class="col-auto">
		      <select path="isDone" id="inputState" class="form-control">
		        <option selected>Choose...</option>
		        <option>Completed</option>
		        <option>Not Completed</option>
		      </select>
		    </div>
		    
		    <div class="col-auto">
		      <button type="submit" class="btn btn-primary mb-2">Add</button>
		    </div>
		  </div>
		</form:form>
	</div>

	<script src="webjars/jquery/3.4.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>