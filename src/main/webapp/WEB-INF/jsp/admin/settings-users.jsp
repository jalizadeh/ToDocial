<%@ include file="../common/header.jspf"%>

<div class="row">
	<div class="col-3">
		<ul class="list-group">
			<li
				class="list-group-item list-group-item-secondary disabled bg-light"
				aria-disabled="true">Administrative Settings</li>
			<a href="/admin/settings"
				class="list-group-item list-group-item-action">General</a>
			<a href="/admin/settings/users"
				class="list-group-item list-group-item-action active">Users</a>
			<a href="/admin/settings/todos"
				class="list-group-item list-group-item-action">Todos</a>
			<a href="/admin/settings/security"
				class="list-group-item list-group-item-action">Security</a>
		</ul>
	</div>

	<div class="col-9">
		<div class="card">
			<div class="card-body">
				<div class="row">
					<div class="col-8">
						<h4 class="display-4">Admin Panel > Users</h4>
					</div>
					<div class="col-4 text-center">
						<a class="btn btn-primary" href="/admin/add-user" role="button">Add
							a user</a>
					</div>
				</div>

				<p>Total number of users: ${all_users.size()}</p>

				<table class="table table-striped table-hover">
					<thead>
						<tr>
							<th>Username</th>
							<th>Email</th>
							<th>Role</th>
							<th></th>
						</tr>
					</thead>
					<c:forEach items="${all_users}" var="user">
						<tr>
							<th>${user.username}</th>
							<th>${user.email}</th>
							
							<th><c:forEach items="${user.roles}" var="role">
								${role.name}
								<br />
								</c:forEach></th>
							
							<th><a type="button" class="btn btn-success"
								href="/admin/change_user_state?id=${user.id}"><i
									class="fas fa-sync-alt"></i></a> <a type="button"
								class="btn btn-info" href="/admin/modify_user?id=${user.id}"><i
									class="fas fa-edit"></i></a> <a type="button"
								class="btn btn-warning" href="/admin/delete_user?id=${user.id}"><i
									class="fas fa-trash-alt"></i></a></th>
						</tr>
					</c:forEach>
					<tbody>
					</tbody>
				</table>

			</div>
		</div>



		<div class="card mt-5">
			<div class="card-body">
				<h4 class="display-4">Online Users</h4>
				<c:forEach items="${listOfLoggedinUsers}" var="user">
					<p>${user}</p>
				</c:forEach>
			</div>
		</div>

	</div>
</div>

<%@ include file="../common/footer.jspf"%>