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
										<span class="badge badge-pill badge-secondary" id="labelTotalWorkouts_${i-1}">1</span></a>
										<input type="hidden" id="totalWorkouts_${i-1}" name="days[${i-1}].totalWorkouts" value="1"/>
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

										<div class="row my-1" id="workoutContainer">
											<div class="col-6">
												<fieldset class="form-group">
													<label for="workoutDay${i}">Workout #${j+1}</label>
													<form:select multiple="single" path="days[${i-1}].dayWorkouts[0].workout" items="${workoutsList}" itemLabel="fullLabel" 
														class="form-control" id="workoutDay${i-1}_workout0" rows="3" required="required"/>
												</fieldset>
											</div>
											<div class="col">
												<fieldset class="form-group">
													<form:label path="days[${i-1}].dayWorkouts[0].sets">Sets</form:label>
													<form:input path="days[${i-1}].dayWorkouts[0].sets" type="number" class="form-control" required="required" min="1"/>
													<form:errors path="days[${i-1}].dayWorkouts[0].sets" cssClass="text-warning" />
												</fieldset>
											</div>
											<div class="col">
												<fieldset class="form-group">
													<form:label path="days[${i-1}].dayWorkouts[0].repsMin">Reps min</form:label>
													<form:input path="days[${i-1}].dayWorkouts[0].repsMin" type="number" class="form-control" placeholder="min" aria-label="reps_min" aria-describedby="addon-wrapping" required="required" min="1"/>
												</fieldset>
											</div>
											<div class="col">
												<fieldset class="form-group">
													<form:label path="days[${i-1}].dayWorkouts[0].repsMax">Reps max</form:label>
													<form:input path="days[${i-1}].dayWorkouts[0].repsMax" type="number" class="form-control" placeholder="max" aria-label="reps_max" aria-describedby="addon-wrapping" required="required" min="1"/>
												</fieldset>
											</div>
										</div>
										<div id="newWorkoutContainer_day${i-1}"></div>

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
			<button type="button" id="addWorkout" class="btn btn-primary">Add Workout</button>

			<a class="btn btn-primary" href="?step=1" role="button">Back</a>
			<button type="submit" class="btn btn-success">Save new plan</button>
		</div>

	</form:form>

	<script type="text/javascript" async defer>
		$(document).ready(function() {
			var activeTab = 0;

			$('#myTab a').on('shown.bs.tab', function (e) {
				activeTab = $(e.target).attr('id').match(/\d+/)[0] - 1;
				//console.log('Active Tab ID:', activeTab);
			});

			$('#addWorkout').click(function() {
				// Get the value from the span element with ID 'totalWorkouts'
				var totalWorkoutsValue = document.getElementById('labelTotalWorkouts_' + activeTab).textContent;
				var totalWorkoutsNumber = parseInt(totalWorkoutsValue, 10);

				var i = activeTab;
				var j = totalWorkoutsNumber;

				var htmlCode = `
					<div class="row my-1" id="workoutContainer">
						<div class="col-6">
							<fieldset class="form-group">
								<label for="workoutDay` + activeTab + `_workout` + j + `">Workout #` + (j+1) + `</label>
								<select id="workoutDay` + activeTab + `_workout` + j + `" name="days[` + i + `].dayWorkouts[` + j + `].workout" rows="3" class="form-control" required="required"><option value="1">1 - Bench Press</option><option value="2">2 - Incline Dumbbell Bench Press</option><option value="3">3 - Chest Dips</option><option value="4">4 - Pull-up</option><option value="5">5 - Barbell Bent Over Row</option><option value="6">6 - Lat Pulldown</option></select>
							</fieldset>
						</div>
						<div class="col">
							<fieldset class="form-group">
								<label for="days` + activeTab + `.dayWorkouts` + j + `.sets">Sets</label>
								<input id="days` + activeTab + `.dayWorkouts` + j + `.sets" name="days[` + i + `].dayWorkouts[` + j + `].sets" type="number" class="form-control" required="required" value="3" min="1">
							</fieldset>
						</div>
						<div class="col">
							<fieldset class="form-group">
								<label for="days` + activeTab + `.dayWorkouts[` + j + `].repsMin">Reps min</label>
								<input id="days` + activeTab + `.dayWorkouts[` + j + `].repsMin" name="days[` + i + `].dayWorkouts[` + j + `].repsMin" type="number" class="form-control" placeholder="min" aria-label="reps_min" aria-describedby="addon-wrapping" required="required" value="12" min="1"/>
							</fieldset>
						</div>
						<div class="col">
							<fieldset class="form-group">
								<label for="days` + activeTab + `.dayWorkouts[` + j + `].repsMin">Reps min</label>
								<input id="days` + activeTab + `.dayWorkouts[` + j + `].repsMax" name="days[` + i + `].dayWorkouts[` + j + `].repsMax" type="number" class="form-control" placeholder="min" aria-label="reps_max" aria-describedby="addon-wrapping" required="required" value="12" min="1"/>
							</fieldset>
						</div>
						
					</div>
				`;

				// Increment the counter for the next row
				j++;

				// Append the new row to the container
				$('#newWorkoutContainer_day' + activeTab).append(htmlCode);
				$('#labelTotalWorkouts_' + activeTab).text(j);
				$('#totalWorkouts_' + activeTab).val(j);
			});
		});
		</script>

<%@ include file="../common/footer.jspf" %>