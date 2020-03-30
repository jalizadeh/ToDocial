<%@ include file="../common/header.jspf"%>

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
						<th>Firstname</th>
						<th>Lastname</th>
						<th>Username</th>
						<th>Email</th>
						<th>Activated</th>
						<th>Role</th>
						<th>Privileges</th>
						<th></th>
					</tr>
				</thead>
				<c:forEach items="${all_users}" var="user">
					<tr>
						<th>${user.firstname}</th>
						<th>${user.lastname}</th>
						<th>${user.username}</th>
						<th>${user.email}</th>
						<th>${user.enabled}</th>
						<th>
							<c:forEach items="${user.roles}" var="role">
								${role.name}
								<br/>
							</c:forEach>
						</th>
						<th>
							<c:forEach items="${user.roles}" var="role">
								<c:forEach items="${role.privileges}" var="privilege">
									${privilege.name}
									<br/>
								</c:forEach>
							</c:forEach>
						</th>
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

<%@ include file="../common/footer.jspf"%>