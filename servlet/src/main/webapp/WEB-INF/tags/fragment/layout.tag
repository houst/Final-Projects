<!DOCTYPE html>
<jsp:directive.tag description="Layout" pageEncoding="UTF-8" isELIgnored="false"/>

<%@ taglib prefix="fragment" tagdir="/WEB-INF/tags/fragment" %>

<jsp:directive.attribute name="title" />
<jsp:directive.attribute name="content" fragment="true" />
 
<html lang="${sessionScope.locale}">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="shortcut icon" type="image/x-icon" href="/img/favicon.png" />

	<title>${title}</title>

	<!-- MDBootstrap -->
	<link rel="stylesheet" href="/lib/fontawesome-free-5.10.2/css/all.css">
	<link rel="stylesheet" href="/lib/mdb-4.8.8/css/bootstrap.min.css">
	<link rel="stylesheet" href="/lib/mdb-4.8.8/css/compiled-4.8.8.min.css">

	<!-- Bootstrap DateTime Picker CSS -->
	<link rel="stylesheet" href="/lib/bootstrap-datetime-picker/bootstrap-datetimepicker.css">

	<!-- JMSpinner CSS -->
	<link rel="stylesheet" href="/lib/ajax-loader-jquery-css3-jmspinner/jm.spinner.min.css">

	<!-- Alertify CSS -->
	<link rel="stylesheet" href="/lib/alertify/alertify.min.css">

	<!-- Custom styles -->
	<link rel="stylesheet" href="/css/style.css">
</head>
<body>

	<fragment:header/>
	
    <jsp:invoke fragment="content"/>
    
    <fragment:footer/>
    
  	<!-- MDBootstrap -->
	<script type="text/javascript" src="/lib/mdb-4.8.8/js/jquery-3.4.1.min.js"></script>
	<script type="text/javascript" src="/lib/mdb-4.8.8/js/popper.min.js"></script>
	<script type="text/javascript" src="/lib/mdb-4.8.8/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/lib/mdb-4.8.8/js/mdb2.js"></script>

	<!-- Bootstrap DateTime Picker (required jQuery, bootstrap 3, moment) -->
	<script type="text/javascript" src="/lib/bootstrap-datetime-picker/moment-with-locales.js"></script>
	<script type="text/javascript" src="/lib/bootstrap-datetime-picker/bootstrap-datetimepicker.js"></script>

	<!-- JMSpinner (required jQuery) -->
	<script type="text/javascript" src="/lib/ajax-loader-jquery-css3-jmspinner/jm.spinner.js"></script>

	<!-- Main JS -->
	<script type="text/javascript" src="/js/_seance.js"></script>
	<script type="text/javascript" src="/js/_ticket.js"></script>
	<script type="text/javascript" src="/js/_movie.js"></script>
	<script type="text/javascript" src="/js/_user.js"></script>
	<script type="text/javascript" src="/js/main.js"></script>
</body>
</html>