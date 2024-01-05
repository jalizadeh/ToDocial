<%@ include file="../common/header.jspf" %>

		<%@ include file="common/result.jspf" %>

		<table class="table table-striped table-hover mt-3">
			<thead>
				<tr>
					<th>Todo</th>
					<th>Creation Date</th>
					<th>Target Date</th>
					<th>Completed?</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${items}" var="todo">
					<tr>
						<th>
							<p><a href="/todo?id=${todo.id}">${todo.name}</a></p>
							<p>Description: ${todo.description}</p>
							<p>Reason: ${todo.reason}</p>
						</th>
						<th>
							<c:choose>
								<c:when test="${settings.dateStructure == 'pattern'}">
									<fmt:formatDate value="${todo.creation_date}"
										pattern="yyyy/MM/dd" />
								</c:when>
								<c:when test="${settings.dateStructure == 'short'}">
									<fmt:formatDate type="date" dateStyle="short"
										value="${todo.creation_date}" />
								</c:when>
								<c:when test="${settings.dateStructure == 'medium'}">
									<fmt:formatDate type="date" dateStyle="medium"
										value="${todo.creation_date}" />
								</c:when>
								<c:when test="${settings.dateStructure == 'long'}">
									<fmt:formatDate type="date" dateStyle="long"
										value="${todo.creation_date}" />
								</c:when>
							</c:choose>
						</th>
						<th>
							<c:choose>
								<c:when test="${settings.dateStructure == 'pattern'}">
									<fmt:formatDate value="${todo.target_date}"
										pattern="yyyy/MM/dd" />
								</c:when>
								<c:when test="${settings.dateStructure == 'short'}">
									<fmt:formatDate type="date" dateStyle="short"
										value="${todo.target_date}" />
								</c:when>
								<c:when test="${settings.dateStructure == 'medium'}">
									<fmt:formatDate type="date" dateStyle="medium"
										value="${todo.target_date}" />
								</c:when>
								<c:when test="${settings.dateStructure == 'long'}">
									<fmt:formatDate type="date" dateStyle="long"
										value="${todo.target_date}" />
								</c:when>
							</c:choose>
						</th>
						<th>${todo.completed}</th>
					</tr>
				</c:forEach>
			</tbody>
		</table>

<%@ include file="../common/footer.jspf" %>