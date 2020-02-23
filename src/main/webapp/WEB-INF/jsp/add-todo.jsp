<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
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
		<form method="post">
		  <div class="form-row align-items-center">
		  <div class="col-auto">
		      <p>Description</p>
		    </div>
		    
		    <div class="col-auto">
		      <input type="text" class="form-control mb-2" name="desc" id="inlineFormInput">
		    </div>
		
			<div class="col-auto">
		      <select id="inputState" class="form-control">
		        <option selected>Choose...</option>
		        <option>Completed</option>
		        <option>Not Completed</option>
		      </select>
		    </div>
		    
		    <div class="col-auto">
		      <button type="submit" class="btn btn-primary mb-2">Submit</button>
		    </div>
		  </div>
		</form>
	</div>

	<script src="webjars/jquery/3.4.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>