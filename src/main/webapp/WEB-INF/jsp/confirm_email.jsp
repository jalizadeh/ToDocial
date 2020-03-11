<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>


	<div class="container mt-5 mb-5">
		<h4 class="display-4">Hey ${registeredUser.firstname} ${registeredUser.lastname},</h4>
		<h4>we sent you a email via ${registeredUser.email}. Please confirm it and enjoy :)</h4>
	</div>

<%@ include file="common/footer.jspf" %>