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

<div class="container">

	<div class="jumbotron mb-3">
			<h1>${seanceAtt.movie.title}</h1>
			<h2>${seanceAtt.startDateTime}</h2>
			
			<form action="/bill" id="book-form" method="PUT">

				<input
					type="hidden"
					name="seanceId"
					value="${seanceAtt.id}" />
				
				
				<h3><fmt:message key="cells"/></h3>
				<div class="row ml-1 clearfix">
				<c:forEach items="${seanceAtt.tickets}" var="ticket">
				<div class="custom-control custom-checkbox col-md-2 clearfix">
					<input type="checkbox" class="custom-control-input" name="ticket${ticket.id}" id="ticket${ticket.id}"
						${ticket.owner != null ? 'disabled' : ''}>
					<label class="custom-control-label" for="ticket${ticket.id}">${ticket.rowNum} <fmt:message key="row"/> - ${ticket.cellNum} <fmt:message key="cell"/></label>
				</div>
				</c:forEach>
				</div>
				<c:if test="${isAuthenticated}">
				<div class="text-left form-sm mt-4 clearfix">
					<button type="submit" class="btn btn-info waves-effect waves-light">
						<fmt:message key="book"/>
						<i class="fas fa-sign-in ml-1"></i>
					</button>
				</div>
				</c:if>
				<c:if test="${isAnonymous}">
				<div class="text-left form-sm mt-4 clearfix">
					<a data-toggle="modal" data-target="#modalLRFormDemo" class="btn btn-info waves-effect waves-light">
						<fmt:message key="book"/>
						<i class="fas fa-sign-in ml-1"></i>
					</a>
				</div>
				</c:if>

				
			</form>
			
	</div>
</div>

</jsp:attribute>
</f:layout>