<%@ include file="../common/header.jspf"%>

<h4 class="display-4">${PageTitle}</h4>

<c:if test="${error != null}">
	<div class="alert alert-danger" role="alert">${error}</div>
</c:if>


<form:form method="post" modelAttribute="role">
	<fieldset class="form-group">
		<form:input path="name" type="text" class="form-control" required="required" />
	</fieldset>



	<fieldset class="form-group">
		<div class="custom-control custom-checkbox my-1 mr-sm-2">
			<input id="PRIVILEGE_READ" name="privileges"
				class="custom-control-input" type="checkbox" value="1" checked disabled/> <label
				class="custom-control-label" for="PRIVILEGE_READ">PRIVILEGE_READ</label>
		</div>
		
		<div class="custom-control custom-checkbox my-1 mr-sm-2">
			<input id="PRIVILEGE_WRITE" name="privileges"
				class="custom-control-input" type="checkbox" value="2"
				<c:forEach items="${role.privileges}" var="p">
					<c:if test="${p.id == 2}">
						<c:out value="checked"/>
					</c:if>
				</c:forEach>
				/> 
			<label class="custom-control-label" for="PRIVILEGE_WRITE"> PRIVILEGE_WRITE</label>
		</div>

		<div class="custom-control custom-checkbox my-1 mr-sm-2">
			<input id="PRIVILEGE_UPDATE" name="privileges"
				class="custom-control-input" type="checkbox" value="3" 
				<c:forEach items="${role.privileges}" var="p">
					<c:if test="${p.id == 3}">
						<c:out value="checked"/>
					</c:if>
				</c:forEach>
				/>
				<label class="custom-control-label" for="PRIVILEGE_UPDATE">PRIVILEGE_UPDATE</label>
		</div>

		<div class="custom-control custom-checkbox my-1 mr-sm-2">
			<input id="PRIVILEGE_DELETE" name="privileges"
				class="custom-control-input" type="checkbox" value="4" 
				<c:forEach items="${role.privileges}" var="p">
					<c:if test="${p.id == 4}">
						<c:out value="checked"/>
					</c:if>
				</c:forEach>
				/> 
				<label class="custom-control-label" for="PRIVILEGE_DELETE">PRIVILEGE_DELETE</label>
		</div>
	</fieldset>
	
	<a class="btn btn-primary" href="/admin/settings" role="button">Back</a>
	<button type="submit" class="btn btn-success">Save</button>
	<a class="btn btn-danger" href="/admin/settings/delete-role?role_name=${role.name}" role="button">Delete</a>
</form:form>


<%@ include file="../common/footer.jspf"%>