<%@ include file="common/header.jspf" %>

	<h4 class="display-4">Change Password:</h4>
	
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
	
	<form:form method="post" action="/change-password">
		<div class="form-group">
			<label for="sq">Security Question</label>
			  <select class="form-control"  name="sq" aria-describedby="sqHelp" >
			  	 <c:forEach items="${securityQuestions}" var="q">
			            <option value="${q.id}">${q.text}</option>
    			</c:forEach>
			</select>
			<small id="sqHelp" class="form-text text-muted">Choose the question you selected when you signed up.</small>
		</div>
		<div class="form-group">
		    <label for="sqa">Answer</label>
		    <input type="text" class="form-control" name="sqa" id="sqa" required="required"/>
		</div>
		
		<div class="form-group">
		    <label for="password">New Password</label>
		    <input type="password" class="form-control" name="password" id="password" required="required"/>
		</div>
		<div class="form-group">
		    <label for="mp">Confirm New Password</label>
		    <input type="password" class="form-control" name="mp" id="mp"  required="required"/>
		</div>
		
		<button type="submit" class="btn btn-primary">Submit</button>
	</form:form>


<%@ include file="common/footer.jspf" %>