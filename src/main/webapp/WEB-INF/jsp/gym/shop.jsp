<%@ include file="../common/header.jspf" %>

	<c:if test="${error != null}">
		<div class="alert alert-danger" role="alert">${error}</div>
	</c:if>

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

	<header class="bg-dark py-5">
		<div class="container px-4 px-lg-5 my-5">
			<div class="text-center text-white">
				<h1 class="display-4 fw-bolder">Shop your workout plan</h1>
				<p class="lead fw-normal text-white-50 mb-0">Choose among thousands</p>
			</div>
		</div>
	</header>

	<!-- Section-->
	<section class="py-5">
		<div class="row justify-content-center">
			<c:forEach items="${plans}" var="plan">
				<div class="col mb-5">
					<div class="card h-100">
						<div class="badge bg-dark text-white position-absolute" style="top: 0.5rem; right: 0.5rem">Sale</div>
						<img class="card-img-top" src="https://dummyimage.com/450x300/dee2e6/6c757d.jpg" alt="..." />
						<div class="card-body p-4">
							<div class="row">
								<div class="col-8">
									<h5 class="fw-bolder">${plan.title}</h5>
									by <a href="/@${plan.user.username}">@${plan.user.username}</a>
								</div>
								<div class="col">
									<h5 class="fw-bolder">$${plan.price}</h5>
								</div>
							</div>
						</div>
						<!-- Product actions-->
						<div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
							<div class="row">
								<div class="col">
									<p>* * * * * (${plan.price * 10})</p>
									<p>${plan.price * 5} Sales</p>
								</div>
								<div class="col">
									<a class="btn btn-outline-dark mt-auto" href="/gym/shop/cart/add/${plan.id}"><i class="fa fa-cart-plus"></i></a>
									<a class="btn btn-outline-dark mt-auto" href="/gym/shop/product/plan/${plan.id}">View</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</section>

<%@ include file="../common/footer.jspf" %>