<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>


	<div class="container mt-5 mb-5">
		<h4 class="display-4">Add a new Todo:</h4>
		<c:if test="${error != null}">
			<div class="alert alert-danger" role="alert">
  				${error}
			</div>
		</c:if>
		
		
		<form:form method="post" modelAttribute="todo">

			
			<fieldset class="form-group">
				<form:label path="desc">Description</form:label> 
				<form:input path="desc" type="text" class="form-control" required="required"/>
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
	
			<button type="submit" class="btn btn-success">Add</button>
		</form:form>
	</div>

	<script type="text/javascript">
		$('#targetDate').datepicker({
			format : 'dd/mm/yyyy'
		});
	</script>
<%@ include file="common/footer.jspf" %>