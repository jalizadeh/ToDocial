<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login & Enjoy</title>

<link href="webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>

	<div class="container">
		<c:if test="${error != null}">
			<div class="alert alert-danger" role="alert">
  				${error}
			</div>
		</c:if>
		
		<form method="post">
			<div class="form-group">
			    <label for="username">Username</label>
			    <input type="text" class="form-control" name="name" id="username" aria-describedby="usernameHelp">
			    <small id="usernameHelp" class="form-text text-muted">We'll never share your username with anyone else.</small>
			</div>
			<div class="form-group">
			    <label for="exampleInputPassword1">Password</label>
			    <input type="password" class="form-control" name="password" id="exampleInputPassword1">
			</div>
			<div class="form-group form-check">
			    <input type="checkbox" class="form-check-input"  id="exampleCheck1">
			    <label class="form-check-label" for="exampleCheck1">Remember me / </label>
			    <a href="/signup">Register now</a>
			</div>
			<button type="submit" class="btn btn-primary">Submit</button>
		</form>
	</div>

	<script src="webjars/jquery/3.4.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>