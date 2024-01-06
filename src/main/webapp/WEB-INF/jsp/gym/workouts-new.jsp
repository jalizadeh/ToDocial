<%@ include file="../common/header.jspf"%>

<!--<h4 class="display-4">${PageTitle}</h4>-->

<c:if test="${error != null}">
	<div class="alert alert-danger" role="alert">${error}</div>
</c:if>

<h4 class="display-4">Add new workout</h4>

<form:form method="post" modelAttribute="workout" enctype="multipart/form-data">
	<div class="row">
		<div class="col-8">
			<div class="card">
				<div class="card-body">
						<fieldset class="form-group">
							<form:label path="name">Name</form:label>
							<form:input path="name" type="text" class="form-control" required="required" />
							<form:errors path="name" cssClass="text-warning" />
						</fieldset>

						<fieldset class="form-group">
							<label for="description">Description</label>
							<form:textarea path="description" class="form-control" id="description" rows="3" required="required"></form:textarea>
							<form:errors path="description" cssClass="text-warning" />
						</fieldset>

						<div class="row border-bottom">
							<div class="col">
								<fieldset class="form-group">
									<label>Muscle Category</label>
									<c:forEach items="${muscleCategories}" var="mc" varStatus="i">
										<div class="custom-control custom-checkbox mx-2">
											<input id="mc-${mc}" name="muscleCategory" class="custom-control-input" type="checkbox" value="${mc}"/>
											<label class="custom-control-label" for="mc-${mc}">${mc}</label>
										</div>
									</c:forEach>
								</fieldset>
							</div>
							<div class="col">
								<fieldset class="form-group">
									<label>Equipments</label>
									<c:forEach items="${equipments}" var="equip" varStatus="i">
										<div class="custom-control custom-checkbox mx-2">
											<input id="equipment-${equip}" name="equipment" class="custom-control-input" type="checkbox" value="${equip}"/>
											<label class="custom-control-label" for="equipment-${equip}">${equip}</label>
										</div>
									</c:forEach>
								</fieldset>
							</div>
						</div>

						
				</div>
			</div>
		</div>

		<div class="col">
			<div class="form-group">
				<label for="photo">Workout photo</label>
				<input type="file" name="file" class="form-control-file" id="photo">
			</div>
		</div>

		<div class="m-2">
			<a class="btn btn-primary" href="/gym/workouts" role="button">Cancel</a>
			<button type="submit" class="btn btn-success">Save Changes</button>
		</div>
	</div>
</form:form>


<%@ include file="../common/footer.jspf"%>