<jsp:directive.page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" />
<%@taglib prefix="f" tagdir="/WEB-INF/tags/fragment" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fnl" uri="/WEB-INF/tags/contains.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" />

<c:set var="locale" value="${sessionScope.locale}" />

<f:layout>
<jsp:attribute name="title">Main page</jsp:attribute> 
<jsp:attribute name="content">

<section class="container">

		<div class="jumbotron mb-3">
			<h1><fmt:message key="edit"/> <fmt:message key="user"/> ${user.id}</h1>
			<div class="col-md-6">
				<form action="/user" id="edit-user-form" method="PUT">
					
					<input type="hidden" name="userId" class="form-control form-control-sm" value="${user.id}">

					<div class="md-form form-sm">
						<i class="fas fa-envelope prefix"></i>
						<input type="email" name="email" class="form-control form-control-sm" value="${user.email}">
						<label for="email" class=""><fmt:message key="user.email"/></label>
					</div>

					<div class="md-form form-sm">
						<i class="fas fa-user-nurse prefix"></i>
						<input type="text" name="name" class="form-control form-control-sm" value="${user.username}">
						<label for="name" class=""><fmt:message key="user.name"/></label>
					</div>

					<div class="md-form form-sm">
						<i class="fas fa-phone prefix"></i>
						<input type="text" name="number" class="form-control form-control-sm" value="${user.tel}">
						<label for="number" class=""><fmt:message key="user.number"/></label>
					</div>
					
					<h3><fmt:message key="user.roles"/></h3>
					<c:forEach items="${roles}" var="role">
					<div class="custom-control custom-checkbox">
						<input type="checkbox" class="custom-control-input" name="${role.name()}" id="role-${role.name()}"
							${fnl:contains(user.authorities, role) ? 'checked' : ''}>
						<label class="custom-control-label" for="role-${role.name()}">${role.name()}</label>
					</div>
					</c:forEach>

					<div class="text-left form-sm mt-4">
						<button type="submit" class="btn btn-info waves-effect waves-light">
							<fmt:message key="save"/>
							<i class="fas fa-sign-in ml-1"></i>
						</button>
					</div>
				</form>
				</div>
				
			
		</div>
</section>

</jsp:attribute>
</f:layout>