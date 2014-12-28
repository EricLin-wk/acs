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
	function submit(){
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
	<s:form action="edit.do" method="post" id="formObj">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>
					<i class="icon-tag"></i> 个人资料维护
				</h2>
			</div>
			<table class="table table-bordered table-striped table-condensed">
				<tr>
					<th style="width: 15%">登入代码</th>
					<td>${username}</td>
				</tr>
				<tr>
					<th style="width: 15%">姓名</th>
					<td>${nameNative}<s:hidden name="nameNative"/></td>
				</tr>
				<tr>
					<th style="width: 15%">电子邮件</th>
					<td style="text-align: left">${email}<s:hidden name="email"/></td>
				</tr>
				<tr>
					<th style="width: 15%">电话</th>
					<td style="text-align: left">${phone}<s:hidden name="phone"/></td>
				</tr>
				<tr>
					<th style="width: 15%">手机</th>
					<td style="text-align: left">${mobile}<s:hidden name="mobile"/></td>
				</tr>
				<tr>
					<th style="width: 15%">状态</th>
					<td style="text-align: left">${userStatus.options[status].name}</td>
				</tr>
				<tr>
					<th style="width: 15%">部门</th>
					<td style="text-align: left">${group.code}/${group.description}</td>
				</tr>
				<tr>
					<th style="width: 15%">目前角色</th>
					<td style="text-align: left">
						<table class="table table-bordered table-striped table-condensed">
							<tbody>
								<c:forEach items="${roles}" var="role" varStatus="status">
									<tr>
										<td>
											${role.description}
											<c:if test="${role.type=='MANAGER'}"> 部门管理</c:if>
											<c:if test="${role.type=='GROUP'}"> 部门</c:if>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<s:if test="teamRoles.size>0">
							<table class="table table-bordered table-striped table-condensed">
								<tbody>
									<tr>
										<th>DashBoard</th>
									</tr>
									<c:forEach items="${teamRoles}" var="role" varStatus="status">
										<tr>
											<td>
												${role.description}
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</s:if>
					</td>
				</tr>
			</table>
			<div class="row-fluid">
				<div class="span12" style="margin-bottom: 20px; margin-left: 15px">
					<button type="submit" class="btn btn-primary" onclick="submit();">修改</button>
					<a href="prepareChangePasswd.do" class="btn btn-primary">变更密码</a>
				</div>
			</div>
		</div>
	</s:form>
</div>