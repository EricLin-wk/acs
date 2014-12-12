<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.acs.core.user.utils.AdminHelper"%>
<div class="navbar">
	<div class="navbar-inner">
		<div class="container-fluid">
			<button type="button" class="btn menu-toggle" onclick='$("#menu").toggle("fast");'>
				<i class="icon-th-list"></i>
			</button>
			<a class="brand" href="#"><span>Air Control Server</span></a>			
			<!-- user dropdown starts -->
			<div class="btn-group pull-right">
				<a class="btn dropdown-toggle" data-toggle="dropdown" href="#"> <i class="icon-user"></i><span
					class="hidden-phone"> <%=AdminHelper.getUser().getUsername()%></span> <span class="caret"></span>
				</a>
				<ul class="dropdown-menu">
					<li><s:a value="/logout.do">注销</s:a></li>
				</ul>
			</div>
			<!-- user dropdown ends -->
		</div>
	</div>
</div>