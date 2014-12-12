<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/taglibs.jsp"%>
<style type="text/css">
input,textarea  {
	width:auto;
}
</style>
<script type="text/javascript">
	function search(){
		document.searchForm.submit();
	}
</script>
<div class="row-fluid">
	<s:if test="hasActionMessages()">
		<div class="alert alert-block" >
			<button type="button" class="close" data-dismiss="alert">×</button>
			<s:actionmessage cssStyle="list-style-type:none;" escape="false"/>
		</div>
	</s:if>
	<s:if test="hasActionErrors()">
		<div class="alert alert-error">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<s:actionerror cssStyle="list-style-type:none;" escape="false"/>
		</div>
	</s:if>
	<s:if test="hasFieldErrors()">
		<div class="alert alert-info">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<s:fielderror cssStyle="list-style-type:none;" escape="false"/>
		</div>
	</s:if>

	<s:form action="search.do" method="post" id="searchForm">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>
					<i class="icon-tag"></i> User维护管理
				</h2>
				<div class="box-icon">
					<a href="add.do" class="btn btn-primary" style="border-bottom: 10px;width: 35px">新增</a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
				</div>
				
			</div>
	
			<div class="box-content">
				<div class="row-fluid">
					<div class="span1">登入代码</div>
					<div class="span2">
						<input name="usernameParam" id="usernameParam" type="text" value="${usernameParam}">
					</div>
					<div class="span1">电子邮件</div>
					<div class="span2">						
						<input name="emailParam" id="emailParam" type="text" value="${emailParam}"/>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span1">部门</div>
					<div class="span2">
						<input name="groupParam" id="groupParam" type="text" value="${groupParam}"/>
					</div>
					<div class="span1">狀態</div>
					<div class="span3">
						<s:checkboxlist list="userStatusMenu.options" name="statusParam" listKey="value.code" listValue="value.name" template="checkboxlist2" theme="simple"/>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span1">
						<button type="submit" class="btn btn-primary" onClick="search();">搜索</button>
					</div>
				</div>
			</div>
		</div>
	</s:form>
</div>
<s:if test="objList.size > 0">
	<table class="table table-bordered table-striped table-condensed">
		<thead>
			<tr>
				<th>#</th>
				<th>登入代码</th>
				<th>姓名</th>
				<th>电子邮件</th>
				<th>部门</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${objList}" var="user" varStatus="rows_index">
				<tr>
					<td><fmt:formatNumber pattern="0000" value="${rows_index.count + pager.currentPage*20}" /></td>
					<td><a href="view.do?objId=${user.username}">${user.username}</a></td>
					<td>${user.nameNative}</td>
					<td>${user.email}</td>
					<td>${user.group.code}/${user.group.description}</td>
					<td class="center">${userStatusMenu.options[user.status].name}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<teecs:pageutil link="search.do" pagerObj="${pager}" />
</s:if>