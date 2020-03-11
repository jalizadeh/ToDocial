<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

<div class="container">

	<h4 class="display-4">Your Profile:</h4>
	<form method="post">
			<div class="form-group">
			    <label for="firstname">Firstname</label>
			    <input type="text" class="form-control" name="firstname" value="${loggedinUser.firstname}">
			</div>
			<div class="form-group">
			    <label for="lastname">Lastname</label>
			    <input type="text" class="form-control" name="lastname" value="${loggedinUser.lastname}">
			</div>
			<div class="form-group">
			    <label for="username">Username</label>
			    <input type="text" class="form-control" name="firstname" value="${loggedinUser.username}">
			</div>
			<div class="form-group">
			    <label for="password">Password</label>
			    <input type="password" class="form-control" name="password" value="${loggedinUser.password}">
			</div>
			
			<button type="submit" class="btn btn-primary">Submit</button>
		</form>
</div>
	
<%@ include file="common/footer.jspf" %>