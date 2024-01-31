<%@ include file="../common/header.jspf" %>

	<c:if test="${error != null}">
		<div class="alert alert-danger" role="alert">${error}</div>
	</c:if>

	<c:if test="${flash != null}">
		<div class="alert alert-${flash.status} alert-dismissible fade show" role="alert">
			${flash.message}
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
	</c:if>

	<!-- Product section-->
	<section class="py-5">
		<div class="row">
			<div class="col-8">
				<div class="row gx-4 gx-lg-5 align-items-center">
					<img class="card-img-top my-2" src="https://placehold.co/800x200" alt="..." />

					<div class="col">
						<h1 class="display-5 fw-bolder">${plan.title} <span class="badge rounded-pill bg-info text-light">${plan.numberOfWeeks} weeks</span> <span class="badge rounded-pill bg-info text-light">${plan.numberOfDays} days</h1>
						<div class="fs-5 mb-5">
							<span>by <a href="/@${plan.user.username}">@${plan.user.username}</a></span>
						</div>
						<div>
							<p class="lead">${plan.gymPlanIntroduction.moreInfo}</p>
						</div>

						<div>
							<c:forEach items="${days}" var="day">
								<div class="my-2">
									<p><span class="badge rounded-pill bg-info text-light">Day ${day.dayNumber}</span> ${day.focus}</p>

									<c:forEach items="${day.dayWorkouts}" var="dw">
										<div class="row">
											<div class="col-1">
												<img src="/photo/gym/${dw.workout.photo}" class="d-block mx-lg-auto img-fluid" alt="${dw.workout.name}" width="40" height="40" loading="lazy"> 
											</div>
											<div class="col">
												<a href="/gym/workouts/${dw.workout.id}">${dw.workout.name}</a>
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
				<div class="row my-1">
					<div class="col">
						<div class="card">
							<div class="card-body">
								<h3>Price:  $${plan.price}</h3>
								<hr>
								<a class="btn btn-success btn-lg btn-block" href="/gym/shop/cart/add/${plan.id}"><i class="bi-cart-fill me-1"></i> Add to cart</i></a>
							</div>
						</div>
					</div>
				</div>
				<div class="row my-1">
					<div class="col">
						<div class="card">
							<div class="card-body">
								<h4>Plan details</h4>
								<hr>
								<div>
									<p>Main Goal: ${plan.gymPlanIntroduction.mainGoal}</p>
									<p>Workout Type: ${plan.gymPlanIntroduction.workoutType}</p>
									<p>Training Level: ${plan.gymPlanIntroduction.trainingLevel}</p>
									<p>Program Duration: ${plan.numberOfWeeks} weeks</p>
									<p>Days per Week: ${plan.numberOfDays} days</p>
									<p>Time per Workout: ${plan.gymPlanIntroduction.timePerWorkout}</p>
									<p>Equipment Required: ###</p>
									<c:choose>
										<c:when test="${plan.gymPlanIntroduction.targetGender == 0}">
											<p>Target Gender: Men</p>
										</c:when>
										<c:otherwise>
											<p>Target Gender: Women</p>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
			
	</section>
	<!-- Related items section-->
	<section class="py-5 bg-light">
		<div class="container px-4 px-lg-5 mt-5">
			<h2 class="fw-bolder mb-4">Related products</h2>
			<div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
				<div class="col mb-5">
					<div class="card h-100">
						<!-- Product image-->
						<img class="card-img-top" src="https://dummyimage.com/450x300/dee2e6/6c757d.jpg" alt="..." />
						<!-- Product details-->
						<div class="card-body p-4">
							<div class="text-center">
								<!-- Product name-->
								<h5 class="fw-bolder">Fancy Product</h5>
								<!-- Product price-->
								$40.00 - $80.00
							</div>
						</div>
						<!-- Product actions-->
						<div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
							<div class="text-center"><a class="btn btn-outline-dark mt-auto" href="#">View options</a></div>
						</div>
					</div>
				</div>
				<div class="col mb-5">
					<div class="card h-100">
						<!-- Sale badge-->
						<div class="badge bg-dark text-white position-absolute" style="top: 0.5rem; right: 0.5rem">Sale</div>
						<!-- Product image-->
						<img class="card-img-top" src="https://dummyimage.com/450x300/dee2e6/6c757d.jpg" alt="..." />
						<!-- Product details-->
						<div class="card-body p-4">
							<div class="text-center">
								<!-- Product name-->
								<h5 class="fw-bolder">Special Item</h5>
								<!-- Product reviews-->
								<div class="d-flex justify-content-center small text-warning mb-2">
									<div class="bi-star-fill"></div>
									<div class="bi-star-fill"></div>
									<div class="bi-star-fill"></div>
									<div class="bi-star-fill"></div>
									<div class="bi-star-fill"></div>
								</div>
								<!-- Product price-->
								<span class="text-muted text-decoration-line-through">$20.00</span>
								$18.00
							</div>
						</div>
						<!-- Product actions-->
						<div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
							<div class="text-center"><a class="btn btn-outline-dark mt-auto" href="#">Add to cart</a></div>
						</div>
					</div>
				</div>
				<div class="col mb-5">
					<div class="card h-100">
						<!-- Sale badge-->
						<div class="badge bg-dark text-white position-absolute" style="top: 0.5rem; right: 0.5rem">Sale</div>
						<!-- Product image-->
						<img class="card-img-top" src="https://dummyimage.com/450x300/dee2e6/6c757d.jpg" alt="..." />
						<!-- Product details-->
						<div class="card-body p-4">
							<div class="text-center">
								<!-- Product name-->
								<h5 class="fw-bolder">Sale Item</h5>
								<!-- Product price-->
								<span class="text-muted text-decoration-line-through">$50.00</span>
								$25.00
							</div>
						</div>
						<!-- Product actions-->
						<div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
							<div class="text-center"><a class="btn btn-outline-dark mt-auto" href="#">Add to cart</a></div>
						</div>
					</div>
				</div>
				<div class="col mb-5">
					<div class="card h-100">
						<!-- Product image-->
						<img class="card-img-top" src="https://dummyimage.com/450x300/dee2e6/6c757d.jpg" alt="..." />
						<!-- Product details-->
						<div class="card-body p-4">
							<div class="text-center">
								<!-- Product name-->
								<h5 class="fw-bolder">Popular Item</h5>
								<!-- Product reviews-->
								<div class="d-flex justify-content-center small text-warning mb-2">
									<div class="bi-star-fill"></div>
									<div class="bi-star-fill"></div>
									<div class="bi-star-fill"></div>
									<div class="bi-star-fill"></div>
									<div class="bi-star-fill"></div>
								</div>
								<!-- Product price-->
								$40.00
							</div>
						</div>
						<!-- Product actions-->
						<div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
							<div class="text-center"><a class="btn btn-outline-dark mt-auto" href="#">Add to cart</a></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

<%@ include file="../common/footer.jspf" %>