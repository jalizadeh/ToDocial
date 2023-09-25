<%@ include file="../common/header.jspf" %>

	<div class="row mt-2">
		<div class="col border-bottom">
			<h2>Add New Plan - Step 2 > ${plan.title} [${plan.numberOfWeeks}W / ${plan.numberOfDays}D]</h2>
		</div>
	</div>

	<form:form method="post" modelAttribute="plan">
		<div class="row">
			<div class="col-8">
				<div class="card">
					<div class="card-body">
						<fieldset class="form-group">
							<form:label path="title">Plan title</form:label>
							<form:input path="title" type="text" class="form-control" required="required" />
							<form:errors path="title" cssClass="text-warning" />
						</fieldset>

						<div class="row">
							<div class="col">
								<fieldset class="form-group">
									<label for="numberOfWeeks">Number of weeks</label>
									<form:select path="numberOfWeeks" items="${weeks}" class="form-control" id="numberOfWeeks" rows="3" required="required"/>
								</fieldset>
							</div>
							<div class="col">
								<fieldset class="form-group">
									<label for="numberOfDays"><span class="text-warning">*</span>Number of days <span class="text-warning">(can't be changed)</span></label>
									<form:select path="numberOfDays" id="numberOfDays" class="form-control form-select">
										<form:option value="${plan.numberOfDays}" label="${plan.numberOfDays}" />
									</form:select>
								</fieldset>
							</div>
						</div>
					</div>
				</div>

				<div class="card">
					<div class="card-body">
						<ul class="nav nav-tabs" id="myTab" role="tablist">
							<c:forEach var = "i" begin = "1" end = "${plan.numberOfDays}">
								<li class="nav-item ">
									<a class="nav-link ${i==1 ? 'active' : ''}" id="day${i}-tab" data-toggle="tab" 
									href="#day${i}" role="tab" aria-controls="day${i}" aria-selected="false">Day ${i}
									<span class="badge badge-pill badge-secondary">1</span></a>
								</li>
							</c:forEach>
						</ul>
						<div class="tab-content mt-2" id="myTabContent">
							<c:forEach var = "i" begin = "1" end = "${plan.numberOfDays}">
								<div class="tab-pane fade show ${i==1 ? 'active' : ''}" id="day${i}" role="tabpanel" aria-labelledby="day${i}-tab">
									<div class="row">
										<div class="col">
											<fieldset class="form-group">
												<form:label path="days[${i-1}].dayNumber">Day</form:label>
												<form:input path="days[${i-1}].dayNumber" type="text" class="form-control" required="required" />
												<form:errors path="days[${i-1}].dayNumber" cssClass="text-warning" />
											</fieldset>
										</div>
										<div class="col">
											<fieldset class="form-group">
												<form:label path="days[${i-1}].focus">Day Focus</form:label>
												<form:input path="days[${i-1}].focus" type="text" class="form-control" required="required" />
												<form:errors path="days[${i-1}].focus" cssClass="text-warning" />
											</fieldset>
										</div>
									</div>

									<c:forEach var = "j" begin = "0" end = "2">
										<div class="row my-1">
											<div class="col-6">
												<fieldset class="form-group">
													<label for="workoutDay${i}">Workout #${j+1}</label>
													<form:select multiple="single" path="days[${i-1}].dayWorkouts[${j}].workout" items="${workoutsList}" itemLabel="fullLabel" 
														class="form-control" id="workoutDay${i}" rows="3" required="required"/>
												</fieldset>
											</div>
											<div class="col">
												<fieldset class="form-group">
													<form:label path="days[${i-1}].dayWorkouts[${j}].sets">Sets</form:label>
													<form:input path="days[${i-1}].dayWorkouts[${j}].sets" type="text" class="form-control" required="required" />
													<form:errors path="days[${i-1}].dayWorkouts[${j}].sets" cssClass="text-warning" />
												</fieldset>
	
												<fieldset class="form-group input-group flex-nowrap my-1">
													<span class="input-group-text" id="addon-wrapping">Reps</span>
													<form:input path="days[${i-1}].dayWorkouts[${j}].repsMin" type="text" class="form-control" placeholder="min" aria-label="reps_min" aria-describedby="addon-wrapping" required="required" />
													<form:input path="days[${i-1}].dayWorkouts[${j}].repsMax" type="text" class="form-control" placeholder="max" aria-label="reps_max" aria-describedby="addon-wrapping" required="required" />
												</fieldset>
	
												<fieldset class="form-group input-group flex-nowrap my-1">
													<span class="input-group-text" id="addon-wrapping">Rest</span>
													<form:input path="days[${i-1}].dayWorkouts[${j}].restMin" type="text" class="form-control" placeholder="min" aria-label="reps_min" aria-describedby="addon-wrapping" required="required" />
													<form:input path="days[${i-1}].dayWorkouts[${j}].restMax" type="text" class="form-control" placeholder="max" aria-label="reps_max" aria-describedby="addon-wrapping" required="required" />
												</fieldset>
											</div>
										</div>
									</c:forEach>

								</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>

			<div class="col">
				<div class="card">
					<div class="card-body">
						<fieldset class="form-group">
							<form:label path="gymPlanIntroduction.moreInfo">Description</form:label>
							<form:textarea path="gymPlanIntroduction.moreInfo" class="form-control" id="moreInfo" rows="3" required="required" value="dumm"></form:textarea>
							<form:errors path="gymPlanIntroduction.moreInfo" cssClass="text-warning" />
						</fieldset>

						<fieldset class="form-group">
							<label for="gymPlanIntroduction.trainingLevel">Training Level</label>
							<form:select path="gymPlanIntroduction.trainingLevel" items="${trainingLevels}" class="form-control" id="gymPlanIntroduction.trainingLevel" required="required"/>
						</fieldset>

						<fieldset class="form-group">
							<label for="gymPlanIntroduction.mainGoal">Main Goal</label>
							<form:select path="gymPlanIntroduction.mainGoal" items="${mainGoals}" class="form-control" id="gymPlanIntroduction.mainGoal" required="required"/>
						</fieldset>

						<fieldset class="form-group">
							<label for="gymPlanIntroduction.workoutType">Workout Type</label>
							<form:select path="gymPlanIntroduction.workoutType" items="${workoutTypes}" class="form-control" id="gymPlanIntroduction.workoutType" required="required"/>
						</fieldset>

					</div>
				</div>
			</div>

		</div>

		<div class="mt-2">
			<a class="btn btn-primary" href="?step=1" role="button">Back</a>
			<button type="submit" class="btn btn-success">Save new plan</button>
		</div>

	</form:form>


<%@ include file="../common/footer.jspf" %>