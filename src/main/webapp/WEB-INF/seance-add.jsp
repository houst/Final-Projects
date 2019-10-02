<jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
<%@taglib prefix="f" tagdir="/WEB-INF/tags/fragment" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fnl" uri="/WEB-INF/tags/list-func.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" />

<c:set var="locale" value="${sessionScope.locale}" />

<f:layout>
<jsp:attribute name="title"><fmt:message key="add"/> <fmt:message key="seance"/></jsp:attribute> 
<jsp:attribute name="content">

<div class="container">

	<div class="jumbotron mb-3">
			<h1><fmt:message key="add"/> <fmt:message key="seance"/></h1>
			<div class="col-md-6">
				<form action="/seance" id="add-seance-form" class="needs-validation" method="POST" novalidate>

					<div class="md-form">
						<i class="far fa-calendar-alt prefix"></i>
						<input type="text" name="date" id="date-picker-example" class="form-control datepicker" required>
						<label for="date-picker-example"><fmt:message key="date"/></label>
					</div>

					<div class="md-form">
						<i class="far fa-clock prefix"></i>
						<input name="time" type="text" id="input_starttime" class="form-control timepicker" required>
						<label for="input_starttime"><fmt:message key="time"/></label>
					</div>

					<select class="mdb-select md-form" searchable="<fmt:message key="search"/>" required>
						<option value="" disabled selected><fmt:message key="choose"/> <fmt:message key="movie"/></option>
						<c:forEach var="movie" items="${movies}">
							<option value="${movie.title}">${movie.title}</option>
						</c:forEach>
					</select>

					<div class="text-left form-sm mt-4">
						<button type="submit" class="btn btn-success waves-effect waves-light">
							<i class="fas fa-plus"></i>
							<fmt:message key="add"/>
						</button>
					</div>
				</form>
				</div>
				
			
		</div>
	</div>
</div>

</jsp:attribute>
</f:layout>