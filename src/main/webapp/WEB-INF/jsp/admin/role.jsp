<%@ include file="../common/header.jspf"%>

<h4 class="display-4">${PageTitle}</h4>

<c:if test="${error != null}">
	<div class="alert alert-danger" role="alert">${error}</div>
</c:if>


<form:form method="post" modelAttribute="role">
	<fieldset class="form-group">
		<div class="input-group">
			<div class="input-group-prepend">
				<div class="input-group-text">ROLE_</div>
			</div>
			<form:input path="name" type="text" class="form-control"
				required="required" />
		</div>
	</fieldset>



	<fieldset class="form-group">
		<div class="custom-control custom-checkbox my-1 mr-sm-2">
			<input id="PRIVILEGE_READ" name="privileges"
				class="custom-control-input" type="checkbox" value="1" checked disabled/> <label
				class="custom-control-label" for="PRIVILEGE_READ">PRIVILEGE_READ</label>
		</div>
		
		<div class="custom-control custom-checkbox my-1 mr-sm-2">
			<input id="PRIVILEGE_WRITE" name="privileges"
				class="custom-control-input" type="checkbox" value="2" />
			<label class="custom-control-label" for="PRIVILEGE_WRITE">PRIVILEGE_WRITE</label>
		</div>

		<div class="custom-control custom-checkbox my-1 mr-sm-2">
			<input id="PRIVILEGE_UPDATE" name="privileges"
				class="custom-control-input" type="checkbox" value="3" /> <label
				class="custom-control-label" for="PRIVILEGE_UPDATE">PRIVILEGE_UPDATE</label>
		</div>

		<div class="custom-control custom-checkbox my-1 mr-sm-2">
			<input id="PRIVILEGE_DELETE" name="privileges"
				class="custom-control-input" type="checkbox" value="4" /> <label
				class="custom-control-label" for="PRIVILEGE_DELETE">PRIVILEGE_DELETE</label>
		</div>
	</fieldset>
	
	<a class="btn btn-primary" href="/admin/settings" role="button">Back</a>
	<button type="submit" class="btn btn-success">Add</button>
</form:form>


<%@ include file="../common/footer.jspf"%>