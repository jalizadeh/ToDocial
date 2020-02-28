<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

	<div class="container">
		<c:if test="${flash != null}">
			<div class="alert alert-danger" role="alert">
  				${flash}
			</div>
		</c:if>
		
		<form:form method="post" action="/login" modelAttribute="user">
			<div class="form-group">
			    <label for="username">Username</label>
			    <form:input path="username" type="text" class="form-control" name="username" id="username" aria-describedby="usernameHelp"/>
			    <small id="usernameHelp" class="form-text text-muted">We'll never share your username with anyone else.</small>
			</div>
			<div class="form-group">
			    <label for="exampleInputPassword1">Password</label>
			    <form:input path="password" type="password" class="form-control" name="password" id="exampleInputPassword1" />
			</div>
			<div class="form-group form-check">
			    <input type="checkbox" class="form-check-input"  id="exampleCheck1" />
			    <label class="form-check-label" for="exampleCheck1">Remember me / </label>
			    <a href="/signup">Register now</a>
			</div>
			<button type="submit" class="btn btn-primary">Submit</button>
		</form:form>
	</div>

<%@ include file="common/footer.jspf" %>