<%@ include file="common/header.jspf"%>

	<h4 class="display-4">${PageTitle}</h4>

	<c:if test="${error != null}">
		<div class="alert alert-danger" role="alert">${error}</div>
	</c:if>


	<form:form method="post" modelAttribute="todo">
		<fieldset class="form-group">
			<form:label path="desc">Description</form:label>
			<form:input path="desc" type="text" class="form-control"
				required="required" />
			<form:errors path="desc" cssClass="text-warning" />
		</fieldset>

		<fieldset class="form-group">
			<form:label path="due_date">Target Date</form:label>
			<form:input path="due_date" type="text" class="form-control"
				required="required" />
			<form:errors path="due_date" cssClass="text-warning" />
		</fieldset>

		<fieldset class="form-group">
			<div class="custom-control custom-checkbox my-1 mr-sm-2">
				<form:checkbox path="completed" value="${todo.completed}"
					class="custom-control-input" id="iscompleted" />
				<label class="custom-control-label" for="iscompleted">Todo
					is completed</label>
			</div>
		</fieldset>

		<fieldset class="form-group">
			<div class="custom-control custom-checkbox my-1 mr-sm-2">
				<form:checkbox path="publicc" value="${todo.publicc}"
					class="custom-control-input" id="ispublic" />
				<label class="custom-control-label" for="ispublic">Set as
					public (other users can see your Todo's progress)</label>
			</div>
		</fieldset>

		<button type="submit" class="btn btn-success">Submit</button>
	</form:form>


<script type="text/javascript">
	$('#due_date').datepicker({
		format : 'dd/mm/yyyy'
	});
</script>

<%@ include file="common/footer.jspf"%>