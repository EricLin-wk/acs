<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<head>
<c:url value="../js" var="jsPath" />
<c:url value="../css" var="cssPath" />
<c:url value="../img" var="imgPath" />
<meta charset="utf-8">
<title><tiles:insertAttribute name="commonTitle" ignore="true" /> <tiles:insertAttribute name="title"
		ignore="true" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- The styles -->
<link id="bs-css" href="${cssPath}/bootstrap-united.css" rel="stylesheet">
<style type="text/css">
body {
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}
.checkbox{
	padding-left: 0px;
}
.radio input[type="radio"],
.checkbox input[type="checkbox"] {
  margin-left: 0px;
}
</style>
<link href="${cssPath}/bootstrap-responsive.css" rel="stylesheet">
<link href="${cssPath}/charisma-app.css" rel="stylesheet">
<link href="${cssPath}/jquery-ui-1.8.21.custom.css" rel="stylesheet">
<link href='${cssPath}/chosen.css' rel='stylesheet'>
<link href='${cssPath}/uniform.default.css' rel='stylesheet'>
<link href='${cssPath}/jquery.noty.css' rel='stylesheet'>
<link href='${cssPath}/noty_theme_default.css' rel='stylesheet'>
<link href='${cssPath}/jquery.iphone.toggle.css' rel='stylesheet'>
<link href='${cssPath}/opa-icons.css' rel='stylesheet'>
<link href='${cssPath}/uploadify.css' rel='stylesheet'>
<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
	  <script src="${jsPath}/html5.js"></script>
	<![endif]-->

<!-- The fav icon -->
<link rel="shortcut icon" href="${imgPath}/favicon.ico">
<!-- external javascript
	================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->

	<!-- jQuery -->
	<script src="${jsPath}/jquery-1.7.2.min.js"></script>
	<!-- jQuery UI -->
	<script src="${jsPath}/jquery-ui-1.8.21.custom.min.js"></script>
	<!-- transition / effect library -->
	<script src="${jsPath}/bootstrap-transition.js"></script>
	<!-- alert enhancer library -->
	<script src="${jsPath}/bootstrap-alert.js"></script>
	<!-- modal / dialog library -->
	<script src="${jsPath}/bootstrap-modal.js"></script>
	<!-- custom dropdown library -->
	<script src="${jsPath}/bootstrap-dropdown.js"></script>
	<!-- scrolspy library -->
	<script src="${jsPath}/bootstrap-scrollspy.js"></script>
	<!-- library for creating tabs -->
	<script src="${jsPath}/bootstrap-tab.js"></script>
	<!-- library for advanced tooltip -->
	<script src="${jsPath}/bootstrap-tooltip.js"></script>
	<!-- popover effect library -->
	<script src="${jsPath}/bootstrap-popover.js"></script>
	<!-- button enhancer library -->
	<script src="${jsPath}/bootstrap-button.js"></script>
	<!-- accordion library (optional, not used in demo) -->
	<!-- <script src="${jsPath}/bootstrap-collapse.js"></script> -->
	<script src="${jsPath}/jquery.collapse.js"></script>
	<script src="${jsPath}/jquery.collapse_storage.js"></script>
	<script src="${jsPath}/jquery.collapse_cookie_storage.js"></script>
	<!-- autocomplete library -->
	<script src="${jsPath}/bootstrap-typeahead.js"></script>
	<!-- library for cookie management -->
	<script src="${jsPath}/jquery.cookie.js"></script>
	<!-- data table plugin -->
	<script src='${jsPath}/jquery.dataTables.min.js'></script>
	<!-- select or dropdown enhancer -->
	<script src="${jsPath}/jquery.chosen.min.js"></script>
	<!-- checkbox, radio, and file input styler -->
	<script src="${jsPath}/jquery.uniform.min.js"></script>
	<!-- notification plugin -->
	<script src="${jsPath}/jquery.noty.js"></script>
	<!-- for iOS style toggle switch -->
	<script src="${jsPath}/jquery.iphone.toggle.js"></script>
	<!-- autogrowing textarea plugin -->
	<script src="${jsPath}/jquery.autogrow-textarea.js"></script>
	<!-- application script for Charisma demo -->
	<script src="${jsPath}/charisma.js"></script>
	<script src="${jsPath}/formValidator.js"></script>
	<script src="${jsPath}/formValidatorRegex.js"></script>
</head>
<body>
	<!-- topbar starts -->
	<tiles:insertAttribute name="header" ignore="true" />
	
	<!-- topbar ends -->
	<div class="container-fluid">
		<div class="row-fluid">

			<!-- left menu starts -->
			<tiles:insertAttribute name="menu" ignore="true" />
			<!-- left menu ends -->

			<noscript>
				<div class="alert alert-block span10">
					<h4 class="alert-heading">Warning!</h4>
					<p>
						You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use
						this site.
					</p>
				</div>
			</noscript>

			<div id="content" class="span10">
				<!-- content starts -->
				<tiles:insertAttribute name='content' ignore="true" />
				<!-- content ends -->
			</div>
			<!--/#content.span10-->
		</div>
		<!--/fluid-row-->
		<hr>

		<footer> </footer>

	</div>
	<!--/.fluid-container-->
	
</body>
</html>
