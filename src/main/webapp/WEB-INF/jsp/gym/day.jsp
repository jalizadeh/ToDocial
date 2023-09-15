<%@ include file="../common/header.jspf" %>


	<div class="row mt-2">
		<div class="col border-bottom">
			<h2><a href="/gym/plan/${plan.id}">${plan.title} [${plan.numberOfWeeks}W / ${plan.numberOfDays}D]</a> > Week #${week}</a> > Day #${day.dayNumber}</h2>
			<h3>Focus: ${day.focus}</h3>
			<p>
				<div class="progress">
					<div class="progress-bar" role="progressbar" style="width: ${day.progress}%;" 
						aria-valuenow="${day.progress}" aria-valuemin="0" aria-valuemax="100">${day.progress}%</div>
				</div>
			</p>
		</div>
	</div>

	<div class="row">
		<c:forEach items="${workouts}" var="workout">
			<div class="col-12 p-2">
				<div class="card mb-3">
					<div class="row g-0">
						<div class="col-md-4">
							<img src="/resources/img/gym/default-workout.jpg" class="img-fluid rounded-start" alt="${workout.name}">
						</div>
						<div class="col-4">
							<div class="card-body">
								<h5 class="card-title">${workout.name}</h5>
								<div class="row">
									<div class="col-3"><p class="card-text">Sets:</p></div>
									<div class="col"><p class="card-text">${workout.sets}</p></div>
								</div>
								<div class="row">
									<div class="col-3"><p class="card-text">Reps:</p></div>
									<div class="col"><p class="card-text">${workout.repsMin}-${workout.repsMax} reps</p></div>
								</div>
								<div class="row">
									<div class="col-3"><p class="card-text">Rest:</p></div>
									<div class="col"><p class="card-text">${workout.restMin}-${workout.restMax} sec</p></div>
								</div>
							</div>
						</div>
						<div class="col-4">
							<div class="card-body">
								<h5 class="card-title">Workout Records</h5>
								<div class="row">
									<div class="col-3"><p class="card-text">Set 1:</p></div>
									<div class="col"><p class="card-text">10 kg - 8 reps</p></div>
								</div>
								<div class="row">
									<div class="col-3"><p class="card-text">Set 2:</p></div>
									<div class="col"><p class="card-text">10 kg - 10 reps</p></div>
								</div>
								<div class="row">
									<div class="col-3"><p class="card-text">Set 3:</p></div>
									<div class="col"><p class="card-text">10 kg - 12 reps</p></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>



<%@ include file="../common/footer.jspf" %>