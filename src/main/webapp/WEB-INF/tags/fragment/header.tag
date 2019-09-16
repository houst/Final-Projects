<jsp:directive.tag description="Header" pageEncoding="UTF-8" isELIgnored="false"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" />
 
<c:set var="locale" value="${sessionScope.locale}" />
<c:set var="availLanguages" value="${applicationScope.localePrefixes}" />

<c:set var="isAuthenticated" value="${sessionScope['isAuthenticated'] == true}" />
<c:set var="isAnonymous" value="${!sessionScope['isAuthenticated'] == true}" />
<c:set var="isAdmin" value="${sessionScope['isAdmin'] == true}" />
 
<header>

	<div class="container">
		<div class="row">
			<div class="col-md-12">

				<!-- Navbar -->
				<nav class="mb-3 navbar navbar-expand-lg navbar-dark info-color">
					<a class="navbar-brand" href="/">CINEMA</a>
					<button class="navbar-toggler" type="button" data-toggle="collapse"
						data-target="#navbarSupportedContent-4" aria-controls="navbarSupportedContent-4"
						aria-expanded="false" aria-label="Toggle navigation">
						<span class="navbar-toggler-icon"></span>
					</button>
					<div class="collapse navbar-collapse" id="navbarSupportedContent-4">

						<ul class="navbar-nav mr-auto">
							<li class="nav-item">
								<a class="nav-link" href="/">
									<fmt:message key="schedule" />
								</a>
							</li>
						</ul>

						<ul class="navbar-nav ml-auto">
							
							<c:if test="${isAnonymous}">
								<li class="nav-item">
									<a class="nav-link" data-toggle="modal" data-target="#modalLRFormDemo" href="#">
										<fmt:message key="login"/>
									</a>
								</li>
							</c:if>
							
							<c:if test="${isAuthenticated}">
								<li class="nav-item dropdown">
									<a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink-4" data-toggle="dropdown"
										aria-haspopup="true" aria-expanded="false">
										<i class="fas fa-user"></i>
										name
	
									</a>
									<div class="dropdown-menu dropdown-menu-right dropdown-info" aria-labelledby="navbarDropdownMenuLink-4">
										
										<a class="dropdown-item" href="/${locale}/profile"><fmt:message key="profile"/></a>
										
										<c:if test="${isAdmin}">
											<div class="dropdown-divider">
											</div>
											<h6 class="dropdown-header">
												<fmt:message key="admin.panel"/>
											</h6>
											<a class="dropdown-item" href="/${locale}/users">
												<fmt:message key="user.list"/>
											</a>
											<a class="dropdown-item" href="/${locale}/movies">
												<fmt:message key="movie.list"/>
											</a>
											<a class="dropdown-item" href="/${locale}/seances">
												<fmt:message key="seance.list"/>
											</a>
										</c:if>
										
										<div class="dropdown-divider"></div>
										<a class="dropdown-item logout" href="/logout"><fmt:message key="logout"/></a>
									</div>
								</li>
							</c:if>
							
							<li class="nav-item dropdown">
								<a class="nav-link dropdown-toggle" id="navbarDropdownMenuLink-5" data-toggle="dropdown"
									aria-haspopup="true" aria-expanded="false">
									<i class="fas fa-globe-americas"></i>
									${fn:toUpperCase(locale)}
								</a>
								<div class="dropdown-menu dropdown-menu-right dropdown-info" aria-labelledby="navbarDropdownMenuLink-5">
									<c:forEach items="${availLanguages.split(',')}" var="prefix">
									    <a class="dropdown-item" href="/${prefix}${pageContext.request.contextPath}">
											<img class="flag" src="/img/flags/${prefix}.png" alt="${fn:toUpperCase(prefix)}">
											${fn:toUpperCase(prefix)}
										</a>
									</c:forEach>
								</div>
							</li>

						</ul>
					</div>
				</nav>
				<!--/.Navbar -->
			</div>
		</div>
	</div>
	<!-- Modal: Login / Registration Form-->
	<div class="modal fade" id="modalLRFormDemo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
		style="display: none;" aria-hidden="true">

		<div class="modal-dialog cascading-modal" role="document">
			<!--Content-->
			<div class="modal-content">

				<!--Modal cascading tabs-->
				<div class="modal-c-tabs">

					<!-- Nav tabs -->
					<ul class="nav nav-tabs md-tabs tabs-2 light-blue darken-3" role="tablist">
						<li class="nav-item waves-effect waves-light">
							<a class="nav-link active" data-toggle="tab" href="#panel17" role="tab"
								aria-selected="true">
								<i class="fas fa-user mr-1"></i> <fmt:message key="login"/></a>
						</li>
						<li class="nav-item waves-effect waves-light">
							<a class="nav-link" data-toggle="tab" href="#panel18" role="tab" aria-selected="false">
								<i class="fas fa-user-plus mr-1"></i> <fmt:message key="reg"/></a>
						</li>
					</ul>

					<!-- Tab panels -->
					<div class="tab-content">
						<!--Panel 17-->
						<div class="tab-pane fade in active show" id="panel17" role="tabpanel">

							<!--Body-->
							<div class="modal-body mb-1">
								<form method="POST" action="/login" id="login-form" class="needs-validation" novalidate>

									<div class="alertify-log alertify-log-error alertify-log-show"
										style="display:none;margin-top:0">
										<fmt:message key="login.invalid"/>
									</div>

									<div class="md-form form-sm">
										<i class="fas fa-envelope prefix active"></i>
										<input type="text" name="email" id="form2" class="form-control form-control-sm"
											required>
										<label for="form2" class="active"><fmt:message key="login.email"/></label>
										<div class="invalid-feedback">
											<fmt:message key="login.email.invalid"/>
										</div>
									</div>

									<div class="md-form form-sm">
										<i class="fas fa-lock prefix"></i>
										<input type="password" name="password" id="form3"
											class="form-control form-control-sm" required>
										<label for="form3"><fmt:message key="login.password"/></label>
										<div class="invalid-feedback">
											<fmt:message key="login.password.invalid"/>
										</div>
									</div>
									<div class="text-center mt-4">
										<button type="submit" class="btn btn-info waves-effect waves-light">
											<fmt:message key="login"/>
											<i class="fas fa-sign-in ml-1"></i>
										</button>
									</div>
								</form>
							</div>
							<!--Footer-->
							<div class="modal-footer">
								<div class="options text-center text-md-right mt-1">
									<p><fmt:message key="login.password.forgot"/>
										<a href="#" class="blue-text"><fmt:message key="login.password.forgot-password"/></a>
									</p>
								</div>
								<button type="button" class="btn btn-outline-info waves-effect ml-auto"
									data-dismiss="modal"><fmt:message key="close"/></button>
							</div>

						</div>
						<!--/.Panel 7-->

						<!--Panel 18-->
						<div class="tab-pane fade" id="panel18" role="tabpanel">

							<!--Body-->
							<div class="modal-body">

								<form action="/user" method="POST" id="reg-form" class="needs-validation" novalidate>

									<div class="alert-email-exist alertify-log alertify-log-error alertify-log-show"
										style="display:none;margin-top:0">
										<fmt:message key="reg.email.exists"/>
									</div>

									<div class="alert-confirmation-failed alertify-log alertify-log-error alertify-log-show"
										style="display:none;margin-top:0">
										<fmt:message key="reg.confirm.failed"/>
									</div>

									<div class="md-form form-sm">
										<i class="fas fa-envelope prefix"></i>
										<input type="email" id="email" name="email" class="form-control form-control-sm"
											required>
										<label for="email" class=""><fmt:message key="login.email"/></label>
										<div class="invalid-feedback">
											<fmt:message key="login.email.invalid"/>
										</div>
									</div>

									<div class="md-form form-sm">
										<i class="fas fa-lock prefix"></i>
										<input type="password" id="password" name="password"
											class="form-control form-control-sm" required>
										<label for="password" class=""><fmt:message key="login.password"/></label>
										<div class="invalid-feedback">
											<fmt:message key="login.password.invalid"/>
										</div>
									</div>

									<div class="md-form form-sm">
										<i class="fas fa-lock prefix"></i>
										<input type="password" id="matchingPassword" name="matchingPassword"
											class="form-control form-control-sm" required>
										<label for="matchingPassword" class=""><fmt:message key="reg.password.confirm"/></label>
										<div class="invalid-feedback">
											<fmt:message key="reg.password.confirm.entry"/>
										</div>
									</div>

									<div class="md-form form-sm">
										<i class="fas fa-user-nurse prefix"></i>
										<input type="text" id="username" name="username"
											class="form-control form-control-sm" required>
										<label for="username" class=""><fmt:message key="reg.name"/></label>
										<div class="invalid-feedback">
											<fmt:message key="reg.name.entry"/>
										</div>
									</div>

									<div class="md-form form-sm">
										<i class="fas fa-phone prefix"></i>
										<input type="number" id="tel" name="tel" class="form-control form-control-sm"
											required>
										<label for="tel" class=""><fmt:message key="reg.tel"/></label>
										<div class="invalid-feedback">
											<fmt:message key="reg.tel.entry"/>
										</div>
									</div>

									<div class="text-center form-sm mt-4">
										<button type="submit" class="btn btn-info waves-effect waves-light">
											<fmt:message key="reg.signup"/>
											<i class="fas fa-sign-in ml-1"></i>
										</button>
									</div>
								</form>
							</div>
							<!--Footer-->
							<div class="modal-footer">
								<div class="options text-right">
									<p class="pt-1"><fmt:message key="reg.accepting-all"/>
										<a href="#" class="blue-text"><fmt:message key="reg.accepting-all-agreements"/></a>
									</p>
								</div>
								<button type="button" class="btn btn-outline-info waves-effect ml-auto"
									data-dismiss="modal"><fmt:message key="close"/></button>
							</div>
						</div>
						<!--/.Panel 8-->
					</div>

				</div>
			</div>
			<!--/.Content-->
		</div>
	</div>
	<!-- END Modal: Login / Registration Form-->
</header> 