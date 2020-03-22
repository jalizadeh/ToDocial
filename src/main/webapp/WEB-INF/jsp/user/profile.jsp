<%@ include file="../common/header.jspf"%>

	<div class="row">

		<div class="col-3">
			<ul class="list-group">
				<li
					class="list-group-item list-group-item-secondary disabled bg-light"
					aria-disabled="true">Personal settings</li>
				<a href="/user/profile"
					class="list-group-item list-group-item-action active">Profile</a>
				<a href="/user/account"
					class="list-group-item list-group-item-action">Account</a>
				<a href="/user/security"
					class="list-group-item list-group-item-action">Security</a>
			</ul>
			<ul class="list-group mt-3">
				<a href="/logout"
					class="list-group-item list-group-item-action list-group-item-secondary">Log
					out</a>
			</ul>
		</div>
		<div class="col-9">
			<form method="post">
				<div class="form-group">
					<label for="firstname">Firstname</label> <input type="text"
						class="form-control" name="firstname"
						value="${loggedinUser.firstname}">
				</div>
				<div class="form-group">
					<label for="lastname">Lastname</label> <input type="text"
						class="form-control" name="lastname"
						value="${loggedinUser.lastname}">
				</div>

				<button type="submit" class="btn btn-primary">Update
					Profile</button>
			</form>
		</div>
	</div>

<%@ include file="../common/footer.jspf"%>