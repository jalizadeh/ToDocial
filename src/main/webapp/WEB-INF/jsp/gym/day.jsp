<%@ include file="../common/header.jspf" %>


	<div class="row mt-2">
		<div class="col border-bottom">
			<h2><a href="/gym/plan/${plan.id}">${plan.title} [${plan.numberOfWeeks} weeks / ${plan.numberOfDays} days]</a></h2>
			
			<div class="row">
				<div class="col-6">
					<h3>Focus<br>${day.focus}</h3>
				</div>
				<div class="col">
					<h3>Week<br>#${week}</h3>
				</div>
				<div class="col">
					<h3>Day<br>#${day.dayNumber}</h3>
				</div>
				<div class="col">
					<h3>Date<br>${pwd.workoutDate}</h3>
				</div>
			</div>
			
			<p>
				<div class="progress">
					<div class="progress-bar" role="progressbar" style="width: ${pwd.progress}%;" 
						aria-valuenow="${pwd.progress}" aria-valuemin="0" aria-valuemax="100">${pwd.progress}%</div>
				</div>
			</p>
		</div>
	</div>

	<div class="row">
		<c:forEach items="${dayWorkouts}" var="dayWorkout">
			<div class="col-12 p-2">
				<div class="card mb-3">
					<div class="row g-0">
						<div class="col-md-4">
							<a href="/gym/workouts/${dayWorkout.workout.id}"><img src="/photo/gym/${dayWorkout.workout.photo}" class="mx-auto d-block" alt="${dayWorkout.workout.name}" style="max-width: 30%;"></a>
						</div>
						<div class="col-4">
							<div class="card-body">
								<h5 class="card-title">${dayWorkout.workout.name}</h5>
								<div class="row">
									<div class="col-3"><p class="card-text">Sets:</p></div>
									<div class="col"><p class="card-text">${dayWorkout.sets}</p></div>
								</div>
								<div class="row">
									<div class="col-3"><p class="card-text">Reps:</p></div>
									<div class="col"><p class="card-text">${dayWorkout.repsMin}-${dayWorkout.repsMax} reps</p></div>
								</div>
								
							</div>
						</div>
						<div class="col-4">
							<div class="card-body">
								<c:forEach items="${dayWorkout.workoutLogs}" var="wlog" varStatus="loop">
									<c:if test="${wlog.pwd.id == pwdId}">
										<div class="row" id="dayWorkout-${dayWorkout.id}">
											<div class="col-3"><p class="card-text">Set ${wlog.setNumber}:</p></div>
											<div class="col"><p class="card-text">${wlog.weight} kg - ${wlog.reps} reps</p></div>
										</div>
									</c:if>
								</c:forEach>
								<div id="newLogPlaceholder${dayWorkout.id}"></div>
								<button type="button" id="addSingleWorkoutLog${dayWorkout.id}" class="btn btn-primary" onclick="addSingleWorkoutLog(${day.dayNumber},'${dayWorkout}', '${dayWorkout.id}')">Add set</button>
								or
								<button type="button" id="addWorkoutLogByNote${dayWorkout.id}" class="btn btn-primary" onclick="addWorkoutLogByNote(${day.dayNumber},'${dayWorkout}', '${dayWorkout.id}')">Add by log note</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>


	<script>
		function addSingleWorkoutLog(dayNumber, workout, workoutId) {
			//find the total number of sets logged for that workout, so the next will be +1
			var lastSetNumber = document.querySelectorAll('[id*="dayWorkout-' + workoutId + '"]').length;

			// Create the dynamic form elements
			let form = document.createElement('form');
            form.id = 'workoutLog';
            form.action = dayNumber + '/workout/' + workoutId + '/add-single-workout-log';
            form.method = 'post';

			let setInput = document.createElement('input');
            setInput.id = 'setNumber';
            setInput.name = 'setNumber';
            setInput.type = 'number';
			setInput.value = lastSetNumber + 1;
            setInput.className = 'form-control';
            setInput.required = true;

			let weightInput = document.createElement('input');
            weightInput.id = 'weight';
            weightInput.name = 'weight';
            weightInput.type = 'text';
            weightInput.className = 'form-control';
			weightInput.placeholder = 'KG';
            weightInput.required = true;

			let repsInput = document.createElement('input');
            repsInput.id = 'reps';
            repsInput.name = 'reps';
            repsInput.type = 'text';
            repsInput.className = 'form-control';
			repsInput.placeholder = 'Reps';
            repsInput.required = true;
			
            let input1 = document.createElement('input');
            let input2 = document.createElement('input');
            let cancelButton = document.createElement('button');
            let saveButton = document.createElement('button');

			// Create the submit button
            let submitButton = document.createElement('button');
            submitButton.type = 'submit';
            submitButton.textContent = 'Save';

            // Set attributes and values
            cancelButton.textContent = 'Cancel';
            saveButton.textContent = 'Save';

            // Add event listeners to buttons
            cancelButton.addEventListener('click', function() {
                form.remove();
				$('#addSingleWorkoutLog'+workoutId).show();
				$('#addWorkoutLogByNote'+workoutId).show();
            });
            saveButton.addEventListener('click', function() {
                // Add your save logic here
                form.remove();
            });

            // Append elements to the form
			form.appendChild(setInput);
			form.appendChild(weightInput);
            form.appendChild(repsInput);
            form.appendChild(document.createElement('br'));
            form.appendChild(cancelButton);
            form.appendChild(submitButton);

            // Append the form to the table
            document.getElementById('newLogPlaceholder' + workoutId).appendChild(form);
			$('#addSingleWorkoutLog' + workoutId).hide();
			$('#addWorkoutLogByNote' + workoutId).hide();
        }


		function addWorkoutLogByNote(dayNumber, workout, workoutId) {
			//find the total number of sets logged for that workout, so the next will be +1
			var lastSetNumber = document.querySelectorAll('[id*="dayWorkout-' + workoutId + '"]').length;

			// Create the dynamic form elements
			let form = document.createElement('form');
            form.id = 'workoutLog';
            form.action = dayNumber + '/workout/' + workoutId + '/add-workout-log-note';
            form.method = 'post';

			let noteInput = document.createElement('input');
            noteInput.id = 'lognote';
            noteInput.name = 'lognote';
            noteInput.type = 'text';
			noteInput.value = "";
            noteInput.className = 'form-control';
            noteInput.required = true;

			
			// Create the submit button
            let submitButton = document.createElement('button');
            submitButton.type = 'submit';
            submitButton.textContent = 'Save';
			
            // Set attributes and values
            let cancelButton = document.createElement('button');
            cancelButton.textContent = 'Cancel';
            let saveButton = document.createElement('button');
            saveButton.textContent = 'Save';

            // Add event listeners to buttons
            cancelButton.addEventListener('click', function() {
                form.remove();
				$('#addWorkoutLogByNote' + workoutId).show();
				$('#addSingleWorkoutLog' + workoutId).show();
            });
            saveButton.addEventListener('click', function() {
                // Add your save logic here
                form.remove();
            });

            // Append elements to the form
			form.appendChild(noteInput);
            form.appendChild(document.createElement('br'));
            form.appendChild(cancelButton);
            form.appendChild(submitButton);

            // Append the form to the table
            document.getElementById('newLogPlaceholder' + workoutId).appendChild(form);
			$('#addSingleWorkoutLog' + workoutId).hide();
			$('#addWorkoutLogByNote' + workoutId).hide();
        }


		
	</script>


<%@ include file="../common/footer.jspf" %>