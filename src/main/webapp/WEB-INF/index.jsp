<jsp:directive.page contentType="text/html" pageEncoding="UTF-8" />
<%@taglib prefix="f" tagdir="/WEB-INF/tags/fragment" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="locale" value="${sessionScope.locale}" />

<f:layout>
<jsp:attribute name="title">Main page</jsp:attribute> 
<jsp:attribute name="content">

<section class="container">
  <div class="jumbotron mb-3">
    <h1>Hello from Layout</h1>
  </div>
</section>

</jsp:attribute>
</f:layout>