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
<jsp:attribute name="title"><fmt:message key="add"/> <fmt:message key="movie"/></jsp:attribute> 
<jsp:attribute name="content">

<div class="container">

	<div class="jumbotron mb-3">
			<h1><fmt:message key="add"/> <fmt:message key="movie"/></h1>
			<div class="col-md-6">
				<form action="/movie" id="add-movie-form" class="needs-validation" method="POST" novalidate>

					<div class="md-form form-sm">
						<i class="fas fa-film prefix"></i>
						<input type="text" name="title" class="form-control form-control-sm" required>
						<label for="title"><fmt:message key="movie.title"/></label>
					</div>

					<div class="md-form form-sm">
						<i class="far fa-clock prefix"></i>
						<input type="number" name="duration" class="form-control form-control-sm" required>
						<label for="duration"><fmt:message key="movie.duration"/></label>
					</div>

					<div class="md-form form-sm">
						<i class="fas fa-phone prefix"></i>
						<input type="text" name="description" class="form-control form-control-sm" required>
						<label for="description"><fmt:message key="movie.description"/></label>
					</div>

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

</jsp:attribute>
</f:layout>