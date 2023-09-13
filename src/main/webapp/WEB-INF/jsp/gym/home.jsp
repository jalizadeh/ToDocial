<%@ include file="../common/header.jspf" %>

	<div class="row">
		<div class="col-3">
			<p>Blood</p>
			<p>Stretching</p>
			<p>Vitamines & Minerals</p>
			<p>FAQ</p>
			<p>Mr. Olympia</p>
		</div>

		<div class="col-3">
			<p>Food Diary</p>
			<p>Yoga</p>
			<p>Best Foods</p>
			<p>Selection</p>
			<p>Quotes</p>
		</div>

		<div class="col-3">
			<p>Diet & Recipes</p>
			<p>Challenges</p>
			<p>Supplements Guide</p>
			<p>Equipments</p>
			<p>Terms</p>
		</div>

		<div class="col-3">
			<p>Notes</p>
			<p>Height Increase</p>
			<p>General Health Tips</p>
			<p>BMI</p>
			<p>BMR</p>
		</div>
	</div>

	<div class="row mt-2">
		<div class="col border-bottom">
			<h2>Your Active Plans</h2>
		</div>
	</div>

	<div class="row">
		<c:forEach items="${plans}" var="plan">
			<div class="card p-2" >
				<div class="card-body">
				  <h5 class="card-title">${plan.title}</h5>
				  <h6 class="card-subtitle mb-2 text-muted">${plan.gymPlanIntroduction.trainingLevel} - ${plan.numberOfWeeks} weeks</h6>
				  <p class="card-text">${fn:substring(plan.gymPlanIntroduction.moreInfo, 0, 45)}...</p>
				  <a href="/gym/plan?id=${plan.id}" class="card-link">View details</a>
				  <a href="/gym/plan?edit=${plan.id}" class="card-link">Edit plan</a>
				</div>
			  </div>
		</c:forEach>
	</div>

	<div class="row mt-2">
		<div class="col border-bottom">
			New To The Gym?
		</div>
	</div>

	<div class="row mt-2">
		<div class="col border-bottom">
			Beginner
			<br/>
			Intermediate
			<br/>
			Advanced
		</div>
	</div>

	<div class="row mt-2">
		<div class="col border-bottom">
			Days per week
			<br/>
			2 Days - 3 - 4 - 5 - 6
		</div>
	</div>

	<div class="row mt-2">
		<div class="col border-bottom">
			Muscles Emphasized
			<br/>
			ABS - ARMS - CHEST - SHOULDER - LEG - BACK
		</div>
	</div>

	<div class="row mt-2">
		<div class="col border-bottom">
			By Goal
			<br/>
			LOSE WEIGHT - Fitness - Mass Gain - Build Muscle - Power Lifting 
		</div>
	</div>

	<div class="row mt-2">
		<div class="col border-bottom">
			Main Category
		</div>
	</div>

	<div class="row mt-2">
		<div class="col border-bottom">
			<button>Find Workout plan</button>
		</div>
	</div>


<%@ include file="../common/footer.jspf" %>