<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

	<div class="container mt-5 mb-5">
		<c:if test="${flash != null}">
			<div class="alert alert-${flash.status}" role="alert">
  				${flash.message}
			</div>
		</c:if>
		
		<c:if test="${message != null}">
			<div class="alert alert-${message.status}" role="alert">
  				${message.message}
			</div>
		</c:if>
		
		<form:form method="post" action="/login" modelAttribute="user">
			<div class="form-group">
			    <label for="username">Username</label>
			    <form:input path="username" type="text" class="form-control" name="username" id="username"/>
			    <form:errors path="username" cssClass="text-warning"/>
			</div>
			<div class="form-group">
			    <label for="password">Password</label>
			    <form:input path="password" type="password" class="form-control" name="password" id="password" />
			    <form:errors path="password" cssClass="text-warning"/>
			</div>
			<div class="form-group form-check">
			    <input type="checkbox" class="form-check-input"  id="exampleCheck1" />
			    <label class="form-check-label" for="exampleCheck1">Remember me</label>
			</div>
			<div class="form-group">
				<a href="/forgot-password">Forgot my password</a> / <a href="/signup">Register now</a>
			</div>
			<button type="submit" class="btn btn-primary">Log in</button>
		</form:form>
	</div>

<%@ include file="common/footer.jspf" %>