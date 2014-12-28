<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/taglibs.jsp"%>
<%@ page import="com.acs.core.common.utils.ServerValue"%>
<style type="text/css">
input,textarea {
	width: auto;
}
</style>
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
	<s:form action="save.do" method="post" id="formObj">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>
					<i class="icon-tag"></i> 个人资料维护
				</h2>
			</div>
			<table class="table table-bordered table-striped table-condensed">
				<tr>
					<th style="width: 15%">* 登入代码</th>
					<td>${username}</td>
				</tr>
				<tr>
					<th style="width: 15%">* 姓名</th>
					<td><s:textfield name="nameNative" id="nameNative" theme="simple"/></td>
				</tr>
				<tr>
					<th style="width: 15%">* 电子邮件</th>
					<td><s:textfield name="email" id="email" theme="simple"/></td>
				</tr>
				<tr>
					<th style="width: 15%">电话</th>
					<td><s:textfield name="phone" theme="simple"/></td>
				</tr>
				<tr>
					<th style="width: 15%">手机</th>
					<td><s:textfield name="mobile" theme="simple"/></td>
				</tr>
			</table>
			<div class="row-fluid">
				<div class="span12" style="margin-bottom: 20px; margin-left: 15px">
					<button type="submit" class="btn btn-primary">储存修改</button>
					<a href="view.do" class="btn btn-primary">回检视页</a>
				</div>
			</div>
		</div>
	</s:form>
</div>