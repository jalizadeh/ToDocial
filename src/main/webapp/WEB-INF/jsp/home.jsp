<%@ include file="common/header.jspf" %>

	<div class="row">
		<div class="col-3 mx-2">
			<div class="row border-bottom">
				<div class="col-7">
					<p>Community</p>
				</div>
			</div>

			<c:forEach items="${users}" var="user">
				<div class="my-2">
					<a href="/@${user.username}">
						<img class="img-profile rounded-circle" width="32px" height="32px" src="/photo/user/${user.photo}">
					</a>
					<a href="/@${user.username}">${user.firstname} ${user.lastname}</a>
				</div>
			</c:forEach>
		</div>

		<div class="col mx-2">
			<div class="row border-bottom">
				<div class="col-7">
					<p>Recent activities</p>
				</div>
			</div>

			<c:forEach items="${todos}" var="todo">
				<div class="my-2">
					<c:choose>
						<c:when test="${todo.completed == true}">
							<p>
								<i class="fas fa-clipboard-check text-success"></i> <a
									href="/todo?id=${todo.id}">${todo.name}</a> by @${todo.user.username}
							</p>
						</c:when>
						<c:otherwise>
							<p>
								<i class="fas fa-clipboard text-warning"></i> <a
									href="/todo?id=${todo.id}">${todo.name}</a> by @${todo.user.username}
							</p>
						</c:otherwise>
					</c:choose>
				</div>
			</c:forEach>
		</div>
	</div>

<%@ include file="common/footer.jspf" %>