<%@ include file="../common/header.jspf"%>

<div class="row mt-2">
	<div class="col border-bottom">
		<h2>Add New Plan - Step 1</h2>
	</div>
</div>

<div class="row">
	
	<div class="col-8">
		<div class="card">
			<div class="card-body">
				<form:form method="post" modelAttribute="plan">
					<fieldset class="form-group">
						<form:label path="title">Workout title</form:label>
						<form:input path="title" type="text" class="form-control" required="required" />
						<form:errors path="title" cssClass="text-warning" />
					</fieldset>

					<fieldset class="form-group">
						<label for="numberOfWeeks">Number of weeks</label>
						<form:select path="numberOfWeeks" items="${weeks}" class="form-control" id="numberOfDays" rows="3" required="required"/>
					</fieldset>

					<fieldset class="form-group">
						<label for="numberOfDays">Number of days</label>
						<form:select path="numberOfDays" items="${days}" class="form-control" id="numberOfDays" rows="3" required="required"/>
					</fieldset>
					
					<a class="btn btn-primary" href="/gym" role="button">Cancel</a>
					<button type="submit" class="btn btn-success">Next</button>
				</form:form>
			</div>
		</div>
	</div>

	
</div>


<%@ include file="../common/footer.jspf"%>