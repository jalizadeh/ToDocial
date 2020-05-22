<%@ include file="../common/header.jspf"%>


<div class="row">
	<div class="col-3">
		<ul class="list-group">
			<li
				class="list-group-item list-group-item-secondary disabled bg-light"
				aria-disabled="true"><spring:message code="settings.menu.title" /></li>
			<a href="/admin/settings"
				class="list-group-item list-group-item-action active"><spring:message
					code="settings.menu.general" /></a>
			<a href="/admin/settings/users"
				class="list-group-item list-group-item-action"><spring:message
					code="settings.menu.users" /></a>
			<a href="/admin/settings/todos"
				class="list-group-item list-group-item-action"><spring:message
					code="settings.menu.todos" /></a>
			<a href="/admin/settings/security"
				class="list-group-item list-group-item-action"><spring:message
					code="settings.menu.security" /></a>
		</ul>
	</div>
	<div class="col-9">
		<c:if test="${flash != null}">
			<div class="alert alert-${flash.status} alert-dismissible fade show"
				role="alert">
				${flash.message}
				<button type="button" class="close" data-dismiss="alert"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</c:if>

		<div class="card">
			<div class="card-body">
				<form:form method="post" modelAttribute="settingsObj">
					<div class="form-group">
						<label for="siteName">Site Name</label>
						<form:input path="siteName" type="text" class="form-control"
							id="siteName" required="required" value="${settings.siteName}" />
						<form:errors path="siteName" cssClass="text-warning" />
					</div>

					<div class="form-group">
						<label for="siteDescription">Site Description</label>
						<form:input path="siteDescription" type="text"
							class="form-control" id="siteDescription" required="required"
							value="${settings.siteDescription}" />
					</div>

					<div class="form-group">
						<label for="footerCopyright">Footer Copyright text</label>
						<form:input path="footerCopyright" type="text"
							class="form-control" id="footerCopyright" required="required"
							value="${settings.footerCopyright}" />
					</div>



					<fieldset class="form-group">
						<div class="custom-control custom-switch">
							<form:checkbox path="anyoneCanRegister"
								value="${settings.anyoneCanRegister}"
								class="custom-control-input" id="anyoneCanRegister" />
							<label class="custom-control-label" for="anyoneCanRegister">Anyone
								can register</label>
						</div>
					</fieldset>



					<fieldset class="form-group">
						<label for="inputState">Default role for registered user <a
							href="/admin/settings/add-role" class="badge badge-success"><i
								class="fas fa-plus"></i> Add new role</a> <a
							class="badge badge-success text-white" onclick='modifyRole()'><i
								class="fas fa-cog"></i> Modify selected role</a>
						</label>
						<form:select class="form-control" path="defaultRole"
							items="${roles}" id="roles" />
					</fieldset>


					<fieldset class="form-group">
						<div class="row">
							<div class="col">
								<div class="row">
									<legend class="col-form-label col-sm-4 pt-0">Date
										Structure</legend>
									<div class="col-sm-8">
										<div class="custom-control custom-radio">
											<form:radiobutton path="dateStructure" value="pattern"
												checked="${settings.dateStructure == 'pattern' ? 'checked' : '' }"
												class="custom-control-input" id="ds1" />
											<label class="custom-control-label" for="ds1"> <fmt:formatDate
													type="date" value="${now}" pattern="yyyy/MM/dd" />
											</label>
										</div>
										<div class="custom-control custom-radio">
											<form:radiobutton path="dateStructure" value="short"
												checked="${settings.dateStructure == 'short' ? 'checked' : '' }"
												class="custom-control-input" id="ds2" />
											<label class="custom-control-label" for="ds2"> <fmt:formatDate
													type="date" dateStyle="short" value="${now}" />
											</label>
										</div>
										<div class="custom-control custom-radio">
											<form:radiobutton path="dateStructure" value="medium"
												checked="${settings.dateStructure == 'medium' ? 'checked' : '' }"
												class="custom-control-input" id="ds3" />
											<label class="custom-control-label" for="ds3"> <fmt:formatDate
													type="date" dateStyle="medium" value="${now}" />
											</label>
										</div>
										<div class="custom-control custom-radio">
											<form:radiobutton path="dateStructure" value="long"
												checked="${settings.dateStructure == 'long' ? 'checked' : '' }"
												class="custom-control-input" id="ds4" />
											<label class="custom-control-label" for="ds4"> <fmt:formatDate
													type="date" dateStyle="long" value="${now}" />
											</label>
										</div>
									</div>
								</div>
							</div>

							<div class="col">
								<div class="row">
									<legend class="col-form-label col-sm-4 pt-0">Time
										Structure</legend>
									<div class="col-sm-8">
										<div class="custom-control custom-radio">
											<form:radiobutton path="timeStructure" value="short"
												checked="${settings.timeStructure == 'short' ? 'checked' : '' }"
												class="custom-control-input" id="ts1" />
											<label class="custom-control-label" for="ts1"> <fmt:formatDate
													type="time" timeStyle="short" value="${now}" />
											</label>
										</div>
										<div class="custom-control custom-radio">
											<form:radiobutton path="timeStructure" value="medium"
												checked="${settings.timeStructure == 'medium' ? 'checked' : '' }"
												class="custom-control-input" id="ts2" />
											<label class="custom-control-label" for="ts2"> <fmt:formatDate
													type="time" timeStyle="medium" value="${now}" />
											</label>
										</div>
										<div class="custom-control custom-radio">
											<form:radiobutton path="timeStructure" value="long"
												checked="${settings.timeStructure == 'long' ? 'checked' : '' }"
												class="custom-control-input" id="ts3" />
											<label class="custom-control-label" for="ts3"> <fmt:formatDate
													type="time" timeStyle="long" value="${now}" />
											</label>
										</div>
									</div>
								</div>
							</div>
						</div>


					</fieldset>



					<fieldset class="form-group">
						<label class="my-1 mr-2" for="language">Language</label>
						<form:select class="form-control" path="language"
							items="${languages}" />
					</fieldset>


					<button type="submit" class="btn btn-primary">Save Changes</button>
				</form:form>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	function modifyRole() {
		window.location = "/admin/settings/modify-role?role_name="
				+ document.getElementById('roles').value;
	}
</script>

<%@ include file="../common/footer.jspf"%>