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
				class="list-group-item list-group-item-action">Users</a>
			<a href="/admin/settings/todos"
				class="list-group-item list-group-item-action active">Todos</a>
			<a href="/admin/settings/security"
				class="list-group-item list-group-item-action">Security</a>
		</ul>
	</div>

	<div class="col-9">
		<div class="card">
			<div class="card-body">
				<div class="row">
					<div class="col-8">
						<h4 class="display-4">Admin Panel > todos</h4>
					</div>
					<div class="col-4 text-center">
						<a class="btn btn-primary" href="#" role="button">...</a>
					</div>
				</div>

				<p>Total number of users: ${all_todos.size()}</p>

				<table class="table table-striped table-hover">
					<thead>
						<tr>
							<th>Description</th>
							<th>Creation Date</th>
							<th>Due Date</th>
							<th>Completed?</th>
							<th>Public?</th>
							<th>Likes</th>
							<th>User</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${all_todos}" var="todo">
						<tr>
							<th>${todo.name}</th>
							<th><fmt:formatDate value="${todo.creation_date}"
									pattern="yyyy/MM/dd" /></th>
							<th><fmt:formatDate value="${todo.target_date}"
									pattern="yyyy/MM/dd" /></th>
							<th>${todo.completed}</th>
							<th>${todo.publicc}</th>
							<th>${todo.like}</th>
							<th><a href="/@${todo.user.username}">${todo.user.username}</a></th>
							<th><a type="button" class="btn btn-warning"
								href="/admin/delete_todo?id=${todo.id}"><i
									class="fas fa-trash-alt"></i></a></th>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>

		<div class="card">
			<div class="card-body">
				<form method="post">
					
						<fieldset class="form-group">
							<label for="inputState">Todo page size (how many todos
								are loaded in one page)</label> <select id="inputState"
								class="form-control">
								<option selected>10</option>
								<option>20</option>
								<option>30</option>
							</select>
						</fieldset>


						<fieldset class="form-group">
							<div class="custom-control custom-checkbox mr-sm-2">
								<input type="checkbox" class="custom-control-input"
									id="customControlAutosizing"> <label
									class="custom-control-label" for="customControlAutosizing">Allow
									search engines to fetch public pages</label>
							</div>
						</fieldset>

						<fieldset class="form-group">
							<div class="custom-control custom-checkbox mr-sm-2">
								<input type="checkbox" class="custom-control-input"
									id="customControlAutosizing"> <label
									class="custom-control-label" for="customControlAutosizing">Users
									must be registered to access a public page</label>
							</div>
						</fieldset>

						<fieldset class="form-group">
							<div class="custom-control custom-checkbox mr-sm-2">
								<input type="checkbox" class="custom-control-input"
									id="customControlAutosizing"> <label
									class="custom-control-label" for="customControlAutosizing">Users
									must be registered to access a public todo</label>
							</div>
						</fieldset>

						<button type="submit" class="btn btn-primary">Save
							Changes</button>
				</form>
			</div>
		</div>
	</div>
</div>
<%@ include file="../common/footer.jspf"%>