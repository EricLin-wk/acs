<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/taglibs.jsp"%>
<%@ page import="com.acs.core.common.utils.ServerValue"%>

<script type="text/javascript">
	$().ready(function() {
		if ($("#menuKey").val() == "") {
			$("#saveOrdUpdate").val('save');
			$('#menuKey').attr("readonly", false);
			$("input[name='optionCode']").each(function(i, item) {
				$(this).attr("readonly", false);
			});
		} else {
			$("#saveOrdUpdate").val('update');
			$('#menuKey').attr("readonly", true);
			$("input[name='optionCode']").each(function(i, item) {
				if (item.value != '') {
					$(this).attr("readonly", true);
				}
			});
		}
		$("input[name='optionSortOrder']").each(function(i, item) {
			$(this).numeric();
		});

	});
	function addNewOpn() {
		var sOpt = "<tr class=''>"
				+ "<td><input type='hidden' size='10' name='optionId' id='optionId' value='' /></td>"
				+ "<td><input type='text' size='10' name='optionCode' id='optionCode' value=''/></td>"
				+ "<td><input type='text' size='18' name='optionMemoOne' id='optionMemoOne' value='' /></td>"
				+ "<td><input type='text' size='6' name='optionMemoTwo' id='optionMemoTwo' value='' /></td>"
				+ "<td><input type='text' name='optionName' id='optionName' value='' /></td>"
				+ "<td><input type='text' size='4' name='optionSortOrder' id='optionSortOrder' value='' /></td>"
				+ "</tr>";
		$("#addOption").append(sOpt);
	}
	function submitForm() {
		try {
			$("input[name='optionSortOrder']")
					.each(
							function(i, item) {
								if ($("input[id='optionCode_" + (i + 1) + "']")
										.val() != ""
										&& item.value == "") {
									if (!($(
											"input[id='optionCode_" + (i + 1)
													+ "']").val() === undefined)) {
										alert("SortOrder为必填");
										throw item;
									}
								}
							});
		} catch (e) {
			return false;
		}
		document.menuForm.submit();
		return true;
	}
</script>
<style type="text/css">
input,textarea  {
	width:auto;
}
</style>
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
	<s:form action="save.do" method="post" id="menuForm">
		<div class="box span12">
			<div class="box-header well" data-original-title>
				<h2>
					<i class="icon-tag"></i> 选单维护管理
				</h2>
			</div>
			<table class="table table-bordered table-striped table-condensed">
				<tr>
					<th>Menu Key</th>
					<td style="text-align: left"><input type="text" size="30"
						id="menuKey" name="menuKey" value="${menu.key}" /></td>
				</tr>
				<tr>
					<th>Menu Desc</th>
					<td style="text-align: left"><input type="text" size="40"
						id="menuDesc" name="menuDesc" value="${menu.description}" /></td>
				</tr>
				<tr>
					<th>Menu Type</th>
					<td style="text-align: left"><input type="text" maxlength="1"
						size="1" id="menuType" name="menuType" value="${menu.type}" /></td>
				</tr>
				<tr>
					<th>Memo</th>
					<td style="text-align: left"><textarea class="autogrow" rows="3" cols="80"
							id="menuMemo" name="menuMemo">${menu.memo}</textarea><input
						type="hidden" id="saveOrdUpdate" name="saveOrdUpdate" value="" /></td>
				</tr>
			</table>
			<table class="table table-bordered table-striped table-condensed">
				<thead>
					<tr>
						<th></th>
						<th>OPTION CODE</th>
						<th>OPTION MEMO1</th>
						<th>OPTION MEMO2</th>
						<th>OPTION NAME</th>
						<th>SORT ORDER</th>
						<th>删除</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="optionCodes" var="option" status="rows_index">
						<tr class="<s:if test="#rows.even == true ">bg-yl</s:if>">
							<td><input type="hidden" size="10" name="optionId"
								id="optionId_${rows_index.count}" value="${option.oid}" />${option.oid}</td>
							<td><input type="text" size="10" name="optionCode"
								id="optionCode_${rows_index.count}" value="${option.code}" /></td>
							<td><input type="text" size="18" name="optionMemoOne"
								id="optionMemoOne_${rows_index.count}" value="${option.memo1}" /></td>
							<td><input type="text" size="6" name="optionMemoTwo"
								id="optionMemoTwo_${rows_index.count}" value="${option.memo2}" /></td>
							<td><input type="text" name="optionName"
								id="optionName_${rows_index.count}" value="${option.name}" /></td>
							<td><input type="text" size="4" name="optionSortOrder"
								id="optionSortOrder_${rows_index.count}"
								value="${option.sortOrder}" /></td>
							<td><input name="optionDelete" type="checkbox"
								value="${option.code}" /></td>
						</tr>
					</s:iterator>
					<tr class="">
						<td><input type="hidden" size="10" name="optionId"
							id="optionId" value="" /></td>
						<td><input type="text" size="10" name="optionCode"
							id="optionCode" value="" /></td>
						<td><input type="text" size="18" name="optionMemoOne"
							id="optionMemoOne" value="" /></td>
						<td><input type="text" size="6" name="optionMemoTwo"
							id="optionMemoTwo" value="" /></td>
						<td><input type="text" name="optionName" id="optionName"
							value="" /></td>
						<td><input type="text" size="4" name="optionSortOrder"
							id="optionSortOrder" value="" /></td>
						<td><a href="javascript:void(0);" class="btn btn-primary" id="newOpn"
							onclick="addNewOpn();">添加新的Option</a></td>
					</tr>
				</tbody>
				<tbody id="addOption"></tbody>
			</table>
			<div class="row-fluid">
				<div class="span12" style="margin-bottom:20px;margin-left: 15px">
					<a href="javascript:void(0);" class="btn btn-primary"
						onClick="submitForm();">储存</a>
					<a href="list.do" class="btn btn-primary">回上一页</a>
				</div>
			</div>
		</div>
	</s:form>
</div>