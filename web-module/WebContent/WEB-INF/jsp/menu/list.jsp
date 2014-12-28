<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/taglibs.jsp"%>
<c:url value="../img" var="imgPath" />
<script type="text/javascript">
	$().ready(function() {
		$.ajax({
			cache : false,
			async : false,
			url : '<c:url value="/menu/autoCompleteMenuKey.do"/>',
			data : {},
			type : 'post',
			dataType : 'json',
			error : function(x, e) {
				alert("failed!" + x + e);
			},
			//complete: function(){alert("complete!");},
			success : function(data) {
				var dataSource = data['key'];
				$("#menuKeyParam").typeahead({
					source : dataSource
				});
			}
		});
		document.getElementById('fileUpload').style.display = "none";
	});
	function search(){
		document.getElementById('fileUpload').style.display = "block";
		document.searchForm.submit();
	}
</script>
<div class="row-fluid">
	<s:if test="hasActionMessages()">
		<div class="alert alert-success" >
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
	<div id="fileUpload" class="box span12" >
		<h2>请稍后....</h2>
		<img src="${imgPath}/ajax-loaders/ajax-loader-6.gif"/>
	</div>

	<s:form action="list.do" method="post" id="searchForm">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>
					<i class="icon-tag"></i> 选单维护管理
				</h2>
				<div class="box-icon">
					<a href="add.do" class="btn btn-primary" style="border-bottom: 10px;width: 35px">新增</a>
					<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
				</div>
				
			</div>
	
			<div class="box-content">
				<div class="row-fluid">
					<div class="span1">Menu Key</div>
					<div class="span2">
						<input name="menuKeyParam" type="text" id="menuKeyParam" value="${menuKeyParam}">
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
<s:if test="menus.size > 0">
<table class="table table-bordered table-striped table-condensed">
	<thead>
		<tr>
			<th>Menu Key</th>
			<th>Menu Desc</th>
			<th>Menu Type</th>
			<th>删除</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${menus}" var="menu" varStatus="rows_index">
			<tr>
				<td><a href="edit.do?menuKey=${menu.key}">${menu.key}</a></td>
				<td class="center">${menu.description}</td>
				<td class="center">${menu.type}</td>
				<td class="center"><a href="delete.do?deleteMenuKey=${menu.key}" class="btn btn-primary">删除</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<teecs:pageutil link="list.do" pagerObj="${pager}" />
</s:if>