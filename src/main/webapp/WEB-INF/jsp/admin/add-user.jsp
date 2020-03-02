<%@ include file="common/header.jspf" %>
<%@ include file="../common/navigation.jspf" %>


	<div class="container">
		<h4 class="display-4">Add a new user:</h4>
		<c:if test="${errorMessages != null}">
			<div class="alert alert-danger" role="alert">
				<p>There are some errors:</p>
	  			<c:forEach items="${errorMessages}" var="e" >
	  				<li><c:out value = "${e}"/></li>
	  			</c:forEach>
			</div>
		</c:if>
		
		
		<form:form method="post" modelAttribute="nUser">
			<form:hidden path="id"/>
			
			<fieldset class="form-group">
				<form:label path="firstname">Firstname</form:label> 
				<form:input path="firstname" type="text" class="form-control" required="required"/>
				<form:errors path="firstname" cssClass="text-warning"/>
			</fieldset>
			
			<fieldset class="form-group">
				<form:label path="lastname">Lastname</form:label> 
				<form:input path="lastname" type="text" class="form-control" required="required"/>
				<form:errors path="lastname" cssClass="text-warning"/>
			</fieldset>
			
			<fieldset class="form-group">
				<form:label path="username">Username</form:label> 
				<form:input path="username" type="text" class="form-control" required="required"/>
				<form:errors path="username" cssClass="text-warning"/>
			</fieldset>
			
			<fieldset class="form-group">
				<form:label path="email">Email</form:label> 
				<form:input path="email" type="email" class="form-control" required="required"/>
				<form:errors path="email" cssClass="text-warning"/>
			</fieldset>
			
			<fieldset class="form-group">
				<form:label path="password">Password</form:label> 
				<form:input path="password" type="password" class="form-control" required="required"/>
				<form:errors path="password" cssClass="text-warning"/>
			</fieldset>
			
			<fieldset class="form-group">
				<form:label path="mp">Confirm password</form:label> 
				<form:input path="mp" type="password" class="form-control" required="required"/>
				<form:errors path="mp" cssClass="text-warning"/>
			</fieldset>
			
			<fieldset class="form-group">
			    <label for="enabled">Activity status</label>
			    <form:select class="form-control"  path="enabled">
				    <form:options items="${enabledValues}" />
				</form:select>
			 </fieldset>
			 
			 <fieldset class="form-group">
			    <label for="role">Role</label>
			    <form:select class="form-control"  path="role.name">
				    <form:options items="${roleValues}" />
				</form:select>
			 </fieldset>
	
			<button type="submit" class="btn btn-success">Add</button>
		</form:form>
	</div>

<%@ include file="common/footer.jspf" %>