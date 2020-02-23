<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
		
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Todo</title>

<link href="webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
	<div class="container">
		<h4 class="display-4">Update Todo:</h4>
		<%-- ${todo} --%>
		
		<form:form method="post" modelAttribute="todo">
			<form:hidden path="id"/>
			
			<fieldset class="form-group">
				<form:label path="desc">Description</form:label> 
				<form:input path="desc" type="text"
					class="form-control" required="required"/>
				<form:errors path="desc" cssClass="text-warning"/>
			</fieldset>
			
			<fieldset class="form-group">
				<form:label path="targetDate">Target Date</form:label> 
				<form:input path="targetDate" type="text"
					class="form-control" required="required"/>
				<form:errors path="targetDate" cssClass="text-warning"/>
			</fieldset>
	
			<button type="submit" class="btn btn-success">Update</button>
		</form:form>
	</div>

	<script src="webjars/jquery/3.4.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
	<script src="webjars/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
	
	<script type="text/javascript">
		$('#targetDate').datepicker({
			format : 'dd/mm/yyyy'
		});
	</script>
	
</body>
</html>