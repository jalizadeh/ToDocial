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
									<span class="badge badge-pill badge-secondary" ><form:label path="days[${i-1}].totalWorkouts" id="totalWorkouts">1</form:label></span></a>
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

									<c:forEach var = "j" begin = "0" end = "0">
										<div class="row my-1" id="workoutContainer">
											<div class="col-6">
												<fieldset class="form-group">
													<label for="workoutDay${i}">Workout #${j+1}</label>
													<form:select multiple="single" path="days[${i-1}].dayWorkouts[${j}].workout" items="${workoutsList}" itemLabel="fullLabel" 
														class="form-control" id="workoutDay${i-1}_workout${j}" rows="3" required="required"/>
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
										<div id="newWorkoutContainer"></div>
									</c:forEach>

									<button type="button" id="addWorkout" class="btn btn-primary">Add Workout</button>
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

	<script>
		$(document).ready(function() {
			$('#addWorkout').click(function() {
				// Get the value from the span element with ID 'totalWorkouts'
				var totalWorkoutsValue = document.getElementById('totalWorkouts').textContent;
				var totalWorkoutsNumber = parseInt(totalWorkoutsValue, 10);

				var i = 0;
				var j = totalWorkoutsNumber;

				var htmlCode = `
					<div class="row my-1" id="workoutContainer">
						<div class="col-6">
							<fieldset class="form-group">
								<label for="workoutDay0_workout${j}">Workout #${j}</label>
								<select id="workoutDay0_workout${j}" name="days[0].dayWorkouts[${j}].workout" rows="3" class="form-control" required="required"><option value="1">1 - Bench Press</option><option value="2">2 - Incline Dumbbell Bench Press</option><option value="3">3 - Chest Dips</option><option value="4">4 - Pull-up</option><option value="5">5 - Barbell Bent Over Row</option><option value="6">6 - Lat Pulldown</option></select>
							</fieldset>
						</div>
						<div class="col">
							<fieldset class="form-group">
								<label for="days0.dayWorkouts${j}.sets">Sets</label>
								<input id="days0.dayWorkouts${j}.sets" name="days[0].dayWorkouts[${j}].sets" type="text" class="form-control" required="required" value="0">
								
							</fieldset>

							<fieldset class="form-group input-group flex-nowrap my-1">
								<span class="input-group-text" id="addon-wrapping">Reps</span>
								<input id="days0.dayWorkouts${j}.repsMin" name="days[0].dayWorkouts[${j}].repsMin" aria-describedby="addon-wrapping" placeholder="min" type="text" class="form-control" aria-label="reps_min" required="required" value="0">
								<input id="days0.dayWorkouts${j}.repsMax" name="days[0].dayWorkouts[${j}].repsMax" aria-describedby="addon-wrapping" placeholder="max" type="text" class="form-control" aria-label="reps_max" required="required" value="0">
							</fieldset>

							<fieldset class="form-group input-group flex-nowrap my-1">
								<span class="input-group-text" id="addon-wrapping">Rest</span>
								<input id="days0.dayWorkouts${j}.restMin" name="days[0].dayWorkouts[${j}].restMin" aria-describedby="addon-wrapping" placeholder="min" type="text" class="form-control" aria-label="reps_min" required="required" value="0">
								<input id="days0.dayWorkouts${j}.restMax" name="days[0].dayWorkouts[${j}].restMax" aria-describedby="addon-wrapping" placeholder="max" type="text" class="form-control" aria-label="reps_max" required="required" value="0">
							</fieldset>
						</div>
					</div>
				`;

				// Increment the counter for the next row
				i++;
				j++;

				// Append the new row to the container
				$('#newWorkoutContainer').append(htmlCode);
				$('#totalWorkouts').html(j);
			});
		});
		</script>

<%@ include file="../common/footer.jspf" %>