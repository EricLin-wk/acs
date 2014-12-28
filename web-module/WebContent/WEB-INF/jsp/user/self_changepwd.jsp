<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/taglibs.jsp"%>
<%@ page import="com.acs.core.common.utils.ServerValue"%>
<style type="text/css">
input,textarea {
	width: auto;
}
</style>
<script type="text/javascript">
function changePwd() {
	if (document.formObj.oldPassword.value == "" ) {
		alert("请输入旧密码");
		document.formObj.oldPassword.focus();
		return false ;
	}
	if (document.formObj.newPassword.value == "" ) {
		alert("请输入新密码");
		document.formObj.newPassword.focus();
		return false ;
	}
	if (document.formObj.newPassword.value != document.formObj.confirmPassword.value ) {
		alert("新密码与确认密码不符");
		document.formObj.newPassword.focus();
		return false ;
	}
	document.formObj.submit();
}
</script>
<div class="row-fluid">
	<s:if test="hasActionMessages()">
		<div class="alert alert-success">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<s:actionmessage cssStyle="list-style-type:none;" escape="false" />
		</div>
	</s:if>
	<s:if test="hasActionErrors()">
		<div class="alert alert-error">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<s:actionerror cssStyle="list-style-type:none;" escape="false" />
		</div>
	</s:if>
	<s:if test="hasFieldErrors()">
		<div class="alert alert-info">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<s:fielderror cssStyle="list-style-type:none;" escape="false" />
		</div>
	</s:if>
	<s:form action="changePasswd.do" method="post" id="formObj">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>
					<i class="icon-tag"></i> 个人资料维护
				</h2>
			</div>
			<table class="table table-bordered table-striped table-condensed">
				<tr>
					<th style="width: 15%">旧密码</th>
					<td><s:password name="oldPassword" theme="simple"/></td>
				</tr>
				<tr>
					<th style="width: 15%">新密码</th>
					<td><s:password name="newPassword" theme="simple"/></td>
				</tr>
				<tr>
					<th style="width: 15%">确认</th>
					<td style="text-align: left"><input type="password" name="confirmPassword"/>(请重覆新密码一次)</td>
				</tr>
			</table>
			<div class="row-fluid">
				<div class="span12" style="margin-bottom: 20px; margin-left: 15px">
					<button type="submit" class="btn btn-primary" onclick="changePwd();">确认</button>
					<a href="view.do" class="btn btn-primary">取消</a>
				</div>
			</div>
		</div>
	</s:form>
</div>