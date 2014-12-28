<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.acs.core.user.utils.AdminHelper"%>
<%@ taglib uri="http://www.teecs.com/jsp/teecs" prefix="teecs"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
if(AdminHelper.hasPermission("ROLE_MONITOR_RPT")) {
%>
<c:url value="/dashboard/list.do" var="redirectPath" />
<meta http-equiv="refresh" content="10; url=${redirectPath}" />
<%
} else {
%>
<c:url value="/userSelf/view.do" var="redirectPath" />
<meta http-equiv="refresh" content="10; url=${redirectPath}" />
<%
}
%>

<title>请稍后</title>
</head>
<body>
请稍后
</body>
</html>