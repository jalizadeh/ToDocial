<%@ include file="common/header.jspf"%>

<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal.username" var="loggedinUsername"/>
</sec:authorize>

<sec:authorize access="isAnonymous()">
	<div class="alert alert-light alert-dismissible fade show my-2"
		role="alert">
		<card class="card text-center">
		<div class="card-body">
			<h4>Create your own jTodo profile</h4>
			<p>Sign up for your own profile on jTodo, a social media for
				sharing and enjoying todos</p>
			<a class="btn btn-outline-success" href="/signup" role="button">Sign
				up</a>
		</div>
		<button type="button" class="close" data-dismiss="alert"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		</card>

	</div>
</sec:authorize>


<div class="row my-5">
	<div class="col">
		<div class="border-dark">
			<img class="img-profile rounded-circle" width="200px" height="200px" src="/user-avatar/${user.photo}">
			<h3 class="mt-2">${user.firstname} ${user.lastname}</h3>
			<h4 class="text-secondary">${user.username}</h4>
			<sec:authorize access="isAuthenticated()">
				<c:if test="${isfollowing eq false}">
					<a class="btn btn-primary"
						href="/users/follow?target=${user.username}" role="button">Follow</a>
				</c:if>
				<c:if test="${isfollowing eq true}">
					<a class="btn btn-primary"
						href="/users/unfollow?target=${user.username}" role="button">Unfollow</a>
				</c:if>
			</sec:authorize>
		</div>
	</div>



	<!-- todos -->
	<!-- ///// -->
	<div class="col-9">

		<ul class="nav nav-tabs" id="myTab" role="tablist">
			<li class="nav-item "><a class="nav-link active" id="todos-tab"
				data-toggle="tab" href="#todos" role="tab" aria-controls="todos"
				aria-selected="false">Todos <span
					class="badge badge-pill badge-secondary">${todos.size()}</span></a></li>
			<li class="nav-item"><a class="nav-link" id="likes-tab"
				data-toggle="tab" href="#likes" role="tab" aria-controls="likes"
				aria-selected="false">Likes <span
					class="badge badge-pill badge-secondary">0</span></a></li>
			<li class="nav-item"><a class="nav-link" id="followers-tab"
				data-toggle="tab" href="#followers" role="tab"
				aria-controls="followers" aria-selected="false">Followers <span
					class="badge badge-pill badge-secondary">${user.followers.size()}</span></a></li>
			<li class="nav-item"><a class="nav-link" id="following-tab"
				data-toggle="tab" href="#following" role="tab"
				aria-controls="following" aria-selected="false">Following <span
					class="badge badge-pill badge-secondary">${user.followings.size()}</span></a></li>
		</ul>
		<div class="tab-content mt-2" id="myTabContent">
			<div class="tab-pane fade show active" id="todos" role="tabpanel"
				aria-labelledby="todos-tab">
				<div class="row">

					<!-- Left column -->
					<div class="col">
						<c:forEach items="${todos}" varStatus="j">
							<c:if test="${(j.index mod 2) eq 0}">
								<div class="card mt-3">
									<div class="card-body">
										<c:choose>
											<c:when test="${todos[j.index].completed == true}">
												<i class="fas fa-clipboard-check text-success"></i>
												<a href="#"> ${todos[j.index].name}</a>

												<div class="row mt-3">
													<div class="col">
														<!-- date -->
														<i class="fas fa-calendar-alt"></i>
														<fmt:formatDate value="${todos[j.index].target_date}"
															pattern="yyyy/MM/dd" />
													</div>
													<div class="col">
														<!-- likes -->
														<i class="fas fa-star"></i> ${todos[j.index].like}
													</div>
												</div>
											</c:when>
											<c:otherwise>
												<i class="fas fa-clipboard text-warning"></i>
												<a href="#"> ${todos[j.index].name}</a>

												<div class="row mt-3">
													<div class="col">
														<!-- date -->
														<i class="fas fa-calendar-alt"></i>
														<fmt:formatDate value="${todos[j.index].target_date}"
															pattern="yyyy/MM/dd" />
													</div>
													<div class="col">
														<!-- likes -->
														<i class="fas fa-star"></i> ${todos[j.index].like}
													</div>
												</div>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</c:if>
						</c:forEach>
					</div>

					<!-- Right column -->
					<div class="col">
						<c:forEach items="${todos}" varStatus="j">
							<c:if test="${(j.index mod 2) ne 0}">
								<div class="card mt-3">
									<div class="card-body">
										<c:choose>
											<c:when test="${todos[j.index].completed == true}">
												<i class="fas fa-clipboard-check text-success"></i>
												<a href="#"> ${todos[j.index].name}</a>

												<div class="row mt-3">
													<div class="col">
														<!-- date -->
														<i class="fas fa-calendar-alt"></i>
														<fmt:formatDate value="${todos[j.index].target_date}"
															pattern="yyyy/MM/dd" />
													</div>
													<div class="col">
														<!-- likes -->
														<i class="fas fa-star"></i> ${todos[j.index].like}
													</div>
												</div>
											</c:when>
											<c:otherwise>
												<i class="fas fa-clipboard text-warning"></i>
												<a href="#"> ${todos[j.index].name}</a>

												<div class="row mt-3">
													<div class="col">
														<!-- date -->
														<i class="fas fa-calendar-alt"></i>
														<fmt:formatDate value="${todos[j.index].target_date}"
															pattern="yyyy/MM/dd" />
													</div>
													<div class="col">
														<!-- likes -->
														<i class="fas fa-star"></i> ${todos[j.index].like}
													</div>
												</div>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</c:if>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="tab-pane fade" id="likes" role="tabpanel"
				aria-labelledby="followers-tab"></div>
			<div class="tab-pane fade" id="followers" role="tabpanel"
				aria-labelledby="followers-tab">
				<c:forEach items="${user.followers}" var="follower">
					<div class="card mt-3">
						<div class="card-body">
							<div class="row">
								<div class="col">
									<a href="/@${follower.username}">
									<img class="img-profile rounded-circle" width="32px" height="32px" src="/user-avatar/${follower.photo}">
									</a> <a
										href="/@${follower.username}">${follower.firstname}
										${follower.lastname}</a>
								</div>
								<div class="col-3">
									<sec:authorize access="isAuthenticated()">
										<c:if test="${loggedinUsername ne follower.username }">
											<a class="btn btn-primary"
												href="/users/follow?target=${follower.username}"
												role="button">Follow</a>
										</c:if>
									</sec:authorize>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>

			<div class="tab-pane fade" id="following" role="tabpanel"
				aria-labelledby="following-tab">
				<c:forEach items="${user.followings}" var="following">
					<div class="card mt-3">
						<div class="card-body">
							<div class="row">
								<div class="col">
									<a href="/@${following.username}">
									<img class="img-profile rounded-circle" width="32px" height="32px" src="/user-avatar/${following.photo}">
									</a> <a
										href="/@${following.username}">${following.firstname} 
										${following.lastname}</a>
								</div>
								<div class="col-3">
									<sec:authorize access="isAuthenticated()">
										<c:if test="${loggedinUsername ne following.username }">
											<a class="btn btn-primary"
												href="/users/unfollow?target=${following.username}"
												role="button">Unfollow</a>
										</c:if>
									</sec:authorize>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>

<%@ include file="common/footer.jspf"%>