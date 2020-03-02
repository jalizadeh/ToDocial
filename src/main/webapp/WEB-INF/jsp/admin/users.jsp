<%@ include file="common/header.jspf" %>
<%@ include file="../common/navigation.jspf" %>


	<div class="container mt-5 mb-5">
		<h2 class="display-2">Admin Panel > users</h2>
		<p>Total number of users: ${users_count}</p>
		
		<table class="table table-striped table-hover">
			<thead>
				<tr>
					<th>ID</th>
					<th>Firstname</th>
					<th>Lastname</th>
					<th>Username</th>
					<th>Email</th>
					<th>Activated</th>
					<th>Role</th>
					<th></th>
				</tr>
			</thead>
				<c:forEach items="${all_users}" var="user">
					<tr>
						<th>${user.id}</th>
						<th>${user.firstname}</th>
						<th>${user.lastname}</th>
						<th>${user.username}</th>
						<th>${user.email}</th>
						<th>${user.enabled}</th>
						<th>${user.role.name}</th>
						<th>
						<a type="button" class="btn btn-success" href="/admin/change_user_state?id=${user.id}"><i class="fas fa-sync-alt"></i></a>
						<a type="button" class="btn btn-info" href="/admin/modify_user?id=${user.id}"><i class="fas fa-edit"></i></a>
						<a type="button" class="btn btn-warning" href="/admin/delete_user?id=${user.id}"><i class="fas fa-trash-alt"></i></a>
						</th>
					</tr>
				</c:forEach>
			<tbody>
			</tbody>
		</table>
		
		<div><a class="btn btn-primary" href="/admin/add_user" role="button">Add a user</a></div>
	</div>

<%@ include file="common/footer.jspf" %>