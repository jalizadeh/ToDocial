<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>


	<div class="container">
		<h4 class="display-4">Add a new Todo:</h4>
		<c:if test="${error != null}">
			<div class="alert alert-danger" role="alert">
  				${error}
			</div>
		</c:if>
		
		
		<form:form method="post" modelAttribute="todo">

			
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

<%@ include file="common/footer.jspf" %>