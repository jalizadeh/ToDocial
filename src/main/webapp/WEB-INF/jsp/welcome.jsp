<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>


	<div class="container">
		<h2 class="display-2">Welcome ${name}</h2>
		<p>You have ${todoCount} todos. <a href="/list-todos"><span class="badge badge-secondary">Go to list</span></a></p>
	</div>

<%@ include file="common/footer.jspf" %>