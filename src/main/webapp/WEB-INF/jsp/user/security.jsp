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
					class="list-group-item list-group-item-action">Account</a>
				<a href="/user/security"
					class="list-group-item list-group-item-action active">Security</a>
			</ul>
			<ul class="list-group mt-3">
				<a href="/logout"
					class="list-group-item list-group-item-action list-group-item-secondary">Log
					out</a>
			</ul>
		</div>

		<div class="col-9">
			<div>
				<h4>Change Password</h4>
				<div class="border-top my-1 mb-1"></div>
				<div class="form-group">
					<label for="password">New password</label> <input type="password"
						class="form-control" name="password" id="password">
				</div>
				<div class="form-group">
					<label for="password">Confirm new password</label> <input
						type="password" class="form-control" name="mp" id="mp">
				</div>
				<p>Make sure it's at least 15 characters OR at least 8
					characters including a number and a lowercase letter.</p>
				<a class="btn btn-success" href="#" role="button">Update
					Password</a> <a href="#" role="button">I forgot my password</a>
			</div>

			<div class="mt-5">
				<h4>Two-factor authentication</h4>
				<div class="border-top my-1 mb-1"></div>
				<div class="text-center mt-3">
					<i class="fas fa-key fa-2x text-success"></i>
					<h4>Two factor authentication is not enabled yet</h4>
					<p>Two-factor authentication adds an additional layer of
						security to your account<br/>
					by requiring more than just a password to log in.</p>
					<a class="btn btn-success" href="#" role="button">Enable
						two-factor authentication</a>
				</div>
			</div>

		</div>
	</div>

<script src="/static_res/js/pwstrength.js"></script>
<script src="/static_res/js/jquery.validate.js"></script>

<script type="text/javascript">
		$(document)
				.ready(
						function() {
							options = {
								common : {
									minChar : 8
								},
								ui : {
									showVerdictsInsideProgressBar : true,
									showErrors : true,
									errorMessages : {
										wordLength : 'Your password is too short',
										wordNotEmail : 'Do not use your email as your password',
										wordSequences : 'Your password contains sequences',
										wordLowercase : 'Use lower case characters',
										wordUppercase : 'Use upper case characters',
										wordOneNumber : 'Use numbers',
										wordOneSpecialChar : 'Use special characters: [~!@#$%^&amp;*,_?]'
									}
								}
							};
							$('#password').pwstrength(options);
						});
	</script>

<%@ include file="../common/footer.jspf"%>