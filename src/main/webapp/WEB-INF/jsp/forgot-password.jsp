<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

	<div class="container mt-5 mb-5">
		<c:if test="${flash != null}">
			<div class="alert alert-${flash.status}" role="alert">
  				${flash.message}
			</div>
		</c:if>

		<form:form method="post" action="/forgot-password">
			<div class="form-group">
			    <label for="email">Email</label>
			    <input path="email" type="text" class="form-control" name="email" id="email" required="required"/>
			</div>
			<button type="submit" class="btn btn-primary">Submit</button>
		</form:form>
	</div>

<%@ include file="common/footer.jspf" %>