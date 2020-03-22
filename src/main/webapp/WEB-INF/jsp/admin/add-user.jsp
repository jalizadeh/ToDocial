<%@ include file="../common/header.jspf" %>

		<h4 class="display-4">Add a new user:</h4>
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
		
		
		<form:form method="post" action="/admin/add-user" modelAttribute="user">
			<form:hidden path="id"/>
			
			<div class="form-group">
				<label for="firstname">Firstname</label>
				<form:input path="firstname" type="text" class="form-control" required="required"/>
				<form:errors path="firstname" cssClass="text-warning"/>
			</div>
			
			<div class="form-group">
				<label for="lastname">Lastname</label> 
				<form:input path="lastname" type="text" class="form-control" required="required"/>
				<form:errors path="lastname" cssClass="text-warning"/>
			</div>
			
			<div class="form-group">
				<label path="username">Username</label> 
				<form:input path="username" type="text" class="form-control" required="required"/>
				<form:errors path="username" cssClass="text-warning"/>
			</div>
			
			<div class="form-group">
				<label path="email">Email</label> 
				<form:input path="email" type="email" class="form-control" required="required"/>
				<form:errors path="email" cssClass="text-warning"/>
			</div>
			
			<div class="form-group">
				<label path="password">Password</label> 
				<form:input path="password" type="password" class="form-control" required="required"/>
				<form:errors path="password" cssClass="text-warning"/>
			</div>
			
			<div class="form-group">
				<label path="mp">Confirm password</label> 
				<form:input path="mp" type="password" class="form-control" required="required"/>
				<form:errors path="mp" cssClass="text-warning"/>
			</div>
			
			<div class="form-group">
			    <label for="enabled">Activity status</label>
			    <form:select class="form-control"  path="enabled">
				    <form:options items="${enabledValues}" />
				</form:select>
			 </div>
			 
			 <div class="form-group">
			    <label for="role">Role</label>
			    <form:select class="form-control"  path="role.name">
				    <form:options items="${roleValues}" />
				</form:select>
			 </div>
	
			<button type="submit" class="btn btn-success">Add</button>
		</form:form>
	
	
	<script src="/static_res/js/pwstrength.js"></script>
	<script src="/static_res/js/jquery.validate.js"></script>

	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							options = {
								common : {
									minChar : 8
								},
								ui : {
									showVerdictsInsideProgressBar : true,
									showErrors : true,
									errorMessages : {
										wordLength : 'Your password is too short',
										wordNotEmail : 'Do not use your email as your password',
										wordSequences : 'Your password contains sequences',
										wordLowercase : 'Use lower case characters',
										wordUppercase : 'Use upper case characters',
										wordOneNumber : 'Use numbers',
										wordOneSpecialChar : 'Use special characters: [~!@#$%^&amp;*,_?]'
									}
								}
							};
							$('#password').pwstrength(options);
						});
	</script>

<%@ include file="../common/footer.jspf" %>