<jsp:directive.page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" />
<%@taglib prefix="f" tagdir="/WEB-INF/tags/fragment" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" />

<c:set var="locale" value="${sessionScope.locale}" />

<f:layout>
<jsp:attribute name="title">Error 404</jsp:attribute> 
<jsp:attribute name="content">

	<section class="container">

		<div class="jumbotron mb-3">
			<h1><i class="far fa-frown"></i> <fmt:message key="error.code404.title" /></h1>
			<p>
				<fmt:message key="error.code404.description" /></p>
			<p>
				<a class="btn btn-lg btn-primary" href="/${locale}" role="button">
					<fmt:message key="home" />
					&raquo;
				</a>
			</p>
		</div>
	</section>
</jsp:attribute>
</f:layout>