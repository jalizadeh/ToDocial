<%@ include file="../common/header.jspf"%>

	<div class="row">
		<div class="col-3">
			<ul class="list-group">
				<li
					class="list-group-item list-group-item-secondary disabled bg-light"
					aria-disabled="true">Personal settings</li>
				<a href="/user/profile"
					class="list-group-item list-group-item-action">Profile</a>
				<a href="/user/account"
					class="list-group-item list-group-item-action active">Account</a>
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
					<label for="username">Username</label> <input type="text"
						class="form-control" name="firstname"
						value="${loggedinUser.username}">
				</div>
				<button type="submit" class="btn btn-primary">Update
					Account</button>
			</form>

			<div class="card mt-5">
				<div class="card-body">
					<h4 class="text-danger">Danger Zone</h4>
					<div class="border-top my-1 mb-1"></div>
					<p>Once you delete your account, there is no going back. Please
						be certain.</p>
					<a class="btn btn-danger" href="#" role="button">Delete my
						account</a>
				</div>
			</div>
		</div>
	</div>

<%@ include file="../common/footer.jspf"%>