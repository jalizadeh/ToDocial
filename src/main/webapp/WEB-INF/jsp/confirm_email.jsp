<%@ include file="common/header.jspf" %>

		<h4 class="display-4">Hey ${registeredUser.firstname} ${registeredUser.lastname},</h4>
		<h4>we sent you an email to ${registeredUser.email}. Please confirm it and enjoy :)</h4>

<%@ include file="common/footer.jspf" %>