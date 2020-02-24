<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

	
	<font color="red">${error}</font>
	
	<form method="post">
	Firstname: <input type="text" name="firstname"/>
	<br>
	Lastname: <input type="text" name="lastname"/>
	<br>
	Email: <input type="text" name="email"/>
	<br>
	Password: <input type="password" name="password"/>
	<br>
	Confirm Password: <input type="password" new="confirmpassword"/>
	<br>
	Remember me <input name="remember" type="checkbox" value="true"><br>
	<input type="submit"/>
	</form>

	
<%@ include file="common/footer.jspf" %>