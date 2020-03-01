<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

<div class="container mt-5 mb-5">
	<p>Hey ${user.firstname} ${user.lastname}, we sent you a email via ${user.email}</p>
	<p>Please confirm it and enjoy :)</p>
</div>

<%@ include file="common/footer.jspf" %>