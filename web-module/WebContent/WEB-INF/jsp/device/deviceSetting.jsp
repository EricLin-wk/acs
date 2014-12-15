<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/taglibs.jsp"%>
<%@ page import="com.acs.core.common.utils.ServerValue"%>
<c:url value="../css" var="cssPath" />
<c:url value="../js" var="jsPath" />
<link rel="stylesheet" href="${cssPath}/jqx.base.css" type="text/css" />
<script type="text/javascript" src="${jsPath}/jqxcore.js"></script>
<script type="text/javascript" src="${jsPath}/jqxdata.js"></script>
<script type="text/javascript" src="${jsPath}/jqxbuttons.js"></script>
<script type="text/javascript" src="${jsPath}/jqxscrollbar.js"></script>        
<script type="text/javascript" src="${jsPath}/jqxgrid.js"></script>
<script type="text/javascript" src="${jsPath}/jqxgrid.selection.js"></script>
<style>
#deviceSearchForm label.error  {
	width: auto;
	display: inline;
	color: #B94A48;
}
#groupSearchForm label.error  {
	width: auto;
	display: inline;
	color: #B94A48;
}
</style>
<script>
$().ready(function() {
	$("#deviceSearchForm").validate({
		rules: {
			"paraDeviceId": "required"						
		},
		messages: {
			"paraDeviceId": "必须选择设备"	
		}
	});
	$("#groupSearchForm").validate({
		rules: {
			"paraGroupId": "required"						
		},
		messages: {
			"paraGroupId": "必须选择设备群组"	
		}
	});
	$("#saveDefaultForm").validate({
		rules: {
			"paraDefaultTemperature": {required: true, range: [-20, 100]},
			"paraDefaultHumidity": {required: true, range: [0, 100]}
		}
	});	
	$("#dialogDefaultSetting").dialog({
		autoOpen: false,
		modal: true});
	$("#btnDefaultSetting").click(function () {
		$("#dialogDefaultSetting").dialog("open");
   });
});
</script>
<div class="row-fluid">
	<s:if test="hasActionMessages()">
		<div class="alert alert-success" >
			<button type="button" class="close" data-dismiss="alert">×</button>
			<strong><s:actionmessage cssStyle="list-style-type:none;" escape="false"/></strong>
		</div>
	</s:if>
	<s:if test="hasActionErrors()">
		<div class="alert alert-error">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<strong><s:actionerror cssStyle="list-style-type:none;" escape="false"/></strong>
		</div>
	</s:if>
	<s:if test="hasFieldErrors()">
		<div class="alert alert-info">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<strong><s:fielderror cssStyle="list-style-type:none;" escape="false"/></strong>
		</div>
	</s:if>	
	<div class="box span12">
		<div class="box-header well" data-original-title>
			<h2>
				<i class="icon-tag"></i> 温度湿度设置
			</h2>
			<div class="box-icon">
				<a href="#" class="btn btn-primary" style="width: 65px" id="btnDefaultSetting">默认设定</a>
				<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
			</div>				
		</div>	
		<div class="box-content">
			<s:form action="listDevice.do" method="post" id="deviceSearchForm">
			<div class="row-fluid">			
				<div class="span1">选择设备</div>
				<div class="span2">
					<select data-placeholder="选择设备" id="paraDeviceId" name="paraDeviceId" data-rel="chosen" >
					<c:forEach items="${deviceMap}" var="entry">
						<optgroup label="${entry.key}">
						<c:forEach items="${entry.value}" var="device">
						<option value="${device.oid}" <c:if test="${device.oid == paraDeviceId}">selected</c:if>>${device.deviceName}</option>
						</c:forEach>
						</optgroup>
					</c:forEach>
					</select>		
				</div>
				<div class="span1">
						<button type="submit" class="btn btn-primary">搜索设备</button>
					</div>
			</div>
			</s:form>
			<s:form action="listGroup.do" method="post" id="groupSearchForm">
			<div class="row-fluid">			
				<div class="span1">选择设备群组</div>
				<div class="span2">
					<s:select id="paraGroupId" name="paraGroupId" list="groupMap" listKey="key" listValue="value.groupName" theme="simple"/>						
				</div>
				<div class="span1">
						<button type="submit" class="btn btn-primary">搜索群组</button>
					</div>
			</div>
			</s:form>			
		</div>
	</div>	
