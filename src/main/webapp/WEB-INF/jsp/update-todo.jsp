<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>


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
			
			<fieldset class="form-group">
			    <label for="done">Is todo finished?</label>
			    <form:select class="form-control"  path="done">
				    <form:options items="${isDoneValues}" />
				</form:select>
			 </fieldset>
	
			<button type="submit" class="btn btn-success">Update</button>
		</form:form>
	</div>

<%@ include file="common/footer.jspf" %>