<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="org.springframework.security.authentication.BadCredentialsException" %>
<%@ page import="org.springframework.security.core.AuthenticationException" %>
<%@ page import="org.springframework.security.core.userdetails.UserDetails"%>
<%@ page import="com.acs.core.user.utils.AdminHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Login Page</title>
<link id="bs-css" href="css/bootstrap-united.css" rel="stylesheet"/>
<style type="text/css">
body {
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}
</style>
<link href="css/charisma-app.css" rel="stylesheet"/>
<link href='css/uniform.default.css' rel='stylesheet'/>
<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="js/html5.js"></script>
<![endif]-->
</head>

<!-- <c:if test="${param.error != null}">
		<h2>Username or password wrong!</h2>
	</c:if> -->

<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="row-fluid">
				<div class="span12 center login-header">
				<c:if test="${SPRING_SECURITY_LAST_EXCEPTION.message=='Bad credentials'}">
					<h2 style="color: red">Your login attempt was not successful, try again.</h2>
				</c:if>
				</div><!--/span-->
			</div><!--/row-->
			<div class="row-fluid">
				<div class="well span5 center login-box">
					<div class="alert alert-info">Please login with your Username and Password.</div>
					<form class="form-horizontal" action="j_spring_security_check" method="post">
						<fieldset>
							<div class="input-prepend" title="Username" data-rel="tooltip">
								<span class="add-on"><i class="icon-user"></i></span><input	autofocus class="input-large span10" name="j_username"
									id="username" type="text" />
							</div>
							<div class="clearfix"></div>

							<div class="input-prepend" title="Password" data-rel="tooltip">
								<span class="add-on"><i class="icon-lock"></i></span><input
									class="input-large span10" name="j_password" id="password"
									type="password"/>
							</div>
							<div class="clearfix"></div>

							<!-- <div class="input-prepend">
								<label class="remember" for="remember"><input
									type="checkbox" id="remember" name="_spring_security_remember_me"/>Remember me</label>
							</div> -->
							<div class="clearfix"></div>

							<p class="center span5">
								<button type="submit" class="btn btn-primary">Login</button>
							</p>
						</fieldset>
					</form>
				</div>
				<!--/span-->
			</div>
			<!--/row-->
		</div>
		<!--/fluid-row-->

	</div>
	<!--/.fluid-container-->

	<!-- external javascript
	================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->

	<!-- jQuery -->
	<script src="js/jquery-1.7.2.min.js"></script>
	<!-- library for advanced tooltip -->
	<script src="js/bootstrap-tooltip.js"></script>
	<!-- checkbox, radio, and file input styler -->
	<script src="js/jquery.uniform.min.js"></script>
	<script>
		//tool tip
		$('[rel="tooltip"],[data-rel="tooltip"]').tooltip({
			"placement" : "bottom",
			delay : {
				show : 400,
				hide : 200
			}
		});
		//uniform - styler for checkbox, radio and file input
		$("input:checkbox, input:radio, input:file").not(
				'[data-no-uniform="true"],#uniform-is-ajax').uniform();
	</script>
</body>
</html>