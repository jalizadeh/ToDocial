<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

<div class="container mt-5 mb-5">
	<c:if test="${errorMessages != null}">
		<div class="alert alert-danger" role="alert">
			<p>There are some errors:</p>
  			<c:forEach items="${errorMessages}" var="e" >
  				<li><c:out value = "${e}"/></li>
  			</c:forEach>
		</div>
	</c:if>
	
	<c:if test="${exception != null}">
		<div class="alert alert-danger" role="alert">
			<p>There are some errors:</p>
  			<li>${exception}</li>
		</div>
	</c:if>
	
	<form:form method="post" action="/signup" modelAttribute="user">
		<div class="form-group">
		    <label for="firstname">Firstname</label>
		    <form:input path="firstname" type="text" class="form-control" name="firstname" id="firstname" required="required"/>
		    <form:errors path="firstname" cssClass="text-warning"/>
		</div>
		<div class="form-group">
		    <label for="lastname">Lastname</label>
		    <form:input path="lastname" type="text" class="form-control" name="lastname" id="lastname" required="required"/>
		    <form:errors path="lastname" cssClass="text-warning"/>
		</div>
		<div class="form-group">
		    <label for="username">Username</label>
		    <form:input path="username" type="text" class="form-control" name="username" id="username" required="required"/>
		    <form:errors path="username" cssClass="text-warning"/>
		</div>
		<div class="form-group">
		    <label for="email">Email</label>
		    <form:input path="email" type="email" class="form-control" name="email" id="email" aria-describedby="emailHelp" required="required"/>
		    <small id="emailHelp" class="form-text text-muted">We don't like spams, too.</small>
		    <form:errors path="email" cssClass="text-warning"/>
		</div>
		<div class="form-group">
		    <label for="password">Password</label>
		    <form:input path="password" type="password" class="form-control" name="password" id="password" aria-describedby="passwordHelp" required="required"/>
		    <small id="passwordHelp" class="form-text text-muted">We don't store plain passwords, they are all encrypted.</small>
		    <form:errors path="password" cssClass="text-warning"/>
		</div>
		<div class="form-group">
		    <label for="mp">Confirm Password</label>
		    <input path="mp" type="password" class="form-control" name="mp" id="mp" aria-describedby="mpHelp" required="required"/>
		    <small id="mpHelp" class="form-text text-muted">To make sure there is no mistake.</small>
		    <form:errors path="mp" cssClass="text-warning"/>
		</div>
		<div class="form-group">
			<label for="sq">Security Question</label>
			  <select class="form-control"  name="sq" aria-describedby="sqHelp" >
			  	 <c:forEach items="${securityQuestions}" var="q">
			            <option value="${q.id}">${q.text}</option>
    			</c:forEach>
			</select>
			<small id="sqHelp" class="form-text text-muted">The answer to this question is used for password recovery.</small>
		</div>
		
		<div class="form-group">
		    <label for="sqa">Answer</label>
		    <input type="text" class="form-control" name="sqa" id="sqa" required="required"/>
		</div>
		
		<button type="submit" class="btn btn-primary">Sign up</button>
	</form:form>
</div>


<%@ include file="common/footer.jspf" %>