</div>
<div id="dialogDefaultSetting" title="默认设定">
	<s:form action="saveDefault.do" method="post" id="saveDefaultForm">
	<div class="">
		<div class="">
			默认温度<s:textfield id="paraDefaultTemperature" name="paraDefaultTemperature" theme="simple" cssStyle="width:30px"/>°C						
		</div>
		<div class="">
			默认湿度<s:textfield id="paraDefaultHumidity" name="paraDefaultHumidity" theme="simple" cssStyle="width:30px"/>%						
		</div>
		<div class=""><button id="btnSaveDefault" type="submit" class="btn btn-primary">保存设定</button></div>
	</div>
	</s:form>
</div>
<s:if test="paraJsonSetting != null">
<script>
$().ready(function() {
	var data = $("#paraJsonSetting").val();
	var source =
    {
        localdata: data,
        datafields:
        [
            { name: 'timeOfDay', type: 'string'},
            { name: 'sun', type: 'string' },
			{ name: 'mon', type: 'string' },
            { name: 'tue', type: 'string' },
            { name: 'wed', type: 'string' },
            { name: 'thu', type: 'string' },
            { name: 'fri', type: 'string' },
            { name: 'sat', type: 'string' }                    
        ],
        datatype: "json"
    };
    var dataAdapter = new $.jqx.dataAdapter(source);

    // initialize jqxGrid
    $("#jqxgrid").jqxGrid(
    {
        width: 610,
        source: dataAdapter,
        selectionmode: 'multiplecellsadvanced',
        autoheight: true,
        columns: [
          { text: '时间', datafield: 'timeOfDay', width: 50, align: 'center', cellsalign: 'center', pinned: true },
		  { text: '周日', datafield: 'sun', width: 80, align: 'center', cellsalign: 'right'},
		  { text: '周一', datafield: 'mon', width: 80, align: 'center', cellsalign: 'right'},
		  { text: '周二', datafield: 'tue', width: 80, align: 'center', cellsalign: 'right'},
		  { text: '周三', datafield: 'wed', width: 80, align: 'center', cellsalign: 'right'},
		  { text: '周四', datafield: 'thu', width: 80, align: 'center', cellsalign: 'right'},
		  { text: '周五', datafield: 'fri', width: 80, align: 'center', cellsalign: 'right'},
		  { text: '周六', datafield: 'sat', width: 80, align: 'center', cellsalign: 'right'}
        ]
    });
    
    $("#btnSetTemperature").click(function () {
	    var newValue = $("#paraTemperature").val() + "°C " + $("#paraHumidity").val() + "%";
	     var cells = $('#jqxgrid').jqxGrid('getselectedcells');
	     for (var i = 0; i < cells.length; i++) {
	         var cell = cells[i];         
	         if(cell.datafield != "timeOfDay"){
			 	$('#jqxgrid').jqxGrid('setcellvalue',  cell.rowindex, cell.datafield, newValue);                        
			 }
	     }
    });
    
    $("#btnSave").click(function () {
    	var data = $('#jqxgrid').jqxGrid('getrows');
    	$("#paraJsonSetting").val(JSON.stringify(data));
    	$("#saveForm").submit();
    	//$("#jqxgrid").jqxGrid('exportdata', 'json', 'jqxGrid');
    });
});
</script>
	<div class="row-fluid">
		<h4>${displayTtile}</h4>
	</div>
	<br />
	<div class="row-fluid">
		<div class="span1">
			温度<s:textfield id="paraTemperature" name="paraTemperature" theme="simple" cssStyle="width:30px"/>°C						
		</div>
		<div class="span1">
			湿度<s:textfield id="paraHumidity" name="paraHumidity" theme="simple" cssStyle="width:30px"/>%						
		</div>
		<div class="span2"><button id="btnSetTemperature" type="button" class="btn btn-primary">设定温度湿度</button></div>
	</div>
	<div id="jqxgrid">
	</div>	
	<br />
	<div class="row-fluid">
		<div class="span1">
		<s:form action="save.do" method="post" id="saveForm">
		<s:hidden id="paraDeviceId" name="paraDeviceId" value="%{paraDeviceId}"/>
		<s:hidden id="paraGroupId" name="paraGroupId" value="%{paraGroupId}"/>
		<s:hidden id="paraJsonSetting" name="paraJsonSetting" value="%{paraJsonSetting}"/>
		<button id="btnSave" type="button" class="btn btn-primary">保存设定</button>
		</s:form>
		</div>
	</div>
</s:if>