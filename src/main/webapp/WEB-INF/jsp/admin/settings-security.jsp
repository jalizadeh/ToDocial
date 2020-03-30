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
				class="list-group-item list-group-item-action">Todos</a>
			<a href="/admin/settings/security"
				class="list-group-item list-group-item-action active">Security</a>
		</ul>
	</div>
	
	<div class="col-9">
		<div class="card">
			<div class="card-body">
				<form method="post">
					

					<fieldset class="form-group">
						<div class="custom-control custom-checkbox mr-sm-2">
							<input type="checkbox" class="custom-control-input"
								id="customControlAutosizing"> <label
								class="custom-control-label" for="customControlAutosizing">Newly registered users must be verified for furthur access</label>
						</div>
					</fieldset>

					<fieldset class="form-group">
						<div class="custom-control custom-checkbox mr-sm-2">
							<input type="checkbox" class="custom-control-input"
								id="customControlAutosizing"> <label
								class="custom-control-label" for="customControlAutosizing">Users must be registered to access a public page</label>
						</div>
					</fieldset>
					
					<fieldset class="form-group">
						<div class="custom-control custom-checkbox mr-sm-2">
							<input type="checkbox" class="custom-control-input"
								id="customControlAutosizing"> <label
								class="custom-control-label" for="customControlAutosizing">Users must be registered to access a public todo</label>
						</div>
					</fieldset>

					<button type="submit" class="btn btn-primary">Save Changes</button>
				</form>
			</div>
		</div>
	</div>
</div>

<%@ include file="../common/footer.jspf"%>