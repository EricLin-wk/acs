<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.acs.core.user.utils.AdminHelper"%>
<%@ taglib uri="http://www.teecs.com/jsp/teecs" prefix="teecs"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
if(AdminHelper.hasPermission("ROLE_MONITOR_RPT")) {
%>
<meta http-equiv="refresh" content="0; url=./dashboard/list.do" />
<%
} else {
%>
<meta http-equiv="refresh" content="0; url=./userSelf/view.do" />
<%
}
%>

<title>请稍后</title>
</head>
<body>
请稍后
</body>
</html>