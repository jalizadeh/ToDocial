<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

<div class="container mt-5 mb-5">
	<h4 class="display-4">Change Password:</h4>
	
	<c:if test="${errorMessages != null}">
		<div class="alert alert-danger" role="alert">
			<p>There are some errors:</p>
  			<c:forEach items="${errorMessages}" var="e" >
  				<li><c:out value = "${e}"/></li>
  			</c:forEach>
		</div>
	</c:if>
	
	<c:if test="${exception != null}">
		<div class="alert alert-danger" role="alert">
			<p>There are some errors:</p>
  			<li>${exception}</li>
		</div>
	</c:if>
	
	<form:form method="post" action="/change-password">
		<div class="form-group">
		    <label for="password">Password</label>
		    <input type="password" class="form-control" name="password" id="password" required="required"/>
		</div>
		<div class="form-group">
		    <label for="mp">Confirm Password</label>
		    <input type="password" class="form-control" name="mp" id="mp"  required="required"/>
		</div>
		
		<button type="submit" class="btn btn-primary">Submit</button>
	</form:form>
</div>


<%@ include file="common/footer.jspf" %>