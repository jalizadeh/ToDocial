<%@ include file="common/header.jspf" %>

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
			    <form:input path="username" type="text" class="form-control" name="username" id="username" required="required"/>
			    <form:errors path="username" cssClass="text-warning"/>
			</div>
			<div class="form-group">
			    <label for="password">Password</label>
			    <form:input path="password" type="password" class="form-control" name="password" id="password" required="required"/>
			    <form:errors path="password" cssClass="text-warning"/>
			</div>
			<div class="form-group form-check">
			    <input type="checkbox" class="form-check-input"  id="remember" name="remember"/>
			    <label class="form-check-label" for="remember">Remember me</label>
			</div>
			
			<c:if test="${settings.anyoneCanRegister == true}">
				<div class="form-group">
					<a href="/forgot-password">Forgot my password</a> / <a href="/signup">Register now</a>
				</div>
			</c:if>
			
			<button type="submit" class="btn btn-primary">Log in</button>
		</form:form>

<%@ include file="common/footer.jspf" %>