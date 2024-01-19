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

		<section class="h-100 h-custom" style="background-color: #eee;">
			<div class="container py-5 h-100">
			  <div class="row d-flex justify-content-center align-items-center h-100">
				<div class="col">
				  <div class="card">
					<div class="card-body p-4">
		  
					  <div class="row">
		  
						<div class="col-lg-7">
						  <h5 class="mb-3"><a href="/gym/shop" class="text-body"><i class="fas fa-long-arrow-alt-left me-2"></i>Continue shopping</a></h5>
						  <hr>
		  
						  <div class="d-flex justify-content-between align-items-center mb-4">
							<div>
							  <p class="mb-1">Shopping cart</p>
							  <p class="mb-0">You have ${items.size()} items in your cart</p>
							</div>
							<div>
							  <p class="mb-0"><span class="text-muted">Sort by:</span> <a href="#!"
								  class="text-body">price <i class="fas fa-angle-down mt-1"></i></a></p>
							</div>
						  </div>
		  
						  <c:forEach items="${items}" var="item">
							<div class="card mb-3">
								<div class="card-body">
								  <div class="d-flex justify-content-between">
									<div class="d-flex flex-row align-items-center">
									  <div>
										<img src="https://placehold.co/65" class="img-fluid rounded-3" alt="Shopping item" style="width: 65px;">
									  </div>
									  <div class="ms-3">
										<h5>${item.title}</h5>
										<p class="small mb-0">by @${item.user.username}</p>
									  </div>
									</div>
									<div class="d-flex flex-row align-items-center">
									  <div style="width: 80px;">
										<h5 class="mb-0">$${item.price}</h5>
									  </div>
									  <a href="cart/remove/${item.id}" style="color: #cecece;"><i class="fas fa-trash-alt"></i></a>
									</div>
								  </div>
								</div>
							  </div>
						  </c:forEach>
						</div>
						<div class="col-lg-5">
		  
						  <div class="card bg-primary text-white rounded-3">
							<div class="card-body">
							  <div class="d-flex justify-content-between align-items-center mb-4">
								<h5 class="mb-0">Card details</h5>
							  </div>
		  
							  <p class="small mb-2">Card type</p>
							  <a href="#!" type="submit" class="text-white"><i
								  class="fab fa-cc-mastercard fa-2x me-2"></i></a>
							  <a href="#!" type="submit" class="text-white"><i
								  class="fab fa-cc-visa fa-2x me-2"></i></a>
							  <a href="#!" type="submit" class="text-white"><i
								  class="fab fa-cc-amex fa-2x me-2"></i></a>
							  <a href="#!" type="submit" class="text-white"><i class="fab fa-cc-paypal fa-2x"></i></a>
		  
							  <form class="mt-4">
								<div class="form-outline form-white mb-4">
								  <input type="text" id="typeName" class="form-control form-control-lg" siez="17"
									placeholder="Cardholder's Name" />
								  <label class="form-label" for="typeName">Cardholder's Name</label>
								</div>
		  
								<div class="form-outline form-white mb-4">
								  <input type="text" id="typeText" class="form-control form-control-lg" siez="17"
									placeholder="1234 5678 9012 3457" minlength="19" maxlength="19" />
								  <label class="form-label" for="typeText">Card Number</label>
								</div>
		  
								<div class="row mb-4">
								  <div class="col-md-6">
									<div class="form-outline form-white">
									  <input type="text" id="typeExp" class="form-control form-control-lg"
										placeholder="MM/YYYY" size="7" id="exp" minlength="7" maxlength="7" />
									  <label class="form-label" for="typeExp">Expiration</label>
									</div>
								  </div>
								  <div class="col-md-6">
									<div class="form-outline form-white">
									  <input type="password" id="typeText" class="form-control form-control-lg"
										placeholder="&#9679;&#9679;&#9679;" size="1" minlength="3" maxlength="3" />
									  <label class="form-label" for="typeText">Cvv</label>
									</div>
								  </div>
								</div>
		  
							  </form>
		  
							  <hr class="my-4">
		  
							  <div class="d-flex justify-content-between">
								<p class="mb-2">Subtotal</p>
								<p class="mb-2">$${total}</p>
							  </div>
		  
							  <div class="d-flex justify-content-between mb-4">
								<p class="mb-2">Total(Incl. taxes)</p>
								<p class="mb-2">$${total}</p>
							  </div>
		  
							  <button type="button" class="btn btn-info btn-block btn-lg">
								<div class="d-flex justify-content-between">
								  <span>$${total}</span>
								  <span>Checkout <i class="fas fa-long-arrow-alt-right ms-2"></i></span>
								</div>
							  </button>
		  
							</div>
						  </div>
		  
						</div>
		  
					  </div>
		  
					</div>
				  </div>
				</div>
			  </div>
			</div>
		  </section>

<%@ include file="../common/footer.jspf" %>