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
		        <form:input  path="name" type="text" class="form-control" required="required" />
		      </div>
		</fieldset>

		
		
		<fieldset class="form-group">
		 	<c:forEach items="${role.privileges}" var="privilege">
		 		<div class="custom-control custom-checkbox my-1 mr-sm-2">
					<form:checkbox path="privileges" value="${privilege.id}"
					class="custom-control-input" id="${privilege.name}" />
					<label class="custom-control-label" for="${privilege.name}">${privilege.name}</label>
				</div>
		 	</c:forEach>
		</fieldset>
		
		<button type="submit" class="btn btn-success">Submit</button>
	</form:form>


<%@ include file="../common/footer.jspf"%>