<%@ include file="common/header.jspf"%>

<h4 class="display-4">${PageTitle}</h4>

<c:if test="${error != null}">
	<div class="alert alert-danger" role="alert">${error}</div>
</c:if>

<div class="row">
	<div class="col-8">
		<div class="card">
			<div class="card-body">
				<form:form method="post" modelAttribute="todo">
					<fieldset class="form-group">
						<form:label path="name">Name</form:label>
						<form:input path="name" type="text" class="form-control"
							required="required" />
						<form:errors path="name" cssClass="text-warning" />
					</fieldset>

					<fieldset class="form-group">
						<label for="description">Description</label>
						<form:textarea path="description" class="form-control"
							id="description" rows="3" required="required"></form:textarea>
						<form:errors path="description" cssClass="text-warning" />
					</fieldset>

					<fieldset class="form-group">
						<label for="reason">The reasons why this todo is/should be
							added</label>
						<form:textarea path="reason" class="form-control" id="reason"
							rows="3" required="required"></form:textarea>
						<form:errors path="reason" cssClass="text-warning" />
					</fieldset>


					<fieldset class="form-group">
						<label for="ttype">Type</label>
						<form:select class="form-control" path="ttype" items="${allType}"
							id="ttype" />
					</fieldset>


					<fieldset class="form-group">
						<label for="priority">Priority</label>
						<form:select class="form-control" path="priority"
							items="${allPriority}" id="priority" />
					</fieldset>

					<fieldset class="form-group">
						<form:label path="target_date">Target Date</form:label>
						<form:input path="target_date" type="text"
							class="form-control date-picker" required="required" />
						<form:errors path="target_date" cssClass="text-warning" />
					</fieldset>


					<fieldset class="form-group">
						<div class="custom-control custom-checkbox my-1 mr-sm-2">
							<form:checkbox path="publicc" value="${todo.publicc}"
								class="custom-control-input" id="ispublic" />
							<label class="custom-control-label" for="ispublic">Set as
								public (other users can see your Todo's progress)</label>
						</div>
					</fieldset>
					
					<c:if test="${todo.completed == true}">					
						<fieldset class="form-group">
							<label for="completion_note">Closing notes</label>
							<form:textarea path="completion_note" class="form-control"
								id="completion_note" rows="3"  required="required" ></form:textarea>
							<form:errors path="completion_note" cssClass="text-warning" />
						</fieldset>
					</c:if>
					
					<a class="btn btn-primary" href="/@<sec:authentication property='principal.username'/>" role="button">Cancel</a>
					<button type="submit" class="btn btn-success">Save Changes</button>
				</form:form>
			</div>
		</div>
	</div>

	<c:if test="${todo.id != null}">
		<div class="col">
			<div class="row">
				<!-- likes -->
				<div class="col">
					<div class="card  bg-info text-white">
						<div class="card-body">
							<p class="h2">
								<i class="fas fa-heart"></i> ${todo.like}
							</p>
						</div>
					</div>
				</div>

				<!-- dates -->
				<div class="col">
					<div class="card  bg-info text-white">
						<div class="card-body">
							<i class="fas fa-play-circle"></i>
							<fmt:formatDate value="${todo.creation_date}" pattern="yyyy/MM/dd" />
							<br /><i class="fas fa-crosshairs"></i>
							<fmt:formatDate value="${todo.target_date}" pattern="yyyy/MM/dd" />
							
							<c:if test="${todo.completed == true}">
								<br/><i class="fas fa-clipboard-check"></i>
								<fmt:formatDate value="${todo.completion_date}" pattern="yyyy/MM/dd" />
							</c:if>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col">
					<c:if test="${todo.completed == false}">
						<a class="btn btn-success btn-lg btn-block mt-2"
							href="/complete-todo?id=${todo.id}" role="button"><i class="far fa-check-circle"></i> Complete</a>
					</c:if>
				</div>
				
				<div class="col">
					<c:choose>
						<c:when test="${todo.canceled == false}">
						<a class="btn btn-danger btn-lg btn-block mt-2"
							href="/cancel-todo?id=${todo.id}" role="button"><i class="far fa-stop-circle"></i> Stop</a>
						</c:when>
						<c:otherwise>
							<a class="btn btn-warning btn-lg btn-block mt-2"
								href="/resume-todo?id=${todo.id}" role="button"><i class="fas fa-play-circle"></i> Resume</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>


			<div class="card mt-2">
				<h5 class="card-header">Logs</h5>
				<div class="card-body">
					<c:forEach items="${todo.logs}" var="log">
						<p>
							<a href="/update-todo-deletelog?todoId=${todo.id}&logId=${log.id}"> <i
								class="fas fa-times btn-delete-todo"></i>
							</a> <span class="badge badge-secondary"> <fmt:formatDate
									value="${log.logDate}" pattern="yyyy/MM/dd" />
							</span> ${log.log}
						</p>
					</c:forEach>

					<form method="post" autocomplete="off" action="/update-todo-newlog">
						<div class="input-group">
							<input type="hidden" id="todoId" name="todoId" value="${todo.id}">
							<input type="text" class="form-control"
								aria-label="Text input with segmented dropdown button"
								name="log" id="log">
							<div class="input-group-append">
								<button type="submit" class="btn btn-outline-secondary">Post</button>
								
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</c:if>
</div>





<script type="text/javascript">
	$('.date-picker').datepicker({
		format : 'dd/mm/yyyy'
	});
</script>

<%@ include file="common/footer.jspf"%>