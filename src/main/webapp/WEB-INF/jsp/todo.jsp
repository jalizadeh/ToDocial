<%@ include file="common/header.jspf"%>

<h4 class="display-4">${PageTitle}</h4>

<c:if test="${error != null}">
	<div class="alert alert-danger" role="alert">${error}</div>
</c:if>


<form:form method="post" modelAttribute="todo">
	<fieldset class="form-group">
		<form:label path="name">Name</form:label>
		<form:input path="name" type="text" class="form-control"
			required="required" />
		<form:errors path="name" cssClass="text-warning" />
	</fieldset>

	<fieldset class="form-group">
		<label for="description">Description</label>
		<form:textarea path="description" class="form-control"
			id="description" rows="3" required="required"></form:textarea>
		<form:errors path="description" cssClass="text-warning" />
	</fieldset>

	<fieldset class="form-group">
		<label for="reason">The reasons why this todo is/should be
			added</label>
		<form:textarea path="reason" class="form-control" id="reason" rows="3"
			required="required"></form:textarea>
		<form:errors path="reason" cssClass="text-warning" />
	</fieldset>


	<fieldset class="form-group">
		<label for="ttype">Type</label>
		<form:select class="form-control" path="ttype" items="${allType}" id="ttype" />
	</fieldset>


	<fieldset class="form-group">
		<label for="priority">Priority</label>
		<form:select class="form-control" path="priority" items="${allPriority}" id="priority" />
	</fieldset>

	<fieldset class="form-group">
		<form:label path="creation_date">Ceration Date</form:label>
		<form:input path="creation_date" type="text" class="form-control"
			required="required" />
		<form:errors path="creation_date" cssClass="text-warning" />
	</fieldset>

	<fieldset class="form-group">
		<form:label path="target_date">Target Date</form:label>
		<form:input path="target_date" type="text" class="form-control date-picker" required="required" />
		<form:errors path="target_date" cssClass="text-warning" />
	</fieldset>


	<fieldset class="form-group">
		<div class="custom-control custom-checkbox my-1 mr-sm-2">
			<form:checkbox path="publicc" value="${todo.publicc}"
				class="custom-control-input" id="ispublic" />
			<label class="custom-control-label" for="ispublic">Set as
				public (other users can see your Todo's progress)</label>
		</div>
	</fieldset>

	<fieldset class="form-group">
		<div class="custom-control custom-checkbox my-1 mr-sm-2">
			<form:checkbox path="completed" value="${todo.completed}"
				class="custom-control-input" id="iscompleted" />
			<label class="custom-control-label" for="iscompleted">Todo is
				completed</label>
		</div>
	</fieldset>

	<fieldset class="form-group">
		<form:label path="completion_date">Completion Date</form:label>
		<form:input path="completion_date" type="text" class="form-control date-picker" />
		<form:errors path="completion_date" cssClass="text-warning" />
	</fieldset>

	<fieldset class="form-group">
		<label for="completion_note">Closing notes</label>
		<form:textarea path="completion_note" class="form-control"
			id="completion_note" rows="3" ></form:textarea>
		<form:errors path="completion_note" cssClass="text-warning" />
	</fieldset>


	<button type="submit" class="btn btn-success">Submit</button>

</form:form>



<script type="text/javascript">
	$('.date-picker').datepicker({
		format : 'dd/mm/yyyy'
	});
</script>

<%@ include file="common/footer.jspf"%>