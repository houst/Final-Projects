<jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
<%@taglib prefix="f" tagdir="/WEB-INF/tags/fragment" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fnl" uri="/WEB-INF/tags/list-func.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" />

<c:set var="locale" value="${sessionScope.locale}" />

<c:set var="isAuthenticated" value="${sessionScope['isAuthenticated'] == true}" />
<c:set var="isAnonymous" value="${!sessionScope['isAuthenticated'] == true}" />

<f:layout>
<jsp:attribute name="title"><fmt:message key="seance"/></jsp:attribute> 
<jsp:attribute name="content">
	
	<c:forEach items="${movies.keySet()}" var="movie">
	<section class="movie-item container">
		
		<div class="jumbotron mb-3">
			<div class="row">
				<div class="col-md-4">
					<img src="/img/no-image.jpg" class="main-poster">
				</div>
				<div class="col-md-8">
					<h2 class="mb-3">${movie.title}</h2>
					<c:forEach items="${movies.get(movie).keySet()}" var="keyDate">
					<div>
						<strong>${keyDate}</strong>
						<div class="card card-seances-time-block bg-light mb-4">
							<div class="card-body">
								<p class="card-text">
									<c:forEach items="${movies.get(movie).get(keyDate)}" var="seance">
									<a href="/${locale}/seance/${seance.id}">${seance.getStartDateTime().toLocalTime()}</a>
									</c:forEach>
								</p>
							</div>
						</div>
					</div>
					</c:forEach>
				</div>
			</div>
		</div>
		
	</section>
	</c:forEach>

</jsp:attribute>
</f:layout>