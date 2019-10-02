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
<jsp:attribute name="title"><fmt:message key="user.list"/></jsp:attribute> 
<jsp:attribute name="content">

<div class="container">

	<div class="jumbotron">
		<h1><fmt:message key="user.list"/></h1>
		<table class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th class="text-center"><fmt:message key="user.email"/></th>
					<th class="text-center"><fmt:message key="user.name"/></th>
					<th class="text-center"><fmt:message key="user.number"/></th>
					<th class="text-center"><fmt:message key="user.roles"/></th>
					<th class="text-center"><fmt:message key="actions"/></th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="el" items="${elements}">
				<tr>
					<td class="pt-3-half">${ el.email }</td>
					<td class="pt-3-half">${ el.username }</td>
					<td class="pt-3-half">${ el.tel }</td>
					<td class="pt-3-half">${ fnl:join(el.authorities,', ') }</td>
					<td>
						<span class="table-edit">
							<a href="/${locale}/user/${el.id}/edit"
								class="btn btn-outline-warning btn-rounded btn-sm px-2 waves-effect waves-light">
								<i class="fas fa-pencil-alt mt-0"></i>
							</a>
						</span>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<div class="row">
			<div class="col-sm-12 col-md-5">
				<div class="dataTables_info">
					<fmt:message key="pagination.showing"/>
					${(page) * size - size + 1 }
					<fmt:message key="pagination.to"/>
					${((page) * size) > elementsCount ? elementsCount : ((page) * size)}
					<fmt:message key="pagination.of"/>
					${elementsCount}
					<fmt:message key="pagination.entries"/>
				</div>
			</div>
			<div class="col-sm-12 col-md-7">
				<c:if test="${elementsCount > size}">
					<div class="dataTables_paginate paging_simple_numbers text-right">
						<ul class="pagination">
							<li class="paginate_button page-item previous ${page < 2 ? 'disabled' : ''}">
								<a href="/${locale}/user?page=${page-1}" class="page-link">
									<fmt:message key="pagination.previous"/>
								</a>
							</li>
							<c:forEach begin="1" end="${pagesCount}" var="i">
							<li class="paginate_button page-item ${page == i ? 'active' : ''}">
								<a href="/${locale}/user?page=${i}" class="page-link">${i}</a>
							</li>
							</c:forEach>
							<li class="paginate_button page-item next ${page == pagesCount ? 'disabled' : ''}">
								<a href="/${locale}/user?page=${page+1}" class="page-link">
									<fmt:message key="pagination.next"/>
								</a>
							</li>
						</ul>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</div>

</jsp:attribute>
</f:layout>
